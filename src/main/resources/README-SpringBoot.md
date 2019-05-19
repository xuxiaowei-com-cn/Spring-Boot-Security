# 服务器 配置
  server:
    # 服务器端口
    port: 40004
    ssl:
      ## SSL 文件名不可以带 .
      key-store: classpath:config/cert/tomcat/demo_xuxiaowei_com_cn.pfx
      key-store-password: 密码
    # 是否应将X-Forwarded-* 标头应用于HttpRequest。
    use-forward-headers: true
    tomcat:
      # 从中提取远程IP的HTTP标头的名称。 例如，`X-FORWARDED-FOR`。
      remote-ip-header: X-Real-IP
      # 用于覆盖原始端口值的HTTP标头的名称。
      port-header: X-Forwarded-Proto