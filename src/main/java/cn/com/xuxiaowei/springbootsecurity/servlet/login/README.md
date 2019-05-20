- WeChatWebPageHttpServlet

    - 第三方登录（微信网页（微信内部））URL HttpServlet

    - 需要注意 getOAuth2CodeUrl(String redirect_uri, String scope, String state) 中的 redirect_uri 参数，
        要与 [微信公众平台接口测试帐号申请](http://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login) 中的 
        网页帐号（网页授权获取用户基本信息）设置一致
    
        - 设置方法：
    
            - 在 [微信公众平台接口测试帐号申请](http://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login) 
                
                - 体验接口权限表
                    
                    - 网页服务
                    
                        - 网页帐号（网页授权获取用户基本信息）
                            
                            - 测试时，可设置为 127.0.0.1