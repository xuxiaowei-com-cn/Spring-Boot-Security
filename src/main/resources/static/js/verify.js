/** 验证 JS */

layui.define(['form'], function (exports) {

    var form = layui.form;

    var verify = {};

    //自定义验证
    form.verify({

        /**
         * 用户昵称 验证
         */
        username: function (value) {
            if (value == null || value === "") {
                return "用户名不能为空！";
            }
        },

        /**
         * 密码 验证
         */
        password: function (value) {
            if (value == null || value === "") {
                return "密码不能为空！";
            }
        },

        /**
         * 验证码 验证
         */
        patchca: function (value) {
            if (value == null || value === "") {
                return "验证码不能为空！";
            }
        }

    });

    //对外暴露的接口
    exports('verify', verify);
});
