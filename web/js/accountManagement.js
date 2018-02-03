
'use strict';
$(function () {
    $(document).ready(function(){
        $.ajax({
            url: "/accountManagement.getMessage",
            type: "post",
            data: {},
            dataType: "json",
            success: function (data) {
                console.log(data);
                var userid = data["userid"];
                var username = data["username"];
                var gender = data["gender"];
                var education = data["education"];
                $("#userid-label").html(userid);
                $("#enter-username").val(username);
                if(gender=="男"){
                    $("#options-man").prop("checked", true);
                }else if(gender=="女"){
                    $("#options-woman").prop("checked", true);
                }
                $("#select-education").val(education);
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
});