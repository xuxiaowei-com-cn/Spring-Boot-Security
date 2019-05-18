/** 验证 JS */

layui.define(['form'], function (exports) {

    var form = layui.form;

    var verify = {};

    //自定义验证
    form.verify({

        /**
         * 用户昵称 验证
         */
        nickname: function (value) {
            if (value == null || value === "") {
                return "昵称不能为空！";
            }
        },

        /**
         * 密码 验证
         */
        password: function (value) {
            if (value == null || value === "") {
                return "密码不能为空！";
            }
        }

    });

    //对外暴露的接口
    exports('verify', verify);
});
