
'use strict';
$(function () {
    $(document).ready(function(){
    });

    /**
     * 学生登录
     */
    $("#login-btn").click(function(){
        var email = $("#enter-email").val();
        var password = $("#enter-password").val();
        var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        if(email==""&&password==""){
            $("#login-error").html("邮箱和密码不能为空！");
            $("#login-error").show();
            setTimeout(function(){$("#login-error").hide();},1000);
        }else if(email==""){
            $("#login-error").html("邮箱不能为空！");
            $("#login-error").show();
            setTimeout(function(){$("#login-error").hide();},1000);
        }else if(password==""){
            $("#login-error").html("密码不能为空！");
            $("#login-error").show();
            setTimeout(function(){$("#login-error").hide();},1000);
        }else if(!reg.test(email)){
            $("#login-error").html("邮箱格式不正确！");
            $("#login-error").show();
            setTimeout(function(){$("#login-error").hide();},1000);
        }else{
            $.ajax({
                url:"/login.user",
                dataType: 'json',
                type: 'post',
                data: {
                    email: email,
                    password: password,
                },
                success: function (data) {
                    var result = data["result"];
                    if (result == "wrong email") {
                        $("#login-error").html("该邮箱没有被注册！");
                        $("#login-error").show();
                        setTimeout(function () {
                            $("#login-error").hide();
                        }, 1000);
                    } else if (result == "wrong password") {
                        $("#login-error").html("密码错误！");
                        $("#login-error").show();
                        setTimeout(function () {
                            $("#login-error").hide();
                        }, 1000);
                    } else if (result == "write off") {
                        $("#login-error").html("此账号已停用！");
                        $("#login-error").show();
                        setTimeout(function () {
                            $("#login-error").hide();
                        }, 1000);
                    }else{
                        //登录成功
                        window.open("/index","_self");
                    }
                },
            });
        }
    });

    /**
     * 邮箱验证
     */
    $("#send-code-btn").click(function () {
        var email = $("#enter-email").val();
        var password1 = $("#enter-password1").val();
        var password2 = $("#enter-password2").val();
        var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        if (!reg.test(email)) {
            $("#register-error").html("邮箱格式不正确！");
            $("#register-error").show();
            setTimeout(function(){$("#register-error").hide();},1000);
        }else if(email==""){
            $("#register-error").html("邮箱不能为空！");
            $("#register-error").show();
            setTimeout(function(){$("#register-error").hide();},1000);
        }else if(password1!=password2){
            $("#register-error").html("两次密码不一样！");
            $("#register-error").show();
            setTimeout(function(){$("#register-error").hide();},1000);
        }else{
            $("#send-code-btn").attr("disabled",true);
            $("#send-code-btn").attr("class","my-button1 disable-button");
            setTimeout(function () {
                $("#send-code-btn").attr("class","my-button1");
                $("#send-code-btn").removeAttr("disabled");
            },10000);
            $.ajax({
                url: "/register.getCode",
                type: "post",
                dataType: "json",
                data:{
                    email: email
                },
                success: function (data) {
                }
            });
        }
    });

    /**
     * 学生注册
     */
    $("#register-btn").click(function () {
        var username = $("#enter-username").val();
        var email = $("#enter-email").val();
        var password1 = $("#enter-password1").val();
        var password2 = $("#enter-password2").val();
        var code = $("#enter-code").val();
        var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        if(username==""||email==""||password1==""||password2==""){
            $("#register-error").html("注册信息填写不完整！");
            $("#register-error").show();
            setTimeout(function(){$("#register-error").hide();},1000);
        }else if(code==""){
            $("#register-error").html("验证码不能为空！");
            $("#register-error").show();
            setTimeout(function(){$("#register-error").hide();},1000);
        }else if(password1.length<6||password1.length>16){
            $("#register-error").html("密码长度不规范！");
            $("#register-error").show();
            setTimeout(function(){$("#register-error").hide();},1000);
        }else if(password1!=password2) {
            $("#register-error").html("两次密码不相同！");
            $("#register-error").show();
            setTimeout(function () {
                $("#register-error").hide();
            }, 1000);
        }else if(!reg.test(email)){
            $("#register-error").html("邮箱格式不正确！");
            $("#register-error").show();
            setTimeout(function(){$("#register-error").hide();},1000);
        }else{
            $.ajax({
                url: "/register.user",
                type: "post",
                data: {
                    username: username,
                    email: email,
                    password: password1,
                    code: code
                },
                dataType: "json",
                success: function (data) {
                    var result = data["result"];
                    if(result=="has register"){
                        $("#register-error").html("该邮箱已被注册！");
                        $("#register-error").show();
                        setTimeout(function(){$("#register-error").hide();},1000);
                    }else if(result=="wrong code"){
                        $("#register-error").html("验证码不正确！");
                        $("#register-error").show();
                        setTimeout(function(){$("#register-error").hide();},1000);
                    }else{
                        //显示注册成功
                        $("#userReg-success-modal").modal("show");
                    }
                }
            })
        }
    });

    /**
     * 转到登录界面
     */
    $("#goto-login-btn").click(function () {
        $("#userReg-success-modal").modal("hide");
        window.open("/login","_self");
    });

    $("#user-register-a").click(function () {
        $("#user-register-div").show();
        $("#institution-register-div").hide();
        $("#user-register-a").attr("class","active");
        $("#institution-register-a").removeAttr("class");
    });

    $("#institution-register-a").click(function () {
        $("#user-register-div").hide();
        $("#institution-register-div").show();
        $("#institution-register-a").attr("class","active");
        $("#user-register-a").removeAttr("class");
    });

    $("#user-login-a").click(function () {
        $("#user-login-div").show();
        $("#institution-login-div").hide();
        $("#manager-login-div").hide();
        $("#user-login-a").attr("class","active");
        $("#institution-login-a").removeAttr("class");
        $("#manager-login-a").removeAttr("class");
    });

    $("#institution-login-a").click(function () {
        $("#user-login-div").hide();
        $("#institution-login-div").show();
        $("#manager-login-div").hide();
        $("#institution-login-a").attr("class","active");
        $("#user-login-a").removeAttr("class");
        $("#manager-login-a").removeAttr("class");
    });

    $("#manager-login-a").click(function () {
        $("#user-login-div").hide();
        $("#institution-login-div").hide();
        $("#manager-login-div").show();
        $("#manager-login-a").attr("class","active");
        $("#institution-login-a").removeAttr("class");
        $("#user-login-a").removeAttr("class");
    });

    /**
     * 经理登录
     */
    $("#manager-login-btn").click(function () {
        var id = $("#enter-manager-id").val();
        var password = $("#enter-manager-password").val();
        if(id==""||password==""){
            $("#manager-login-error").html("登录信息填写不完整！");
            $("#manager-login-error").show();
            setTimeout(function () {
                $("#manager-login-error").hide();
            },1000);
        }else{
            $.ajax({
                url: "/login.manager",
                type: "post",
                data: {
                    id: id,
                    password: password
                },
                dataType: "json",
                success: function (data) {
                    var result = data["result"];
                    if(result=="wrong id"){
                        $("#manager-login-error").html("经理号错误！");
                        $("#manager-login-error").show();
                        setTimeout(function () {
                            $("#manager-login-error").hide();
                        },1000);
                    }else if(result=="wrong password"){
                        $("#manager-login-error").html("密码错误！");
                        $("#manager-login-error").show();
                        setTimeout(function () {
                            $("#manager-login-error").hide();
                        },1000);
                    }else{
                        //登录成功
                        window.open("/manager","_self");
                    }
                }
            });
        }
    });
});
