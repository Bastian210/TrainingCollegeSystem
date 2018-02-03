
'use strict';
$(function () {
    $(document).ready(function(){
        $.ajax({
            url: "/accountManagement.getMessage",
            type: "post",
            data: {},
            dataType: "json",
            success: function (data) {
                var userid = data["userid"];
                var username = data["username"];
                var gender = data["gender"];
                var education = data["education"];
                var payid = data["payid"];
                $("#userid-label").html(userid);
                $("#enter-username").val(username);
                if(gender=="男"){
                    $("#options-man").prop("checked", true);
                }else if(gender=="女"){
                    $("#options-woman").prop("checked", true);
                }
                $("#select-education").val(education);
                if(payid==""){
                    $("#bind-account-div").show();
                    $("#has-bind-div").hide();
                }else{
                    $("#bind-account-div").hide();
                    $("#account-id-span").html(payid);
                    $("#account-balance-span").html(data["balance"]);
                    $("#has-bind-div").show();
                }
            }
        });
    });

    $("#user-message-li").click(function () {
        $("#user-message-li").attr("class","active");
        $("#my-points-li").removeAttr("class");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").removeAttr("class");
        $("#chosen-li").html("个人信息");
        $("#user-message-div").show();
        $("#my-points-div").hide();
        $("#pay-account-div").hide();
        $("#account-safety-div").hide();
    });

    $("#my-points-li").click(function () {
        $("#user-message-li").removeAttr("class");
        $("#my-points-li").attr("class","active");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").removeAttr("class");
        $("#chosen-li").html("我的积分");
        $("#user-message-div").hide();
        $("#my-points-div").show();
        $("#pay-account-div").hide();
        $("#account-safety-div").hide();
    });

    $("#pay-account-li").click(function () {
        $("#user-message-li").removeAttr("class");
        $("#my-points-li").removeAttr("class");
        $("#pay-account-li").attr("class","active");
        $("#account-safety-li").removeAttr("class");
        $("#chosen-li").html("支付账号");
        $("#user-message-div").hide();
        $("#my-points-div").hide();
        $("#pay-account-div").show();
        $("#account-safety-div").hide();
    });

    $("#account-safety-li").click(function () {
        $("#user-message-li").removeAttr("class");
        $("#my-points-li").removeAttr("class");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").attr("class","active");
        $("#chosen-li").html("账号安全");
        $("#user-message-div").hide();
        $("#my-points-div").hide();
        $("#pay-account-div").hide();
        $("#account-safety-div").show();
    });

    /**
     * 保存个人信息
     */
    $("#save-usermess-btn").click(function () {
        var userid = $("#userid-label").text();
        var username = $("#enter-username").val();
        var gender = '';
        if($("#options-man").prop("checked")){
            gender = "男";
        }
        if($("#options-woman").prop("checked")){
            gender = "女";
        }
        var education = $("#select-education").val();
        $.ajax({
            url: "/accountManagement.changeMessage",
            type: "post",
            dataType: "json",
            data:{
                userid: userid,
                username: username,
                gender: gender,
                education: education,
            },
            success: function (data) {
                $("#changeMess-success-modal").modal("show");
            }
        });
    });

    /**
     * 绑定支付账号
     */
    $("#bind-account-btn").click(function () {
        var payid = $("#enter-pay-account").val();
        var password = $("#enter-account-password").val();
        if(payid==""||password==""){
            $("#bind-error").html("请将支付账号信息填写完整！");
            $("#bind-error").show();
            setTimeout(function () {
                $("#bind-error").hide();
            },1000);
        }else{
            $.ajax({
                url: "/accountManagement.bindAccount",
                type: "post",
                dataType: "json",
                data: {
                    payid: payid,
                    password: password
                },
                success: function (data) {
                    var result = data["result"];
                    if(result=="wrong id"){
                        $("#bind-error").html("此支付账号不存在!");
                        $("#bind-error").show();
                        setTimeout(function () {
                            $("#bind-error").hide();
                        },1000);
                    }else if(result=="wrong password"){
                        $("#bind-error").html("支付密码错误!");
                        $("#bind-error").show();
                        setTimeout(function () {
                            $("#bind-error").hide();
                        },1000);
                    }else{
                        $("#bind-error").attr("class","alert alert-success");
                        $("#bind-error").html("绑定成功!");
                        $("#bind-error").show();
                        setTimeout(function () {
                            $("#bind-error").hide();
                            $("#bind-account-div").hide();
                            $("#account-id-span").html(payid);
                            $("#has-bind-div").show();
                        },1000);
                    }
                }
            });
        }
    });

    $("#unbind-account-a").click(function () {
        $("#change-payPwd-div").hide();
        $("#unbind-account-modal").modal("show");
    });

    $("#change-payPwd-a").click(function () {
        $("#change-payPwd-div").show();
    });

    $("#unbind-account-btn").click(function () {
        $.ajax({
            url: "/accountManagement.unbindAccount",
            type: "post",
            data:{
                payid: $("#account-id-span").text(),
            },
            dataType: "json",
            success: function (data) {
                $("#bind-account-div").show();
                $("#has-bind-div").hide();
                $("#unbind-account-modal").modal("hide");
            }
        });
    });

    $("#change-payPwd-btn").click(function () {
        var oldPwd = $("#enter-old-payPwd").val();
        var newPwd1 = $("#enter-new-payPwd1").val();
        var newPwd2 = $("#enter-new-payPwd2").val();
        var reg = /^[0-9]*$/;
        if(oldPwd==""||newPwd1==""||newPwd2==""){
            $("#change-payPwd-error").html("密码不能为空");
            $("#change-payPwd-error").show();
            setTimeout(function () {
                $("#change-payPwd-error").hide();
            },1000);
        }else if(!reg.test(newPwd1)||newPwd1.length<4||newPwd1.length>6){
            $("#change-payPwd-error").html("密码必须为4~6位数字！");
            $("#change-payPwd-error").show();
            setTimeout(function () {
                $("#change-payPwd-error").hide();
            },1000);
        }else if(newPwd1!=newPwd2){
            $("#change-payPwd-error").html("两次密码不同！");
            $("#change-payPwd-error").show();
            setTimeout(function () {
                $("#change-payPwd-error").hide();
            },1000);
        }else{
            $.ajax({
                url: "/accountManagement.changePayPwd",
                type: "post",
                data: {
                    payid: $("#account-id-span").text(),
                    oldPwd: oldPwd,
                    newPwd: newPwd1,
                },
                dataType: "json",
                success: function (data) {
                    var result = data["result"];
                    if(result=="wrong password"){
                        $("#change-payPwd-error").html("旧密码错误！");
                        $("#change-payPwd-error").show();
                        setTimeout(function () {
                            $("#change-payPwd-error").hide();
                        },1000);
                    }else{
                        $("#change-payPwd-error").attr("class","alert alert-success");
                        $("#change-payPwd-error").html("修改成功!");
                        $("#change-payPwd-error").show();
                        setTimeout(function () {
                            $("#change-payPwd-error").hide();
                            $("#change-payPwd-div").hide();
                        },1000);
                    }
                }
            });
        }
    });
});