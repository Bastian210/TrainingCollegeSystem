
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
});