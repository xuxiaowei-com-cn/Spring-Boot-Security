# JsEncryptHandlerInterceptor

- JS 非对称性加密 拦截器

- 传输密码页面进行拦截，
    在页面中放入RSA非对成型加密公钥，
    页面使用 jsencrypt.js 和 公钥，
    加密密码后传输，
    后台接收密码后使用私钥解密