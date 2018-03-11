'use strict'

$(function () {
    /**
     * 机构注册
     */
    $("#institution-register-btn").click(function () {
        var name = $("#enter-institution-name").val();
        var address = $("#enter-address").val();
        var phone = $("#enter-phone").val();
        var password1 = $("#enter-institution-password1").val();
        var password2 = $("#enter-institution-password2").val();
        var reg1 = /^1(3|4|5|7|8)\\d{9}$/;
        var reg2 = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
        if(name==""||address==""||phone==""||password1==""||password2==""){
            $("#institution-register-error").html("请将注册信息填写完整！");
            $("#institution-register-error").show();
            setTimeout(function () {
                $("#institution-register-error").hide();
            },1000);
        }else if(password1.length<6||password1.length>16){
            $("#institution-register-error").html("密码长度不规范！");
            $("#institution-register-error").show();
            setTimeout(function () {
                $("#institution-register-error").hide();
            },1000);
        }else if(password2!=password1){
            $("#institution-register-error").html("两次密码不相同！");
            $("#institution-register-error").show();
            setTimeout(function () {
                $("#institution-register-error").hide();
            },1000);
        }else if(!(reg1.test(phone)||reg2.test(phone))){
            $("#institution-register-error").html("联系方式填写不正确！");
            $("#institution-register-error").show();
            setTimeout(function () {
                $("#institution-register-error").hide();
            },1000);
        }else{
            $.ajax({
                url: "/register.institution",
                type: "post",
                data: {
                    name: name,
                    address: address,
                    phone: phone,
                    password: password1
                },
                dataType: "json",
                success: function (data) {
                    var result = data["result"];
                    if(result=="has register"){
                        $("#institution-register-error").html("该机构已被注册！");
                        $("#institution-register-error").show();
                        setTimeout(function () {
                            $("#institution-register-error").hide();
                        },1000);
                    }else{
                        //显示识别码，并让其等待审核
                        $("#ins-id").html("此机构识别码为"+result+"！");
                        $("#insReg-success-modal").modal("show");
                    }
                }
            });
        }
    });

    /**
     * 机构登录
     */
    $("#institution-login-btn").click(function () {
        var id = $("#enter-institution-id").val();
        var password = $("#enter-institution-password").val();
        if(id==""||password==""){
            $("#institution-login-error").html("登陆信息填写不完整！");
            $("#institution-login-error").show();
            setTimeout(function () {
                $("#institution-login-error").hide();
            },1000);
        }else{
            $.ajax({
                url: "/login.institution",
                type: "post",
                dataType: "json",
                data: {
                    id: id,
                    password: password
                },
                success: function (data) {
                    var result = data["result"];
                    if(result=="wrong id"){
                        $("#institution-login-error").html("识别码错误！");
                        $("#institution-login-error").show();
                        setTimeout(function () {
                            $("#institution-login-error").hide();
                        },1000);
                    }else if(result=="wrong password"){
                        $("#institution-login-error").html("密码错误！");
                        $("#institution-login-error").show();
                        setTimeout(function () {
                            $("#institution-login-error").hide();
                        },1000);
                    }else if(result=="still in check"){
                        $("#institution-login-error").html("此账号仍在审核中！");
                        $("#institution-login-error").show();
                        setTimeout(function () {
                            $("#institution-login-error").hide();
                        },1000);
                    }else if(result=="fail check"){
                        $("#institution-login-error").html("此账号申请失败！");
                        $("#institution-login-error").show();
                        setTimeout(function () {
                            $("#institution-login-error").hide();
                        },3000);
                    }else{
                        //登录成功
                        window.open("/institution","_self");
                    }
                }
            });
        }
    });
});