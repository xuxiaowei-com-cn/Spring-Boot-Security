/** 登录 JS */

layui.define(['layer'], function (exports) {

    var $ = layui.$,
        layer = layui.layer,
        $body = $('body');

    var home = {};

    /**
     * 更换图形验证码
     */
    $body.on('click', '.XXW-patchca-img', function () {
        this.src = '/patchca?t=' + new Date().getTime()
    });

    //对外暴露的接口
    exports('home', home);
});
