# HandlerInterceptorConfig

- 拦截器配置

- 激活拦截器

- 自定义拦截器拦截的请求 URL

# LoginUrlAuthenticationEntryPointConfig

- 自定义登录URL验证入口点

- LoginUrlAuthenticationEntryPoint 优先级 高于 http.formLogin().loginPage()

- 使用 LoginUrlAuthenticationEntryPoint 可自定义 登录页面 URL（在登录页面 增加 请求你需要的参数）

# MybatisPlusConfig

- Mybatis Plus 配置

- SQL 执行效率插件

- SQL 格式化

- 分页插件

# ServletConfig

- 激活 Servlet

- 注册 Servlet 为 Bean，使得 Servlet 可使用 Autowired

- 自定义 Servlet URL

# WebSecurityConfigurerAdapterConfig

- 配置 Security 登录页面、登录URL、登录失败URL、登录成功后URL、注销URL、注销成功后跳转的URL、Remember Me、Remember Me Key、Token Cookie 有效时间

- 配置 自定义身份验证

# WebSecurityPasswordEncoderConfig

- 密码编码器

- RSA 解密用户登录时，前端传入的密码

- MD5 加密密码，用于比较

# WebServerFactoryCustomizerConfig

- 自定义错误页面（404、500等）等配置

- WebServerFactoryCustomizer <> 优先级高于 ErrorController（两者二选一）