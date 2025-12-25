### 1.新建ShutdownController

```java
package com.center.medical.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 路飞
 * @date: 2023-05-12 19:16
 * @description:
 */
@RestController
public class ShutdownController {

    private final ShutdownEndpoint shutdownEndpoint;

    @Autowired
    public ShutdownController(ShutdownEndpoint shutdownEndpoint) {
        this.shutdownEndpoint = shutdownEndpoint;
    }

    @PostMapping("/shutdown")
    public void shutdownContext() {
        shutdownEndpoint.shutdown();
    }
}
```

### 2.启用`Spring Boot Actuator`

- 确保在你的SpringBoot项目的配置文件（如`application.properties/application.yml`）中启用了`Spring Boot Actuator`，以确保`ShutdownEndpoint`
  可以自动注册

```yaml
management:
  endpoints:
    web:
      exposure:
        include: shutdown
```

### 3.声明`ShutdownEndpoint`为一个bean

-- 在`AppConfig`配置类中声明了一个类型为`ShutdownEndpoint`的bean。这样一来，`Spring IoC`容器就会正确地将`ShutdownEndpoint`注册为bean

```java
package com.center.medical.app.config;

import org.springframework.boot.actuate.context.ShutdownEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 路飞
 * @date: 2023-05-12 19:25
 * @description:
 */
@Configuration
public class AppConfig {
    @Bean
    public ShutdownEndpoint shutdownEndpoint() {
        return new ShutdownEndpoint();
    }
}

```