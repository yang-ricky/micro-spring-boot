package org.microspringboot.boot.autoconfigure;

import org.microspring.core.BeanPostProcessor;
import org.microspring.core.DefaultBeanFactory;
import org.microspring.core.DefaultBeanDefinition;
import org.microspring.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 自动配置导入选择器
 * 用于从META-INF/spring.factories中加载自动配置类
 */
@Component
public class AutoConfigurationImportSelector implements BeanPostProcessor {
    
    private final DefaultBeanFactory beanFactory;
    private static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";

    public AutoConfigurationImportSelector(DefaultBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean.getClass().isAnnotationPresent(EnableAutoConfiguration.class)) {
            processAutoConfigurations();
        }
        return bean;
    }

    private void processAutoConfigurations() {
        List<String> configurations = loadFactoryNames();
        for (String className : configurations) {
            try {
                Class<?> clazz = Class.forName(className);
                // 注册为Bean
                if (!beanFactory.containsBean(className)) {
                    beanFactory.registerBeanDefinition(className, 
                        new DefaultBeanDefinition(clazz));
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Failed to load auto-configuration class: " + className, e);
            }
        }
    }

    private List<String> loadFactoryNames() {
        List<String> result = new ArrayList<>();
        try {
            Enumeration<URL> urls = getClass().getClassLoader()
                .getResources(FACTORIES_RESOURCE_LOCATION);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("#")) continue;
                    result.add(line.trim());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load spring.factories", e);
        }
        return result;
    }
} 