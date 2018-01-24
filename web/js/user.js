
'use strict';
$(function () {
    $(document).ready(function(){
    });

    $("#login-btn").click(function(){
        var email = $("#enter-email").val();
        var password = $("#enter-password").val();
        var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        if(email==""&&password==""){
            alert("邮箱和密码不能为空！");
        }else if(email==""){
            alert("邮箱不能为空！");
        }else if(password==""){
            alert("密码不能为空！");
        }else if(!reg.test(email)){
            alert("邮箱格式不正确！");
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

                    }
                },
            });
        }
    });
});
