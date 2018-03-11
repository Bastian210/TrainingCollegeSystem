
'use strict';

var indexlist;
var namelist;
var genderlist;
var typelist;

/**
 * 打开改变教师信息的模态框
 * @param name
 * @param gender
 * @param type
 */
function changeTeacher(name,gender,type) {
    $("#teacher-name-label").html(name);
    if(gender=="男"){
        $("#choose-man").prop("checked",true);
    }else{
        $("#choose-woman").prop("checked",true);
    }
    $("#enter-change-type input").attr("value",type);
    $("#change-teacher-modal").modal("show");
}

/**
 * 删除教师
 * @param name
 */
function deleteTeacher(name) {
    $("#teacher-name-span").html(name);
    $("#delete-teacher-modal").modal("show");
}

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
                $("#total-profit").html("￥"+data["profit"]);
                var payid = data["payid"];
                if(payid==""){
                    $("#bind-account-div").show();
                    $("#has-bind-div").hide();
                }else{
                    $("#bind-account-div").hide();
                    $("#has-bind-div").show();
                    $("#account-id-span").html(payid);
                    $("#account-balance-span").html("￥"+data["balance"]);
                }

                var month = data["month"];
                var profit_array = data["profit_array"];
                if(month.length>0){
                    var myChart = echarts.init(document.getElementById('profit-echarts'));

                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: "月营业额统计图"
                        },
                        tooltip: {},
                        legend: {
                            data:['营业额']
                        },
                        xAxis: {
                            data: month
                        },
                        yAxis: {},
                        series: [{
                            name: '营业额',
                            type: 'line',
                            data: profit_array
                        }]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }else{
                    $("#profit-echarts").html("<label>当前没有营业额！</label>");
                }
            }
        });

        getAllTeacher();
    });
    
    $("#ins-message-li").click(function () {
        $("#ins-message-li").attr("class","active");
        $("#teachers-li").removeAttr("class");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").removeAttr("class");
        $("#financial-situation-li").removeAttr("class");
        $("#chosen-li").html("机构信息");
        $("#ins-message-div").show();
        $("#teachers-div").hide();
        $("#pay-account-div").hide();
        $("#account-safety-div").hide();
        $("#financial-situation-div").hide();
    });

    $("#teachers-li").click(function () {
        $("#ins-message-li").removeAttr("class");
        $("#teachers-li").attr("class","active");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").removeAttr("class");
        $("#financial-situation-li").removeAttr("class");
        $("#chosen-li").html("师资力量");
        $("#ins-message-div").hide();
        $("#teachers-div").show();
        $("#pay-account-div").hide();
        $("#account-safety-div").hide();
        $("#financial-situation-div").hide();
    });

    $("#pay-account-li").click(function () {
        $("#ins-message-li").removeAttr("class");
        $("#teachers-li").removeAttr("class");
        $("#pay-account-li").attr("class","active");
        $("#account-safety-li").removeAttr("class");
        $("#financial-situation-li").removeAttr("class");
        $("#chosen-li").html("支付账号");
        $("#ins-message-div").hide();
        $("#teachers-div").hide();
        $("#pay-account-div").show();
        $("#account-safety-div").hide();
        $("#financial-situation-div").hide();
    });

    $("#account-safety-li").click(function () {
        $("#ins-message-li").removeAttr("class");
        $("#teachers-li").removeAttr("class");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").attr("class","active");
        $("#financial-situation-li").removeAttr("class");
        $("#chosen-li").html("账号安全");
        $("#ins-message-div").hide();
        $("#teachers-div").hide();
        $("#pay-account-div").hide();
        $("#account-safety-div").show();
        $("#financial-situation-div").hide();
    });

    $("#financial-situation-li").click(function () {
        $("#ins-message-li").removeAttr("class");
        $("#teachers-li").removeAttr("class");
        $("#pay-account-li").removeAttr("class");
        $("#account-safety-li").removeAttr("class","active");
        $("#financial-situation-li").attr("class","active");
        $("#chosen-li").html("财务情况");
        $("#ins-message-div").hide();
        $("#teachers-div").hide();
        $("#pay-account-div").hide();
        $("#account-safety-div").hide();
        $("#financial-situation-div").show();
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
                url: "/insManagement.changePassword",
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

    $("#show-add-teacher-btn").click(function () {
        $("#add-teacher-div").show();
        $("#display-teachers-div").hide();
    });

    $("#cancel-add-btn").click(function () {
        $("#add-teacher-div").hide();
        $("#display-teachers-div").show();
        clearAddTeacherDiv();
    });

    /**
     * 添加教师
     */
    $("#add-teacher-btn").click(function () {
        var name = $("#enter-teacher-name").val();
        var gender = "";
        if($("#man").prop("checked")){
            gender = "男";
        }
        if($("#woman").prop("checked")){
            gender = "女";
        }
        var type = $("#cascader input").attr("value");
        if(name==""||gender==""||type==""){
            $("#add-teacher-error").html("教师信息填写不完整！");
            $("#add-teacher-error").show();
            setTimeout(function () {
                $("#add-teacher-error").hide();
            },1000);
        }else{
            $.ajax({
                url: "/insManagement.addTeacher",
                type: "post",
                data: {
                    name: name,
                    gender: gender,
                    type: type
                },
                dataType: "json",
                success: function (data) {
                    var result = data["result"];
                    if(result=="success"){
                        $("#add-teacher-div").hide();
                        $("#display-teachers-div").show();
                        getAllTeacher();
                        clearAddTeacherDiv();
                    }else{
                        $("#add-teacher-error").html("已经添加了此教师！");
                        $("#add-teacher-error").show();
                        setTimeout(function () {
                            $("#add-teacher-error").hide();
                        },1000);
                    }
                }
            });
        }
    });

    /**
     * 得到所有教师名单
     */
    function getAllTeacher() {
        $.ajax({
            url: "/insManagement.getTeacher",
            type: "post",
            dataType: "json",
            success: function (data) {
                indexlist = data["index"];
                namelist = data["name"];
                genderlist = data["gender"];
                typelist = data["type"];

                if(indexlist.length===0){
                    $("#none-teachers-p").show();
                    $(".display-table").hide();
                }else{
                    $("#none-teachers-p").hide();
                    $(".display-table").show();
                    if(indexlist.length>10){
                        var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"10\" :total=\""+indexlist.length+"\" @current-change=\"handleCurrentChange\">\n" +
                            "                </el-pagination>";
                        $("#my-pagination").html(content);

                        var Main = {
                            methods: {
                                handleCurrentChange(val) {
                                    PagingDisplay(val);
                                }
                            }
                        }
                        var Ctor = Vue.extend(Main)
                        new Ctor().$mount('#my-pagination')
                    }

                    PagingDisplay(1);
                }
            }
        });
    }

    /**
     * 分页展示教师信息
     * @param val
     * @constructor
     */
    function PagingDisplay(val) {
        var content = "";

        var begin = (val-1)*10;
        var end = 0;
        if(indexlist.length-begin<10){
            end = indexlist.length;
        }else{
            end = begin+10;
        }
        for(var i=begin;i<end;i++){
            content = content+"<tr><td>"+indexlist[i]+"</td><td>"+namelist[i]+"</td><td>"+genderlist[i]
                +"</td><td>"+typelist[i]+"</td><td><a onclick=\"changeTeacher('"+namelist[i]+"','"
                +genderlist[i]+"','"+typelist[i]+"')\">修改</a><a onclick=\"deleteTeacher('"+namelist[i]+"')\">删除</a></td></tr>";
        }
        $("#display-content").html(content);
    }

    /**
     * 确认修改教师信息
     */
    $("#change-teacher-btn").click(function () {
        var name = $("#teacher-name-label").text();
        var gender = "";
        if($("#choose-man").prop("checked")){
            gender = "男";
        }else{
            gender = "女";
        }
        var type = $("#enter-change-type input").attr("value");
        $.ajax({
            url: "/insManagement.changeTeacherMess",
            type: "post",
            data:{
                name: name,
                gender: gender,
                type: type,
            },
            dataType: "json",
            success: function (data) {
                $("#change-teacher-modal").modal("hide");
                getAllTeacher();
            }
        });
    });

    /**
     * 确认删除教师
     */
    $("#delete-teacher-btn").click(function () {
        $.ajax({
            url: "/insManagement.deleteTeacher",
            type: "post",
            data: {
                name: $("#teacher-name-span").text(),
            },
            dataType: "json",
            success: function (data) {
                $("#delete-teacher-modal").modal("hide");
                getAllTeacher();
            }
        });
    });

    /**
     * 清空添加教师输入框中的内容
     */
    function clearAddTeacherDiv() {
        $("#enter-teacher-name").val("");
        $("#man").removeAttr("checked");
        $("#woman").removeAttr("checked");
        $("#cascader input").val("");
    }
});