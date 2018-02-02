
'use strict';
$(function () {
    $("#user-message-li").click(function () {
        $("#user-message-li").attr("class","active");
        $("#my-points-li").removeAttr("class");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").removeAttr("class");
        $("#chosen-li").html("个人信息");
    });

    $("#my-points-li").click(function () {
        $("#user-message-li").removeAttr("class");
        $("#my-points-li").attr("class","active");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").removeAttr("class");
        $("#chosen-li").html("我的积分");
    });

    $("#pay-account-li").click(function () {
        $("#user-message-li").removeAttr("class");
        $("#my-points-li").removeAttr("class");
        $("#pay-account-li").attr("class","active");
        $("#account-safety-li").removeAttr("class");
        $("#chosen-li").html("支付账号");
    });

    $("#account-safety-li").click(function () {
        $("#user-message-li").removeAttr("class");
        $("#my-points-li").removeAttr("class");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").attr("class","active");
        $("#chosen-li").html("账号安全");
    });
});