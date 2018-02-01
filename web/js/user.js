
'use strict';
$(function () {
    $(document).ready(function(){
    });

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
                url:"/login.action",
                dataType: 'json',
                type: 'post',
                data: {
                    email: email,
                    password: password,
                },
                success: function (data) {
                    var result = data["result"];
                    if(result=="wrong email"){
                        $("#login-error").html("该邮箱没有被注册！");
                        $("#login-error").show();
                        setTimeout(function(){$("#login-error").hide();},1000);
                    }
                },
            });
        }
    });

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
            $.ajax({
                url: "/getCode.action",
                type: "post",
                dataType: "json",
                data:{
                    email: email
                },
                success: function (data) {
                    $("#send-code-btn").attr("disabled",true);
                    $("#send-code-btn").attr("class","my-button1 disable-button");
                    setTimeout(function () {
                        $("#send-code-btn").attr("class","my-button1");
                        $("#send-code-btn").removeAttr("disabled");
                    },10000);
                }
            });
        }
    });

    $("#register-btn").click(function () {
        var username = $("#enter-username").val();
        var email = $("#enter-email").val();
        var password1 = $("#enter-password1").val();
        var password2 = $("#enter-password2").val();
        var code = $("#enter-code").val();
        var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        if(username==""||email==""||password1==""||password2==""){
            $("#register-error").html("用户名、密码、邮箱不能为空！");
            $("#register-error").show();
            setTimeout(function(){$("#register-error").hide();},1000);
        }else if(code==""){
            $("#register-error").html("验证码不能为空！");
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
                url: "/register.action",
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
                    }
                }
            })
        }
    });
});
