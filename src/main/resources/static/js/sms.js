/**
 * 短信验证码 JS
 */
layui.define(["layer"], function (exports) {

    var $ = layui.jquery,
        $body = $("body"),
        DISABLED = "layui-disabled";

    //发送验证码
    var sms = {

        /**
         * 发送验证码
         *
         * 发送短信验证码前，需要验证图片验证码，防止发送短信被攻击
         *
         * @param options 对象
         */
        sendSmsCode: function (options) { // getSmsCode, seconds, phone, patchca

            // $.extend 遍历擦混入的对象（数组），如果未传值，使用默认值（可在下面设置默认值，例如：seconds）
            options = $.extend({
                getPatchcaImg: "",      // 获取图片验证码按钮
                getSmsCode: "",         // 发送短信验证码的按钮
                seconds: 60,            // 发送短信验证码间隔时间（秒），默认为 60 秒，如时间不是 60 秒，请传入你的期望值
                phone: "",              // 手机号输入框
                patchca: "",            // 图片验证码输入框
                sendSmsUrl: "",         // 验证图片验证码并发送短信验证码 URL
                header: "",             // 跨站请求伪造
                token: "",              // 跨站请求伪造
                warning: false          // 正在倒计时时，点击获取验证码按钮（倒计时）的警告提示语
            }, options);

            // 获取遍历后的值
            var getPatchcaImg = options.getPatchcaImg,
                getSmsCode = options.getSmsCode,
                seconds = options.seconds,
                phone = options.phone,
                patchca = options.patchca,
                sendSmsUrl = options.sendSmsUrl,
                header = options.header,
                token = options.token,
                warning = options.warning;

            var $getPatchcaImg = $(getPatchcaImg),  // 获取图片验证码按钮 对象
                $getSmsCode = $(getSmsCode),        // 发送短信验证码的按钮 对象
                $phone = $(phone),                  // 手机号输入框 对象
                $patchca = $(patchca),              // 图片验证码输入框 对象
                loopSeconds = seconds,              // 将时间间隔赋值给倒计时
                timeout,                            // 控制倒计时 对象

                countDown = function (loop) {

                    // 倒计时时间自减
                    loopSeconds--;

                    if (loopSeconds < 0) {

                        // clearInterval：取消由 setInterval() 设置的 timeout。
                        clearInterval(timeout);

                        // 倒计时结束，重置倒计时时间间隔
                        loopSeconds = seconds;

                        $getSmsCode.removeClass(DISABLED).html("获取验证码");

                    } else {

                        $getSmsCode.addClass(DISABLED).html(loopSeconds + "秒后重获");

                    }

                    if (!loop) {

                        // setInterval：按照指定的周期（以毫秒计）来调用函数或计算表达式
                        timeout = setInterval(function () {

                            countDown(true);

                        }, 1000);

                    }

                };

            $body.on("click", getSmsCode, function () {

                var phoneV = $phone.val(),
                    patchcaV = $patchca.val();

                // 是否已发送短息验证码（禁用）
                var getSmsCode_DISABLED = $getSmsCode.hasClass(DISABLED);

                if (phoneV == null || phoneV === "") {
                    $phone.focus();
                    return layer.msg("发送短信验证码时，手机号不为空！", {icon: 0})
                }

                if (patchcaV == null || patchcaV === "") {
                    $patchca.focus();
                    return layer.msg("发送短信验证码时，图片验证码不为空！", {icon: 0})
                }

                if (getSmsCode_DISABLED) {  // 正在倒计时时，点击获取短信验证码

                    if (warning) {
                        layer.msg("请在" + loopSeconds + "秒后重试！", {icon: 0})
                    }

                } else {                    // 没有倒计时时、倒计时结束时，点击获取短信验证码

                    $.ajax({
                        url: sendSmsUrl,
                        type: "post",
                        beforeSend: function (xhr) {
                            /* 跨站请求伪造 */
                            xhr.setRequestHeader(header, token);
                        },
                        data: {
                            phone: phoneV,
                            patchca: patchcaV
                        },
                        success: function (res) {
                            //console.log("success", res);

                            var code = res.code;
                            var msg = res.msg;

                            if (code === 0) {
                                countDown();
                                $patchca.addClass(DISABLED).attr("disabled", true);
                                layer.msg(msg, {icon: 1})
                            } else if (code === 1) {

                                // 验证图片验证码失败后，重新获取新的图片验证码
                                layer.msg(msg, {icon: 0}, function () {
                                    $getPatchcaImg.click();
                                })
                            }
                        },
                        error: function (res) {
                            console.log("error", res);
                        }

                    });

                }

            })

        }

    };

    //对外暴露的接口
    exports("sms", sms);
});
