
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
                    }else{
                        //显示注册成功
                    }
                }
            })
        }
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
                        console.log(result);
                        //显示识别码，并让其等待审核
                    }
                }
            });
        }
    });
});
