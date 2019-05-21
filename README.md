# 欢迎捐助

- 如果您觉得本项目对您有帮助，请捐助，谢谢。

- 您的捐助就是我最大的动力。

<p align=center>
  <a href="https://xuxiaowei.com.cn">
    <img src="https://img-blog.csdnimg.cn/20190522010815371.png" alt="徐晓伟工作室" width="360">
  </a>
</p>


# Spring-Boot-Security
Spring Boot 整合 Security。
内涵 Spring Boot 整合 MyBatis Plus，详情请查看 [Spring-Boot-MyBatis-Plus](https://github.com/XXWXHK/Spring-Boot-MyBatis-Plus)


# 使用技术

## RSA 非对称性加密

- 用于用户注册、登录、重置密码时，密码进行RSA非对称性加密传输

## 密码加密

- 用户密码MD5加密储存

## 登录

### Spring Boot Security 登录

- 用户名、密码登录

- 登录前验证图片验证码

### 短信验证码 登录

- 设置保持登录 2 天（或自定义）

### QQ 登录

- 设置保持登录 1 天（或自定义）

- 重写 QQ 组件依赖
    
    - 申请 QQ 应用打通（UnionId）
    
    - 获取 QQ UnionId

### 微信网页（微信内部） 登录

- 设置保持登录 3 天（或自定义）

### 微信扫码 登录

- 设置保持登录 4 天（或自定义）

### 支付宝 登录

- 设置保持登录 5 天（或自定义）

### 微博 登录

- 设置保持登录 6 天（或自定义）

- 请使用 sources 文件夹中的 weibo4j-oauth2-2.1.1-beta3.1.2.jar
    
    - Maven 库中，只有 [weibo4j-oauth2-2.1.1-beta2-3](https://mvnrepository.com/artifact/com.belerweb/weibo4j-oauth2)
    
    - GitHub 中有 [weibo4j-oauth2-beta3.1.1](https://github.com/sunxiaowei2014/weibo4j-oauth2-beta3.1.1)，不过是源码
    
    - sources 文件夹中的 weibo4j-oauth2-2.1.1-beta3.1.2.jar，是修正以后构建的，包括源码

- 重写微博组件

    - 修正逻辑

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
    
- 支付宝开放 API

    - alipay-sdk-java
    
- 微博 组件 weibo4j
    - weibo4j-oauth2        
    
