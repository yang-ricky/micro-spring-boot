package org.microspringboot.boot.autoconfigure;

import java.lang.annotation.*;

/**
 * 启用自动配置的注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableAutoConfiguration {
} 