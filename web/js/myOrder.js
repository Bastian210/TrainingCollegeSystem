
'use strict'

var orderid = "";
var checkbox = "";
var price = 0;

/**
 * 取消订单
 * @param orderid
 */
function cancelOrder(id) {
    $("#cancel-order-modal").modal("show");
    orderid = id;
}

/**
 * 支付订单
 * @param orderid
 * @param price
 * @param checkbox
 */
function payOrder(id,pri,check) {
    $("#pay-order-modal").modal("show");
    orderid = id;
    price = pri;
    checkbox = check;
}

/**
 * 删除订单
 * @param id
 */
function deleteOrder(id) {
    $("#delete-order-modal").modal("show");
    orderid = id;
}

$(function () {
    $(document).ready(function () {
       getAllOrder();
    });

    /**
     * 得到所有订单
     */
    function getAllOrder() {
        $.ajax({
            url: "/myOrder.getAllOrder",
            type: "post",
            dataType: "json",
            success: function (data) {
                var result = data["result"];
                console.log(result);
                var content = "";
                var content1 = "";
                var content2 = "";
                var content3 = "";
                var content4 = "";
                for(var i=0;i<result.length;i++){
                    var json = JSON.parse(JSON.stringify(result[i]));
                    console.log(json);
                    var state = json["state"];
                    var nameList = json["nameList"];
                    var genderList = json["genderList"];
                    var educationList = json["educationList"];
                    var classidList = json["classidList"];
                    console.log(state);
                    if(state=="未支付"){
                        content1 = content1+"<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">"+json["time"]+"</label>\n" +
                            "                    <label class=\"label1\">订单号："+json["orderid"]+"</label>\n" +
                            "                    <label class=\"label1\">"+json["institutionname"]+"</label>\n" +
                            "                    <br>\n" +
                            "                    <label class=\"label2\">"+json["lessonname"]+"</label>\n";
                        content = content+"<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">"+json["time"]+"</label>\n" +
                            "                    <label class=\"label1\">订单号："+json["orderid"]+"</label>\n" +
                            "                    <label class=\"label1\">"+json["institutionname"]+"</label>\n" +
                            "                    <br>\n" +
                            "                    <label class=\"label2\">"+json["lessonname"]+"</label>\n";
                        if(json["type"]=="选班级"){
                            content1 = content1+"<label class=\"label2\">选班级："+json["classtype"]+"</label>\n";
                            content = content+"<label class=\"label2\">选班级："+json["classtype"]+"</label>\n";
                        }else{
                            content1 = content1+"<label class=\"label2\">不选班级</label>\n";
                            content = content+"<label class=\"label2\">不选班级</label>\n";
                        }
                        content1 = content1+ "<label class=\"label2\">学员数量："+json["num"]+"</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥"+json["price"]+"</span><span>￥"+json["actualpay"]+"</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：未支付</label>\n" +
                            "                    <a onclick=\"payOrder('"+json["orderid"]+"',"+json["actualpay"]+",'"+json["checkbox"]+"')\">立即付款</a>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";
                        content = content+ "<label class=\"label2\">学员数量："+json["num"]+"</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥"+json["price"]+"</span><span>￥"+json["actualpay"]+"</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：未支付</label>\n" +
                            "                    <a onclick=\"payOrder('"+json["orderid"]+"',"+json["actualpay"]+",'"+json["checkbox"]+"')\">立即付款</a>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";

                        for(var k=0;k<nameList.length;k++){
                            content1 = content1+" <tr><td>"+nameList[k]+"</td><td>"+genderList[k]+"</td><td>"+educationList[k]+"</td><td>"+classidList[k]+"</td></tr>";
                            content = content+" <tr><td>"+nameList[k]+"</td><td>"+genderList[k]+"</td><td>"+educationList[k]+"</td><td>"+classidList[k]+"</td></tr>";
                        }
                        content1 = content1+"</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                        content = content+"</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                    }else if(state=="已预订"||state=="等待配票"){
                        content2 = content2+"<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">"+json["time"]+"</label>\n" +
                            "                    <label class=\"label1\">订单号："+json["orderid"]+"</label>\n" +
                            "                    <label class=\"label1\">"+json["institutionname"]+"</label>\n" +
                            "                    <br>\n" +
                            "                    <label class=\"label2\">"+json["lessonname"]+"</label>\n";
                        content = content+"<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">"+json["time"]+"</label>\n" +
                            "                    <label class=\"label1\">订单号："+json["orderid"]+"</label>\n" +
                            "                    <label class=\"label1\">"+json["institutionname"]+"</label>\n" +
                            "                    <br>\n" +
                            "                    <label class=\"label2\">"+json["lessonname"]+"</label>\n";
                        if(json["type"]=="选班级"){
                            content2 = content2+"<label class=\"label2\">选班级："+json["classtype"]+"</label>\n";
                            content = content+"<label class=\"label2\">选班级："+json["classtype"]+"</label>\n";
                        }else{
                            content2 = content2+"<label class=\"label2\">不选班级</label>\n";
                            content = content+"<label class=\"label2\">不选班级</label>\n";
                        }
                        content2 = content2+ "<label class=\"label2\">学员数量："+json["num"]+"</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥"+json["price"]+"</span><span>￥"+json["actualpay"]+"</span></label>\n" +
                            "                    <label class=\"label2\">订单状态："+json["state"]+"</label>\n" +
                            "                    <a onclick=\"cancelOrder('"+json["orderid"]+"',)\">立即取消</a>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";
                        content = content+ "<label class=\"label2\">学员数量："+json["num"]+"</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥"+json["price"]+"</span><span>￥"+json["actualpay"]+"</span></label>\n" +
                            "                    <label class=\"label2\">订单状态："+json["state"]+"</label>\n" +
                            "                    <a onclick=\"cancelOrder('"+json["orderid"]+"',)\">立即取消</a>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";

                        for(var k=0;k<nameList.length;k++){
                            content2 = content2+" <tr><td>"+nameList[k]+"</td><td>"+genderList[k]+"</td><td>"+educationList[k]+"</td><td>"+classidList[k]+"</td></tr>";
                            content = content+" <tr><td>"+nameList[k]+"</td><td>"+genderList[k]+"</td><td>"+educationList[k]+"</td><td>"+classidList[k]+"</td></tr>";
                        }
                        content2 = content2+"</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                        content = content+"</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                    }else if(state=="已退订"){
                        content3 = content3+"<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">"+json["time"]+"</label>\n" +
                            "                    <label class=\"label1\">订单号："+json["orderid"]+"</label>\n" +
                            "                    <label class=\"label1\">"+json["institutionname"]+"</label>\n" +
                            "                    <i class=\"el-icon-delete\" onclick=\"deleteOrder('"+json["orderid"]+"')\"></i>\n"+
                            "                    <br>\n" +
                            "                    <label class=\"label2\">"+json["lessonname"]+"</label>\n";
                        content = content+"<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">"+json["time"]+"</label>\n" +
                            "                    <label class=\"label1\">订单号："+json["orderid"]+"</label>\n" +
                            "                    <label class=\"label1\">"+json["institutionname"]+"</label>\n" +
                            "                    <i class=\"el-icon-delete\" onclick=\"deleteOrder('"+json["orderid"]+"')\"></i>\n"+
                            "                    <br>\n" +
                            "                    <label class=\"label2\">"+json["lessonname"]+"</label>\n";
                        if(json["type"]=="选班级"){
                            content3 = content3+"<label class=\"label2\">选班级："+json["classtype"]+"</label>\n";
                            content = content+"<label class=\"label2\">选班级："+json["classtype"]+"</label>\n";
                        }else{
                            content3 = content3+"<label class=\"label2\">不选班级</label>\n";
                            content = content+"<label class=\"label2\">不选班级</label>\n";
                        }
                        content3 = content3+ "<label class=\"label2\">学员数量："+json["num"]+"</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥"+json["price"]+"</span><span>￥"+json["actualpay"]+"</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：已退订</label>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";
                        content = content+ "<label class=\"label2\">学员数量："+json["num"]+"</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥"+json["price"]+"</span><span>￥"+json["actualpay"]+"</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：已退订</label>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";

                        for(var k=0;k<nameList.length;k++){
                            content3 = content3+" <tr><td>"+nameList[k]+"</td><td>"+genderList[k]+"</td><td>"+educationList[k]+"</td><td>"+classidList[k]+"</td></tr>";
                            content = content+" <tr><td>"+nameList[k]+"</td><td>"+genderList[k]+"</td><td>"+educationList[k]+"</td><td>"+classidList[k]+"</td></tr>";
                        }
                        content3 = content3+"</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                        content = content+"</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                    }else if(state=="已完成"){
                        content4 = content4+"<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">"+json["time"]+"</label>\n" +
                            "                    <label class=\"label1\">订单号："+json["orderid"]+"</label>\n" +
                            "                    <label class=\"label1\">"+json["institutionname"]+"</label>\n" +
                            "                    <i class=\"el-icon-delete\" onclick=\"deleteOrder('"+json["orderid"]+"')\"></i>\n"+
                            "                    <br>\n" +
                            "                    <label class=\"label2\">"+json["lessonname"]+"</label>\n";
                        content = content+"<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">"+json["time"]+"</label>\n" +
                            "                    <label class=\"label1\">订单号："+json["orderid"]+"</label>\n" +
                            "                    <label class=\"label1\">"+json["institutionname"]+"</label>\n" +
                            "                    <i class=\"el-icon-delete\" onclick=\"deleteOrder('"+json["orderid"]+"')\"></i>\n"+
                            "                    <br>\n" +
                            "                    <label class=\"label2\">"+json["lessonname"]+"</label>\n";
                        if(json["type"]=="选班级"){
                            content4 = content4+"<label class=\"label2\">选班级："+json["classtype"]+"</label>\n";
                            content = content+"<label class=\"label2\">选班级："+json["classtype"]+"</label>\n";
                        }else{
                            content4 = content4+"<label class=\"label2\">不选班级</label>\n";
                            content = content+"<label class=\"label2\">不选班级</label>\n";
                        }
                        content4 = content4+ "<label class=\"label2\">学员数量："+json["num"]+"</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥"+json["price"]+"</span><span>￥"+json["actualpay"]+"</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：未支付</label>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";
                        content = content+ "<label class=\"label2\">学员数量："+json["num"]+"</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥"+json["price"]+"</span><span>￥"+json["actualpay"]+"</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：未支付</label>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";

                        for(var k=0;k<nameList.length;k++){
                            content4 = content4+" <tr><td>"+nameList[k]+"</td><td>"+genderList[k]+"</td><td>"+educationList[k]+"</td><td>"+classidList[k]+"</td></tr>";
                            content = content+" <tr><td>"+nameList[k]+"</td><td>"+genderList[k]+"</td><td>"+educationList[k]+"</td><td>"+classidList[k]+"</td></tr>";
                        }
                        content4 = content4+"</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                        content = content+"</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                    }
                }

                $("#all-order-div").html(content);
                $("#notpay-order-div").html(content1);
                $("#hasbook-order-div").html(content2);
                $("#unsubscribe-order-div").html(content3);
                $("#finish-order-div").html(content4);
                new Vue().$mount(".main-content");
            }
        });
    }
    
    $("#all-order-a").click(function () {
        $("#all-order-a").attr("class","active");
        $("#notpay-order-a").removeAttr("class");
        $("#hasbook-order-a").removeAttr("class");
        $("#unsubscribe-order-a").removeAttr("class");
        $("#finish-order-a").removeAttr("class");
        $("#all-order-div").show();
        $("#notpay-order-div").hide();
        $("#hasbook-order-div").hide();
        $("#unsubscribe-order-div").hide();
        $("#finish-order-div").hide();
    });
    
    $("#notpay-order-a").click(function () {
        $("#all-order-a").removeAttr("class");
        $("#notpay-order-a").attr("class","active");
        $("#hasbook-order-a").removeAttr("class");
        $("#unsubscribe-order-a").removeAttr("class");
        $("#finish-order-a").removeAttr("class");
        $("#all-order-div").hide();
        $("#notpay-order-div").show();
        $("#hasbook-order-div").hide();
        $("#unsubscribe-order-div").hide();
        $("#finish-order-div").hide();
    });
    
    $("#hasbook-order-a").click(function () {
        $("#all-order-a").removeAttr("class");
        $("#notpay-order-a").removeAttr("class");
        $("#hasbook-order-a").attr("class","active");
        $("#unsubscribe-order-a").removeAttr("class");
        $("#finish-order-a").removeAttr("class");
        $("#all-order-div").hide();
        $("#notpay-order-div").hide();
        $("#hasbook-order-div").show();
        $("#unsubscribe-order-div").hide();
        $("#finish-order-div").hide();
    });
    
    $("#unsubscribe-order-a").click(function () {
        $("#all-order-a").removeAttr("class");
        $("#notpay-order-a").removeAttr("class");
        $("#hasbook-order-a").removeAttr("class");
        $("#unsubscribe-order-a").attr("class","active");
        $("#finish-order-a").removeAttr("class");
        $("#all-order-div").hide();
        $("#notpay-order-div").hide();
        $("#hasbook-order-div").hide();
        $("#unsubscribe-order-div").show();
        $("#finish-order-div").hide();
    });
    
    $("#finish-order-a").click(function () {
        $("#all-order-a").removeAttr("class");
        $("#notpay-order-a").removeAttr("class");
        $("#hasbook-order-a").removeAttr("class");
        $("#unsubscribe-order-a").removeAttr("class");
        $("#finish-order-a").attr("class","active");
        $("#all-order-div").hide();
        $("#notpay-order-div").hide();
        $("#hasbook-order-div").hide();
        $("#unsubscribe-order-div").hide();
        $("#finish-order-div").show();
    });

    $("#pay-order-btn").click(function () {
        var password = $("#enter-pay-password").val();
        if(password==""){
            $("#pay-order-error").html("请填写支付密码！");
            $("#pay-order-error").show();
            setTimeout(function () {
                $("#pay-order-error").hide();
            },1000);
        }else{
            $.ajax({
                url: "/book.payOrder",
                type: "post",
                data: {
                    price: price,
                    password: password,
                    orderid: orderid,
                    checkbox: checkbox,
                },
                dataType: "json",
                success: function (data) {
                    var result = data["result"];
                    if(result=="wrong password"){
                        $("#pay-order-error").html("支付密码错误！");
                        $("#pay-order-error").show();
                        setTimeout(function () {
                            $("#pay-order-error").hide();
                        },1000);
                    }else if(result=="not enough"){
                        $("#pay-order-error").html("余额不足！");
                        $("#pay-order-error").show();
                        setTimeout(function () {
                            $("#pay-order-error").hide();
                        },1000);
                    }else if(result=="has end"){
                        $("#pay-order-error").html("已过截止时间，订单已取消！");
                        $("#pay-order-error").show();
                        setTimeout(function () {
                            $("#pay-order-error").hide();
                        },1000);
                    }else{
                        $("#pay-order-error").html("支付完成！");
                        $("#pay-order-error").show();
                        setTimeout(function () {
                            $("#pay-order-error").hide();
                        },1000);
                        getAllOrder();
                    }
                }
            });
        }
    });

    /**
     * 取消订单
     */
    $("#cancel-order-btn").click(function () {
        $.ajax({
            url: "/myOrder.cancelOrder",
            type: "post",
            dataType: "json",
            data: {
                orderid: orderid,
            },
            success: function (data) {
                $("#cancel-order-modal").modal("hide");
                getAllOrder();
            }
        });
    });

    /**
     * 删除订单
     */
    $("#delete-plan-btn").click(function () {
        $.ajax({
            url: "/myOrder.deleteOrder",
            type: "post",
            dataType: "json",
            data: {
                orderid: orderid,
            },
            success: function (data) {
                $("#delete-order-modal").modal("hide");
                getAllOrder();
            }
        });
    });
});