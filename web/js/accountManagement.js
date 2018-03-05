
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
                var month = data["month"];
                var consumption = data["consume"];
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
                $("#level-label").html(data["level"]+"级");
                $("#points-label").html(data["points"]+"点");
                $("#consumption-label").html(data["consumption"]+"元");

                var myChart = echarts.init(document.getElementById('consume-statistics-div'));

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: "您的月消费额统计图"
                    },
                    tooltip: {},
                    legend: {
                        data:['消费额']
                    },
                    xAxis: {
                        data: month
                    },
                    yAxis: {},
                    series: [{
                        name: '消费额',
                        type: 'line',
                        data: consumption
                    }]
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        });
    });

    $("#user-message-li").click(function () {
        $("#user-message-li").attr("class","active");
        $("#my-points-li").removeAttr("class");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").removeAttr("class");
        $("#consume-statistics-li").removeAttr("class");
        $("#chosen-li").html("个人信息");
        $("#user-message-div").show();
        $("#my-points-div").hide();
        $("#pay-account-div").hide();
        $("#account-safety-div").hide();
        $("#consume-statistics-div").hide();
    });

    $("#my-points-li").click(function () {
        $("#user-message-li").removeAttr("class");
        $("#my-points-li").attr("class","active");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").removeAttr("class");
        $("#consume-statistics-li").removeAttr("class");
        $("#chosen-li").html("我的积分");
        $("#user-message-div").hide();
        $("#my-points-div").show();
        $("#pay-account-div").hide();
        $("#account-safety-div").hide();
        $("#consume-statistics-div").hide();
    });

    $("#pay-account-li").click(function () {
        $("#user-message-li").removeAttr("class");
        $("#my-points-li").removeAttr("class");
        $("#pay-account-li").attr("class","active");
        $("#account-safety-li").removeAttr("class");
        $("#consume-statistics-li").removeAttr("class");
        $("#chosen-li").html("支付账号");
        $("#user-message-div").hide();
        $("#my-points-div").hide();
        $("#pay-account-div").show();
        $("#account-safety-div").hide();
        $("#consume-statistics-div").hide();
    });

    $("#account-safety-li").click(function () {
        $("#user-message-li").removeAttr("class");
        $("#my-points-li").removeAttr("class");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").attr("class","active");
        $("#consume-statistics-li").removeAttr("class");
        $("#chosen-li").html("账号安全");
        $("#user-message-div").hide();
        $("#my-points-div").hide();
        $("#pay-account-div").hide();
        $("#account-safety-div").show();
        $("#consume-statistics-div").hide();
    });

    $("#consume-statistics-li").click(function () {
        $("#user-message-li").removeAttr("class");
        $("#my-points-li").removeAttr("class");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").removeAttr("class");
        $("#consume-statistics-li").attr("class","active");
        $("#chosen-li").html("账号安全");
        $("#user-message-div").hide();
        $("#my-points-div").hide();
        $("#pay-account-div").hide();
        $("#account-safety-div").hide();
        $("#consume-statistics-div").show();
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

    /**
     * 解绑支付账号
     */
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

    $("#change-password-a").click(function () {
        $("#change-password-div").show();
    });

    $("#write-off-a").click(function () {
        $("#change-password-div").hide();
        $("#write-off-modal").modal("show");
    });

    /**
     * 修改登录密码
     */
    $("#change-password-btn").click(function () {
        var oldpassword = $("#enter-old-password").val();
        var newpassword1 = $("#enter-new-password1").val();
        var newpassword2 = $("#enter-new-password2").val();
        if(oldpassword==""||newpassword1==""||newpassword2==""){
            $("#change-password-error").html("密码不能为空！");
            $("#change-password-error").show();
            setTimeout(function () {
                $("#change-password-error").hide();
            },1000);
        }else if(newpassword1!=newpassword2){
            $("#change-password-error").html("两次新密码不相同！");
            $("#change-password-error").show();
            setTimeout(function () {
                $("#change-password-error").hide();
            },1000);
        }else if(newpassword1.length<6||newpassword1.length>16){
            $("#change-password-error").html("密码长度不合格！");
            $("#change-password-error").show();
            setTimeout(function () {
                $("#change-password-error").hide();
            },1000);
        }else{
            $.ajax({
                url: "/accountManagement.changePassword",
                type: "post",
                data: {
                    oldpassword: oldpassword,
                    newpassword: newpassword1
                },
                dataType: "json",
                success: function (data) {
                    var result = data["result"];
                    if(result=="wrong password"){
                        $("#change-password-error").html("旧密码错误！");
                        $("#change-password-error").show();
                        setTimeout(function () {
                            $("#change-password-error").hide();
                        },1000);
                    }else{
                        $("#change-password-error").attr("class","alert alert-success");
                        $("#change-password-error").html("修改成功！");
                        $("#change-password-error").show();
                        setTimeout(function () {
                            $("#change-password-error").hide();
                        },1000);
                    }
                }
            });
        }
    });

    /**
     * 注销用户
     */
    $("#write-off-btn").click(function () {
        $.ajax({
            url: "/accountManagement.writeOff",
            type: "post",
            dataType: "json",
            success: function (data) {
                $("#write-off-modal").modal("hide");
                setTimeout(function () {
                    window.open("/register","_self");
                },500);
            }
        });
    });
});