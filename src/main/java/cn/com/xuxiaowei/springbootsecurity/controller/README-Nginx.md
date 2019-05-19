
## 配置 http
server {
    listen       80;
    server_name  demo.xuxiaowei.com.cn;

    ## 在 Nginx 中配置 http 重定向到 https
    rewrite ^(.*)     https://$host$1 permanent;

    location / {
    
        ## 端口为 Spring Boot http 端口
        proxy_pass          http://127.0.0.1:10004;
        proxy_set_header    Host                $host;
        proxy_set_header    X-Real-IP           $remote_addr;
        proxy_set_header    X-Forwarded-For     $proxy_add_x_forwarded_for;
    }

    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   html;
    }
}

## 配置 https
server {
    listen 443 ssl;
    server_name demo.xuxiaowei.com.cn;

    ## SSL 文件位置
    ssl_certificate cert/nginx/demo.xuxiaowei.com.cn.pem;
    ssl_certificate_key cert/nginx/demo.xuxiaowei.com.cn.key;
    
    ssl_session_timeout 5m;
    ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:!MD5:!ADH:!RC4;
    ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
    ssl_prefer_server_ciphers on;
    
    location / {
    
        ## 端口为 Spring Boot https 端口
        proxy_pass          https://127.0.0.1:40004;
        proxy_set_header    Host                $host;
        proxy_set_header    X-Real-IP           $remote_addr;
        proxy_set_header    X-Forwarded-For     $proxy_add_x_forwarded_for;
    }
}
