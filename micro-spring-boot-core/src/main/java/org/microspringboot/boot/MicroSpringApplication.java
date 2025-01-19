package org.microspringboot.boot;

import org.microspring.context.ApplicationContext;
import org.microspring.context.support.AnnotationConfigApplicationContext;

public class MicroSpringApplication {
    
    private Class<?> primarySource;

    public MicroSpringApplication(Class<?> primarySource) {
        this.primarySource = primarySource;
    }

    public static ApplicationContext run(Class<?> primarySource, String... args) {
        return new MicroSpringApplication(primarySource).run(args);
    }

    public ApplicationContext run(String... args) {
        // 1. 创建应用上下文
        AnnotationConfigApplicationContext context = createApplicationContext();
        
        // 2. 配置上下文
        prepareContext(context);
        
        // 3. 刷新上下文
        context.refresh();
        
        return context;
    }

    protected AnnotationConfigApplicationContext createApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        if (this.primarySource != null) {
            // 设置基础包扫描路径为主配置类所在的包
            context.setBasePackage(this.primarySource.getPackage().getName());
        }
        return context;
    }

    protected void prepareContext(ApplicationContext context) {
        // 在这里可以做一些上下文准备工作
        // 比如设置环境变量等
    }
} 