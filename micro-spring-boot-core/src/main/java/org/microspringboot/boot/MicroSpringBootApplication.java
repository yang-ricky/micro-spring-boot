package org.microspringboot.boot;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MicroSpringBootApplication {
    /**
     * 排除自动配置类
     */
    Class<?>[] exclude() default {};

    /**
     * 指定扫描的包路径
     */
    String[] scanBasePackages() default {};
} 