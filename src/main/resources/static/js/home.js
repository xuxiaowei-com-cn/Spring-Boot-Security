/** 登录 JS */

layui.define(['layer'], function (exports) {

    var $ = layui.$,
        layer = layui.layer,
        $body = $('body');

    var HIDE = "layui-hide";

    var home = {};

    /**
     * 更换图形验证码
     */
    $body.on('click', '.XXW-patchca-img', function () {
        this.src = '/patchca?t=' + new Date().getTime()
    });

    // 是微信
    // 需要引入微信JS（<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>）
    if (typeof WeixinJSBridge != "undefined") {
        // 隐藏微信扫码登录
        $(".XXW-wechat-website").addClass(HIDE);
        // 显示微信内部登录
        $(".XXW-wechat-webpage").removeClass(HIDE);
    }

    //对外暴露的接口
    exports('home', home);
});
