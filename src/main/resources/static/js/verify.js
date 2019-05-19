/** 验证 JS */

layui.define(['form'], function (exports) {

    var $ = layui.$,
        form = layui.form;

    var verify = {
        /**
         * 是否包含大写字母
         */
        containsUpperCase: function (value) {
            return new RegExp(/^.*(?=.*[A-Z]).*$/).test(value);
        },

        /**
         * 是否包含小写字母
         */
        containsLowerCase: function (value) {
            return new RegExp(/^.*(?=.*[a-z]).*$/).test(value);
        },

        /**
         * 是否包含字母
         */
        containsCase: function (value) {
            return new RegExp(/^.*(?=.*[a-zA-Z]).*$/).test(value);
        },

        /**
         * 是否包含数字
         */
        containsNumber: function (value) {
            return new RegExp(/^.*(?=.*\d).*$/).test(value);
        },

        /**
         * 是否包含特殊字符串
         */
        containsSpecial: function (value) {
            return new RegExp(/^.*(?=.*[~!@#$%^&*()_+-=/:;"'<,>.?\\]).*$/).test(value);
        },

        /**
         * 是否包含中文
         */
        containsChinese: function (value) {
            return new RegExp(/^.*(?=.*[\u4e00-\u9fa5]).*$/).test(value);
        },

        /**
         * 是否为手机号
         */
        isPhone: function (value) {
            return new RegExp(/^1\d{10}$/).test(value);
        }

    };

    //自定义验证
    form.verify({

        /**
         * 用户昵称 验证
         */
        phone: function (value) {
            if (value == null || value === "") {
                return "手机号不能为空！";
            }
            if (!verify.isPhone(value)) {
                return "手机号不正确！";
            }
        },

        /**
         * 密码 验证
         */
        password: function (value) {
            if (value == null || value === "") {
                return "密码不能为空！";
            }
            if (!verify.containsUpperCase(value)) {
                return "密码必须包含大写字母！"
            }
            if (!verify.containsLowerCase(value)) {
                return "密码必须包含小写字母！"
            }
            if (!verify.containsNumber(value)) {
                return "密码必须包含数字！"
            }
            if (!verify.containsSpecial) {
                return "密码必须包含特殊字符！"
            }
            if (!verify.containsChinese) {
                return "密码不能包含中文！"
            }
            var length = value.length;
            if (length < 6 || length > 16) {
                return "密码长度6-16";
            }
        },

        /**
         * 确认密码 验证
         */
        repassPassword: function (value) {
            if (value == null || value === "") {
                return "确认密码不能为空！";
            }
            var password = $("#password").val();
            if (password !== value) {
                return "确认密码与密码不一致！";
            }
        },

        /**
         * 图片验证码 验证
         */
        patchca: function (value) {
            if (value == null || value === "") {
                return "图片验证码不能为空！";
            }
        },

        /**
         * 短信验证码 验证
         */
        smsCode: function (value) {
            if (value == null || value === "") {
                return "短信验证码不能为空！";
            }
        }

    });

    //对外暴露的接口
    exports('verify', verify);
});
