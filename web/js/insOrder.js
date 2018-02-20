
'use strict'

$(function () {
    $(document).ready(function () {
        $.ajax({
            url: "/insOrder.getAllInsOrder",
            type: "post",
            dataType: "json",
            success: function (data) {
                var result = data["result"];

                var content = "";
                var content1 = "";
                var content2 = "";
                var content3 = "";
                var content4 = "";
                for (var i = 0; i < result.length; i++) {
                    var json = JSON.parse(JSON.stringify(result[i]));

                    var state = json["state"];
                    var nameList = json["nameList"];
                    var genderList = json["genderList"];
                    var educationList = json["educationList"];
                    var classidList = json["classidList"];

                    if (state == "未支付") {
                        content1 = content1 + "<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">" + json["time"] + "</label>\n" +
                            "                    <label class=\"label1\">订单号：" + json["orderid"] + "</label>\n" +
                            "                    <label class=\"label1\">" + json["username"] + "</label>\n" +
                            "                    <br>\n" +
                            "                    <label class=\"label2\">" + json["lessonname"] + "</label>\n";
                        content = content + "<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">" + json["time"] + "</label>\n" +
                            "                    <label class=\"label1\">订单号：" + json["orderid"] + "</label>\n" +
                            "                    <label class=\"label1\">" + json["username"] + "</label>\n" +
                            "                    <br>\n" +
                            "                    <label class=\"label2\">" + json["lessonname"] + "</label>\n";
                        if (json["type"] == "选班级") {
                            content1 = content1 + "<label class=\"label2\">选班级：" + json["classtype"] + "</label>\n";
                            content = content + "<label class=\"label2\">选班级：" + json["classtype"] + "</label>\n";
                        } else {
                            content1 = content1 + "<label class=\"label2\">不选班级</label>\n";
                            content = content + "<label class=\"label2\">不选班级</label>\n";
                        }
                        content1 = content1 + "<label class=\"label2\">学员数量：" + json["num"] + "</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥" + json["price"] + "</span><span>￥" + json["actualpay"] + "</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：未支付</label>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";
                        content = content + "<label class=\"label2\">学员数量：" + json["num"] + "</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥" + json["price"] + "</span><span>￥" + json["actualpay"] + "</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：未支付</label>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";

                        for (var k = 0; k < nameList.length; k++) {
                            content1 = content1 + " <tr><td>" + nameList[k] + "</td><td>" + genderList[k] + "</td><td>" + educationList[k] + "</td><td>" + classidList[k] + "</td></tr>";
                            content = content + " <tr><td>" + nameList[k] + "</td><td>" + genderList[k] + "</td><td>" + educationList[k] + "</td><td>" + classidList[k] + "</td></tr>";
                        }
                        content1 = content1 + "</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                        content = content + "</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                    } else if (state == "已预订" || state == "等待配票") {
                        content2 = content2 + "<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">" + json["time"] + "</label>\n" +
                            "                    <label class=\"label1\">订单号：" + json["orderid"] + "</label>\n" +
                            "                    <label class=\"label1\">" + json["username"] + "</label>\n" +
                            "                    <br>\n" +
                            "                    <label class=\"label2\">" + json["lessonname"] + "</label>\n";
                        content = content + "<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">" + json["time"] + "</label>\n" +
                            "                    <label class=\"label1\">订单号：" + json["orderid"] + "</label>\n" +
                            "                    <label class=\"label1\">" + json["username"] + "</label>\n" +
                            "                    <br>\n" +
                            "                    <label class=\"label2\">" + json["lessonname"] + "</label>\n";
                        if (json["type"] == "选班级") {
                            content2 = content2 + "<label class=\"label2\">选班级：" + json["classtype"] + "</label>\n";
                            content = content + "<label class=\"label2\">选班级：" + json["classtype"] + "</label>\n";
                        } else {
                            content2 = content2 + "<label class=\"label2\">不选班级</label>\n";
                            content = content + "<label class=\"label2\">不选班级</label>\n";
                        }
                        content2 = content2 + "<label class=\"label2\">学员数量：" + json["num"] + "</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥" + json["price"] + "</span><span>￥" + json["actualpay"] + "</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：" + json["state"] + "</label>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";
                        content = content + "<label class=\"label2\">学员数量：" + json["num"] + "</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥" + json["price"] + "</span><span>￥" + json["actualpay"] + "</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：" + json["state"] + "</label>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";

                        for (var k = 0; k < nameList.length; k++) {
                            content2 = content2 + " <tr><td>" + nameList[k] + "</td><td>" + genderList[k] + "</td><td>" + educationList[k] + "</td><td>" + classidList[k] + "</td></tr>";
                            content = content + " <tr><td>" + nameList[k] + "</td><td>" + genderList[k] + "</td><td>" + educationList[k] + "</td><td>" + classidList[k] + "</td></tr>";
                        }
                        content2 = content2 + "</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                        content = content + "</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                    } else if (state == "已退订" || state == "配票失败") {
                        content3 = content3 + "<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">" + json["time"] + "</label>\n" +
                            "                    <label class=\"label1\">订单号：" + json["orderid"] + "</label>\n" +
                            "                    <label class=\"label1\">" + json["username"] + "</label>\n" +
                            "                    <br>\n" +
                            "                    <label class=\"label2\">" + json["lessonname"] + "</label>\n";
                        content = content + "<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">" + json["time"] + "</label>\n" +
                            "                    <label class=\"label1\">订单号：" + json["orderid"] + "</label>\n" +
                            "                    <label class=\"label1\">" + json["username"] + "</label>\n" +
                            "                    <br>\n" +
                            "                    <label class=\"label2\">" + json["lessonname"] + "</label>\n";
                        if (json["type"] == "选班级") {
                            content3 = content3 + "<label class=\"label2\">选班级：" + json["classtype"] + "</label>\n";
                            content = content + "<label class=\"label2\">选班级：" + json["classtype"] + "</label>\n";
                        } else {
                            content3 = content3 + "<label class=\"label2\">不选班级</label>\n";
                            content = content + "<label class=\"label2\">不选班级</label>\n";
                        }
                        content3 = content3 + "<label class=\"label2\">学员数量：" + json["num"] + "</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥" + json["price"] + "</span><span>￥" + json["actualpay"] + "</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：" + state + "</label>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";
                        content = content + "<label class=\"label2\">学员数量：" + json["num"] + "</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥" + json["price"] + "</span><span>￥" + json["actualpay"] + "</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：" + state + "</label>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";

                        for (var k = 0; k < nameList.length; k++) {
                            content3 = content3 + " <tr><td>" + nameList[k] + "</td><td>" + genderList[k] + "</td><td>" + educationList[k] + "</td><td>" + classidList[k] + "</td></tr>";
                            content = content + " <tr><td>" + nameList[k] + "</td><td>" + genderList[k] + "</td><td>" + educationList[k] + "</td><td>" + classidList[k] + "</td></tr>";
                        }
                        content3 = content3 + "</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                        content = content + "</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                    } else if (state == "已完成") {
                        content4 = content4 + "<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">" + json["time"] + "</label>\n" +
                            "                    <label class=\"label1\">订单号：" + json["orderid"] + "</label>\n" +
                            "                    <label class=\"label1\">" + json["username"] + "</label>\n" +
                            "                    <br>\n" +
                            "                    <label class=\"label2\">" + json["lessonname"] + "</label>\n";
                        content = content + "<div class=\"show-one-order\">\n" +
                            "                    <label class=\"label1\">" + json["time"] + "</label>\n" +
                            "                    <label class=\"label1\">订单号：" + json["orderid"] + "</label>\n" +
                            "                    <label class=\"label1\">" + json["username"] + "</label>\n" +
                            "                    <br>\n" +
                            "                    <label class=\"label2\">" + json["lessonname"] + "</label>\n";
                        if (json["type"] == "选班级") {
                            content4 = content4 + "<label class=\"label2\">选班级：" + json["classtype"] + "</label>\n";
                            content = content + "<label class=\"label2\">选班级：" + json["classtype"] + "</label>\n";
                        } else {
                            content4 = content4 + "<label class=\"label2\">不选班级</label>\n";
                            content = content + "<label class=\"label2\">不选班级</label>\n";
                        }
                        content4 = content4 + "<label class=\"label2\">学员数量：" + json["num"] + "</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥" + json["price"] + "</span><span>￥" + json["actualpay"] + "</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：已完成</label>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";
                        content = content + "<label class=\"label2\">学员数量：" + json["num"] + "</label>\n" +
                            "                    <label class=\"label2\"><span class=\"line\">￥" + json["price"] + "</span><span>￥" + json["actualpay"] + "</span></label>\n" +
                            "                    <label class=\"label2\">订单状态：已完成</label>\n" +
                            "                    <el-collapse>\n" +
                            "                        <el-collapse-item title=\"查看学员\">\n" +
                            "                            <table width=\"100%\">\n" +
                            "                                <thead>\n" +
                            "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                            "                                </thead>\n" +
                            "                                <tbody>";

                        for (var k = 0; k < nameList.length; k++) {
                            content4 = content4 + " <tr><td>" + nameList[k] + "</td><td>" + genderList[k] + "</td><td>" + educationList[k] + "</td><td>" + classidList[k] + "</td></tr>";
                            content = content + " <tr><td>" + nameList[k] + "</td><td>" + genderList[k] + "</td><td>" + educationList[k] + "</td><td>" + classidList[k] + "</td></tr>";
                        }
                        content4 = content4 + "</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                        content = content + "</tbody>\n" +
                            "                            </table>\n" +
                            "                        </el-collapse-item>\n" +
                            "                    </el-collapse>\n" +
                            "                </div>";
                    }
                }

                if (content == "") {
                    $("#all-order-div").html("<p>当前没有任何订单！</p>");
                } else {
                    $("#all-order-div").html(content);
                }
                if(content1==""){
                    $("#notpay-order-div").html("<p>当前没有未支付的订单！</p>")
                }else{
                    $("#notpay-order-div").html(content1);
                }
                if(content2==""){
                    $("#hasbook-order-div").html("<p>当前没有已预订的订单！</p>")
                }else{
                    $("#hasbook-order-div").html(content2);
                }
                if(content3==""){
                    $("#unsubscribe-order-div").html("<p>当前没有已退订或者配票失败的订单！</p>")
                }else{
                    $("#unsubscribe-order-div").html(content3);
                }
                if(content4==""){
                    $("#finish-order-div").html("<p>当前没有已完成的订单！</p>")
                }else{
                    $("#finish-order-div").html(content4);
                }
                new Vue().$mount(".main-content");
            }
        });
    });

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
});