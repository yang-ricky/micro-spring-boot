# Micro-Spring-Boot 开发计划

## 基础阶段（核心功能构建）

### 学习目标
1. 掌握 **Spring Boot** 的核心概念和启动原理
2. 理解 **自动配置**（Auto-Configuration）机制
3. 实现 **独立运行**（Stand-alone）的Spring应用
4. 掌握 **条件装配**（Conditional）的实现原理

---

### 任务1：搭建项目结构与环境
**场景**：初始化一个最基础的Micro-Spring-Boot框架

- [ ] **创建多模块Maven项目**
  - micro-spring-boot-core：核心启动模块
  - micro-spring-boot-autoconfigure：自动配置模块
  - micro-spring-boot-starter：starter依赖模块

- [ ] **定义核心类**：
  1. `MicroSpringApplication`：应用启动入口
  2. `MicroSpringBootApplication`：核心注解
  3. `AutoConfigurationImportSelector`：自动配置选择器

- [ ] **基础依赖配置**
  - 引入micro-spring-context作为基础依赖
  - 配置模块间依赖关系
  - 添加必要的测试依赖

**产出要求**：
1. 完整的项目结构，各模块职责清晰
2. 可正常编译运行的工程
3. 基础单元测试全部通过

---

### 任务2：实现应用启动核心
**场景**：实现类似SpringApplication.run()的核心启动流程

- [ ] **实现 `MicroSpringApplication` 类**
  ```java
  public class MicroSpringApplication {
      public static ConfigurableApplicationContext run(Class<?> primarySource, String... args) {
          // 1. 创建应用上下文
          // 2. 准备环境
          // 3. 刷新上下文
      }
  }
  ```

- [ ] **定义启动流程**
  1. 推断应用类型（SERVLET/REACTIVE）
  2. 设置初始化器（Initializer）
  3. 准备环境（Environment）
  4. 创建应用上下文
  5. 刷新上下文前的准备
  6. 刷新应用上下文
  7. 刷新后的扩展点

- [ ] **支持命令行参数**
  - 解析命令行参数到环境变量
  - 支持--server.port等基础配置

**产出要求**：
1. 能通过`MicroSpringApplication.run(App.class, args)`启动应用
2. 正确输出启动日志和耗时
3. 支持基本的命令行参数配置

---

### 任务3：实现自动配置核心
**场景**：实现Spring Boot最核心的自动配置机制

- [ ] **注解定义**
  ```java
  @Target(ElementType.TYPE)
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @Inherited
  public @interface EnableAutoConfiguration {
  }
  ```

- [ ] **自动配置加载机制**
  1. 扫描classpath下的META-INF/spring.factories
  2. 加载AutoConfiguration类
  3. 按条件筛选需要的配置类

- [ ] **条件注解支持**
  - @ConditionalOnClass
  - @ConditionalOnMissingBean
  - @ConditionalOnProperty

**产出要求**：
1. 自动配置类能被正确加载
2. 条件注解能正常工作
3. 配置加载过程有清晰的日志

---

### 任务4：实现Starter机制
**场景**：支持类似spring-boot-starter的模块化配置

- [ ] **设计Starter结构**
  ```
  micro-spring-boot-starter-web/
  ├── pom.xml
  └── src/
      └── main/
          ├── java/
          │   └── org/microspring/boot/web/
          └── resources/
              └── META-INF/
                  ├── spring.factories
                  └── spring.provides
  ```

- [ ] **自动配置类**
  - WebAutoConfiguration
  - ServerProperties
  - 内嵌Tomcat支持

- [ ] **依赖管理**
  - 定义starter的依赖规范
  - 处理传递依赖
  - 版本管理

**产出要求**：
1. 创建一个web-starter示例
2. 引入starter后相关配置自动生效
3. 支持配置文件覆盖默认配置

---

## 中级阶段（增强功能与优化设计）

### 任务5：配置属性绑定
**场景**：支持配置文件到Java对象的自动绑定

- [ ] **配置文件加载**
  - 支持application.properties/yaml
  - 多环境配置（application-{profile}）
  - 外部配置文件

- [ ] **类型转换**
  - 基础类型转换
  - 集合类型处理
  - 自定义转换器

- [ ] **配置前缀绑定**
  ```java
  @ConfigurationProperties(prefix = "server")
  public class ServerProperties {
      private int port = 8080;
      private String contextPath;
      // ...
  }
  ```

**产出要求**：
1. 配置文件能自动绑定到@ConfigurationProperties类
2. 支持复杂类型转换
3. 配置验证和提示

---

### 任务6：内嵌容器支持
**场景**：实现Spring Boot的内嵌容器特性

- [ ] **Servlet容器抽象**
  ```java
  public interface WebServer {
      void start();
      void stop();
      int getPort();
  }
  ```

- [ ] **Tomcat实现**
  - 内嵌Tomcat配置
  - 生命周期管理
  - 错误页面处理

- [ ] **优雅停机**
  - 注册关闭钩子
  - 资源释放
  - 请求处理完成等待

**产出要求**：
1. 应用启动时自动启动内嵌服务器
2. 支持端口和上下文路径配置
3. 实现优雅停机

---

## 高级阶段（扩展与优化）

### 任务7：Actuator功能
**场景**：实现运行时监控和管理端点

- [ ] **核心端点**
  - /health：健康检查
  - /metrics：性能指标
  - /env：环境变量

- [ ] **端点安全**
  - 访问控制
  - 敏感信息过滤

- [ ] **自定义端点**
  ```java
  @Endpoint(id = "custom")
  public class CustomEndpoint {
      @ReadOperation
      public Map<String, Object> getInfo() {
          // ...
      }
  }
  ```

**产出要求**：
1. 内置端点可正常访问
2. 支持端点启用/禁用配置
3. 可自定义端点

---

### 任务8：事件机制与扩展点
**场景**：提供类似Spring Boot的事件通知机制

- [ ] **核心事件**
  - ApplicationStartingEvent
  - ApplicationEnvironmentPreparedEvent
  - ApplicationReadyEvent

- [ ] **监听器支持**
  ```java
  public class MyListener implements ApplicationListener<ApplicationStartingEvent> {
      @Override
      public void onApplicationEvent(ApplicationStartingEvent event) {
          // ...
      }
  }
  ```

- [ ] **自定义扩展点**
  - ApplicationContextInitializer
  - ApplicationRunner
  - CommandLineRunner

**产出要求**：
1. 启动过程中正确发布事件
2. 监听器能正常接收和处理事件
3. 扩展点正常工作

---

## 附加任务（可选）

1. **开发工具支持**
   - 热重载
   - 开发时自动重启
   - 远程调试支持

2. **日志配置**
   - 默认日志配置
   - 自定义日志级别
   - 文件输出配置

3. **异常处理增强**
   - 统一错误页面
   - REST API错误处理
   - 自定义错误属性

4. **Banner定制**
   - 启动图案定制
   - 动态Banner
   - 禁用Banner

## 开发规范
1. 代码风格遵循micro-spring规范
2. 每个功能必须有单元测试
3. 关键功能需要有集成测试
4. 保持良好的代码注释

## 项目里程碑
1. 基础阶段（2周）：完成核心启动和自动配置
2. 中级阶段（2周）：完成配置绑定和内嵌容器
3. 高级阶段（2周）：完成监控和扩展机制

## 注意事项
- 保持与Spring Boot的概念一致性
- 注重启动性能优化
- 提供完善的文档
- 保持代码简洁性