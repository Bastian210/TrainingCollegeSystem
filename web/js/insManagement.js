
'use strict';
$(function () {
    $(document).ready(function () {
        $.ajax({
            url: "/insManagement.getInsMess",
            type: "post",
            dataType: "json",
            success: function (data) {
                $("#ins-id-label").html(data["id"]);
                $("#enter-ins-name").val(data["name"]);
                $("#enter-ins-address").val(data["address"]);
                $("#enter-ins-phone").val(data["phone"]);
                var payid = data["payid"];
                if(payid==""){
                    $("#bind-account-div").show();
                    $("#has-bind-div").hide();
                }else{
                    $("#bind-account-div").hide();
                    $("#has-bind-div").show();
                    $("#account-id-span").html(payid);
                    $("#account-balance-span").html(data["balance"]);
                }
            }
        });
    });
    
    $("#ins-message-li").click(function () {
        $("#ins-message-li").attr("class","active");
        $("#teachers-li").removeAttr("class");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").removeAttr("class");
        $("#chosen-li").html("机构信息");
        $("#ins-message-div").show();
        $("#teachers-div").hide();
        $("#pay-account-div").hide();
        $("#account-safety-div").hide();
    });

    $("#teachers-li").click(function () {
        $("#ins-message-li").removeAttr("class");
        $("#teachers-li").attr("class","active");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").removeAttr("class");
        $("#chosen-li").html("师资力量");
        $("#ins-message-div").hide();
        $("#teachers-div").show();
        $("#pay-account-div").hide();
        $("#account-safety-div").hide();
    });

    $("#pay-account-li").click(function () {
        $("#ins-message-li").removeAttr("class");
        $("#teachers-li").removeAttr("class");
        $("#pay-account-li").attr("class","active");
        $("#account-safety-li").removeAttr("class");
        $("#chosen-li").html("支付账号");
        $("#ins-message-div").hide();
        $("#teachers-div").hide();
        $("#pay-account-div").show();
        $("#account-safety-div").hide();
    });

    $("#account-safety-li").click(function () {
        $("#ins-message-li").removeAttr("class");
        $("#teachers-li").removeAttr("class");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").attr("class","active");
        $("#chosen-li").html("账号安全");
        $("#ins-message-div").show();
        $("#teachers-div").hide();
        $("#pay-account-div").hide();
        $("#account-safety-div").hide();
    });

    /**
     * 保存结构信息
     */
    $("#ins-message-btn").click(function () {
        var name = $("#enter-ins-name").val();
        var address = $("#enter-ins-address").val();
        var phone = $("#enter-ins-phone").val();
        var reg1 = /^1(3|4|5|7|8)\\d{9}$/;
        var reg2 = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
        if(!(reg1.test(phone)||reg2.test(phone))){
            $("#ins-message-error").html("联系方式不正确！");
            $("#ins-message-error").show();
            setTimeout(function () {
                $("#ins-message-error").hide();
            },1000);
        }else{
            $.ajax({
                url: "/insManagement.changeInsMess",
                type: "post",
                data: {
                    name: name,
                    address: address,
                    phone: phone,
                },
                dataType: "json",
                success: function (data) {
                    $("#ins-message-error").html("保存成功,等待管理员审核！");
                    $("#ins-message-error").show();
                    setTimeout(function () {
                        $("#ins-message-error").hide();
                    },1000);
                }
            });
        }
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
                url: "/insManagement.bindAccount",
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

    /**
     * 解绑支付账号
     */
    $("#unbind-account-btn").click(function () {
        $.ajax({
            url: "/insManagement.unbindAccount",
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

    /**
     * 修改支付密码
     */
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
                url: "/insManagement.changePayPwd",
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