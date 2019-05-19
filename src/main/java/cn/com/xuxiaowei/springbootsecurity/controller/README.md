# ErrorController

- 错误页面 Controller

- 404、500等配置页面位置

# ForgetController

- 忘记密码页面

# IndexController

- 主页 Controller

    - 获取 Security 中用户名
    
    - 获取 Security 中用户名权限
    
    - 获取 用户名 IP
    
        - 如果使用了 Nginx 反向代理，需要正确配置 Nginx 和 配置 application.yml
        
            - Nginx 配置：
                
                - 参见 spring-boot-security\src\main\java\cn\com\xuxiaowei\springbootsecurity\controller\README-Nginx.md

            - Spring Boot application.yml 配置：
            
                - 参见 spring-boot-security\src\main\resources\README-SpringBoot.md

# MainsiteErrorController

- 主站点错误 Controller

- 类似于 WebServerFactoryCustomizer <>， 优先级低于 WebServerFactoryCustomizer <>（两者二选一）

- ErrorController 可设置请求转发与重定向，WebServerFactoryCustomizer <> 只能请求转发

# RegController

- 注册页面

# SecurityController

- 登录页面

# SecurityRestController

- 登录成功

- 登录失败

# SmsController

- 短信登录页面

# SmsRestController

- 发送短信验证码

# UserRestController

- 用户表（测试） 前端控制器，自动生成实体类相关类时生成的，暂未使用