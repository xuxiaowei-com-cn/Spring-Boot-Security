# Spring-Boot-Security
Spring Boot 整合 Security。
内涵 Spring Boot 整合 MyBatis Plus，详情请查看 [Spring-Boot-MyBatis-Plus](https://github.com/XXWXHK/Spring-Boot-MyBatis-Plus)


# 使用技术

## RSA 非对称性加密

- 用于用户注册、登录、重置密码时，密码进行非对称性加密传输

## 密码加密

- 用户密码MD5加密储存

## 登录

### Spring Boot Security 登录

- 登录前验证图片验证码

### 短信验证码 登录

- 设置保持登录 2 天（或自定义）

### QQ 登录

- 设置保持登录 1 天（或自定义）

- 重写 QQ 组件依赖
    
    - 申请 QQ 应用打通（UnionId）
    
    - 获取 QQ UnionId

# 依赖

## Spring Boot 依赖（创建项目时选择）

- Spring Boot
    - 2.1.5.RELEASE

- Core
    - Lombok                    注解（Getter/Setter）。
    - Configuration Processor   为您的自定义配置键生成元数据（注解处理器）。
    
- Web
    - Web                       使用Tomcat和Spring MVC进行全栈Web开发
    
- Template
	- Thymeleaf                 Thymeleaf模板引擎（页面）
    
- Security
    - Security                  通过spring-security保护您的应用程序
    
    
## 其他依赖（创建项目时不可选）

- Fastjson                              阿里巴巴 JSON

- Thymeleaf Extras Spring Security 5    在页面中使用 Thymeleaf 进行权限控制

- MyBatis Plus

    - MyBatis Plus Boot Starter         Spring Boot 整合 MyBatis Plus 必须依赖。
    
    - MyBatis Plus Generator            MyBatis Plus 代码生成器 依赖。
    
    - Spring Boot Starter Velocity      Velocity 模板引擎，MyBatis Plus 代码生成器 默认模板引擎。
    
- 图片验证码
    
    - patchca                           GitHub 图片验证码
    
- QQ 组件

    - Sdk4J                             
    
- WeiXin4J

    - Weixin4j Spring Boot Starter