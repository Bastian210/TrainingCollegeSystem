
'use strict'

var list1;
var list2;
var list3;
var list4;
var list5;

var c1;
var c2;
var c3;
var c4;
var c5;

$(function () {
    $(document).ready(function () {
        $.ajax({
            url: "/insOrder.getAllInsOrder",
            type: "post",
            dataType: "json",
            success: function (data) {
                list1 = data["all"];
                list2 = data["not_pay"];
                list3 = data["has_book"];
                list4 = data["unsubscribe"];
                list5 = data["finish"];

                var Main = {
                    methods: {
                        handleChange(type, orderid, num) {
                            var content = $("#" + type + "-tbody" + num).html();
                            if (content === "") {
                                $.ajax({
                                    url: "/myOrder.getOrderMessage",
                                    type: "post",
                                    dataType: "json",
                                    data: {
                                        orderid: orderid
                                    },
                                    success: function (data) {
                                        var nameList = data["nameList"];
                                        var genderList = data["genderList"];
                                        var educationList = data["educationList"];
                                        var classidList = data["classidList"];

                                        for (var k = 0; k < nameList.length; k++) {
                                            content = content + " <tr><td>" + nameList[k] + "</td><td>" + genderList[k] + "</td><td>" + educationList[k] + "</td><td>" + classidList[k] + "</td></tr>";
                                        }
                                        $("#" + type + "-tbody" + num).html(content);
                                    }
                                });
                            }
                        }
                    }
                }
                var Ctor = Vue.extend(Main);
                c1 = new Ctor();
                c2 = new Ctor();
                c3 = new Ctor();
                c4 = new Ctor();
                c5 = new Ctor();

                if (list1.length === 0) {
                    $("#all-order-div").html("<p>您当前没有下过订单！</p>");
                } else {
                    if (list1.length > 5) {
                        var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"5\" :total=\"" + list1.length + "\" @current-change=\"handleCurrentChange\">\n" +
                            "                </el-pagination>";
                        $("#my-pagination1").html(content);

                        var Main = {
                            methods: {
                                handleCurrentChange(val) {
                                    PagingDisplay1(val);
                                }
                            }
                        }
                        var Ctor = Vue.extend(Main)
                        new Ctor().$mount('#my-pagination1')
                    }

                    PagingDisplay1(1);
                }
                if (list2.length === 0) {
                    $("#notpay-order-div").html("<p>当前没有未支付的订单！</p>")
                } else {
                    if (list2.length > 5) {
                        var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"5\" :total=\"" + list2.length + "\" @current-change=\"handleCurrentChange\">\n" +
                            "                </el-pagination>";
                        $("#my-pagination2").html(content);

                        var Main = {
                            methods: {
                                handleCurrentChange(val) {
                                    PagingDisplay2(val);
                                }
                            }
                        }
                        var Ctor = Vue.extend(Main);
                        new Ctor().$mount('#my-pagination2');
                    }

                    PagingDisplay2(1);
                }
                if (list3.length === 0) {
                    $("#hasbook-order-div").html("<p>当前没有已预订的订单！</p>")
                } else {
                    if (list3.length > 5) {
                        var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"5\" :total=\"" + list3.length + "\" @current-change=\"handleCurrentChange\">\n" +
                            "                </el-pagination>";
                        $("#my-pagination3" +
                            "").html(content);

                        var Main = {
                            methods: {
                                handleCurrentChange(val) {
                                    PagingDisplay3(val);
                                }
                            }
                        }
                        var Ctor = Vue.extend(Main);
                        new Ctor().$mount('#my-pagination3');
                    }

                    PagingDisplay3(1);
                }
                if (list4.length === 0) {
                    $("#unsubscribe-order-div").html("<p>当前没有已退订或者配票失败的订单！</p>")
                } else {
                    if (list4.length > 5) {
                        var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"5\" :total=\"" + list4.length + "\" @current-change=\"handleCurrentChange\">\n" +
                            "                </el-pagination>";
                        $("#my-pagination4").html(content);

                        var Main = {
                            methods: {
                                handleCurrentChange(val) {
                                    PagingDisplay4(val);
                                }
                            }
                        }
                        var Ctor = Vue.extend(Main);
                        new Ctor().$mount('#my-pagination4');
                    }

                    PagingDisplay4(1);
                }
                if (list5.length === 0) {
                    $("#finish-order-div").html("<p>当前没有已完成的订单！</p>")
                } else {
                    if (list5.length > 5) {
                        var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"5\" :total=\"" + list5.length + "\" @current-change=\"handleCurrentChange\">\n" +
                            "                </el-pagination>";
                        $("#my-pagination5").html(content);

                        var Main = {
                            methods: {
                                handleCurrentChange(val) {
                                    PagingDisplay5(val);
                                }
                            }
                        }
                        var Ctor = Vue.extend(Main);
                        new Ctor().$mount('#my-pagination5');
                    }

                    PagingDisplay5(1);
                }
            }
        });
    });

    /**
     * 分页展示全部订单
     * @param val
     * @constructor
     */
    function PagingDisplay1(val) {
        var begin = (val-1)*5;
        var end = 0;
        if(list1.length-begin<5){
            end = list1.length;
        }else{
            end = begin+5;
        }

        var content = "";
        for(var i=begin;i<end;i++){
            var json = JSON.parse(JSON.stringify(list1[i]));
            var state = json["state"];

            content = content+"<div class=\"show-one-order\">\n" +
                "                    <label class=\"label1\">"+json["time"]+"</label>\n" +
                "                    <label class=\"label1\">订单号："+json["orderid"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["username"]+"</label>\n";
            content = content+ "<br>\n" +
                "                    <label class=\"label2\">"+json["lessonname"]+"</label>\n";
            if(json["type"]==="选班级"){
                content = content+"<label class=\"label2\">选班级："+json["classtype"]+"</label>\n";
            }else{
                content = content+"<label class=\"label2\">不选班级</label>\n";
            }
            content = content+ "<label class=\"label2\">学员数量："+json["num"]+"</label>\n" +
                "                    <label class=\"label2\"><span class=\"line\">￥"+json["price"]+"</span><span>￥"+json["actualpay"]+"</span></label>\n" +
                "                    <label class=\"label2\">订单状态："+state+"</label>\n";
            content = content+ "<el-collapse @change=\"handleChange('all','"+json["orderid"]+"',"+i+")\">\n" +
                "                        <el-collapse-item title=\"查看学员\">\n" +
                "                            <table width=\"100%\">\n" +
                "                                <thead>\n" +
                "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                "                                </thead>\n" +
                "                                <tbody id='all-tbody"+i+"'></tbody>\n" +
                "                            </table>\n" +
                "                        </el-collapse-item>\n" +
                "                    </el-collapse>\n" +
                "                </div>";
        }

        $("#all-order-display").html(content);
        c1.$mount("#all-order-display");
    }

    /**
     * 分页展示未支付订单
     * @param val
     * @constructor
     */
    function PagingDisplay2(val) {
        var begin = (val-1)*5;
        var end = 0;
        if(list2.length-begin<5){
            end = list2.length;
        }else{
            end = begin+5;
        }

        var content1 = "";
        for(var i=begin;i<end;i++){
            var json = JSON.parse(JSON.stringify(list2[i]));

            content1 = content1+"<div class=\"show-one-order\">\n" +
                "                    <label class=\"label1\">"+json["time"]+"</label>\n" +
                "                    <label class=\"label1\">订单号："+json["orderid"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["username"]+"</label>\n" +
                "                    <br>\n" +
                "                    <label class=\"label2\">"+json["lessonname"]+"</label>\n";
            if(json["type"]==="选班级"){
                content1 = content1+"<label class=\"label2\">选班级："+json["classtype"]+"</label>\n";
            }else{
                content1 = content1+"<label class=\"label2\">不选班级</label>\n";
            }
            content1 = content1+ "<label class=\"label2\">学员数量："+json["num"]+"</label>\n" +
                "                    <label class=\"label2\"><span class=\"line\">￥"+json["price"]+"</span><span>￥"+json["actualpay"]+"</span></label>\n" +
                "                    <label class=\"label2\">订单状态：未支付</label>\n" +
                "                    <el-collapse @change=\"handleChange('notpay','"+json["orderid"]+"',"+i+")\">\n" +
                "                        <el-collapse-item title=\"查看学员\">\n" +
                "                            <table width=\"100%\">\n" +
                "                                <thead>\n" +
                "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                "                                </thead>\n" +
                "                                <tbody id='notpay-tbody"+i+"'></tbody>\n" +
                "                            </table>\n" +
                "                        </el-collapse-item>\n" +
                "                    </el-collapse>\n" +
                "                </div>";
        }

        $("#notpay-order-display").html(content1);
        c2.$mount("#notpay-order-display");
    }

    /**
     * 分页展示已预订/等待配票的订单
     * @param val
     * @constructor
     */
    function PagingDisplay3(val) {
        var begin = (val-1)*5;
        var end = 0;
        if(list3.length-begin<5){
            end = list3.length;
        }else{
            end = begin+5;
        }

        var content2 = "";
        for(var i=begin;i<end;i++){
            var json = JSON.parse(JSON.stringify(list3[i]));

            content2 = content2+"<div class=\"show-one-order\">\n" +
                "                    <label class=\"label1\">"+json["time"]+"</label>\n" +
                "                    <label class=\"label1\">订单号："+json["orderid"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["username"]+"</label>\n" +
                "                    <br>\n" +
                "                    <label class=\"label2\">"+json["lessonname"]+"</label>\n";
            if(json["type"]==="选班级"){
                content2 = content2+"<label class=\"label2\">选班级："+json["classtype"]+"</label>\n";
            }else{
                content2 = content2+"<label class=\"label2\">不选班级</label>\n";
            }
            content2 = content2+ "<label class=\"label2\">学员数量："+json["num"]+"</label>\n" +
                "                    <label class=\"label2\"><span class=\"line\">￥"+json["price"]+"</span><span>￥"+json["actualpay"]+"</span></label>\n" +
                "                    <label class=\"label2\">订单状态："+json["state"]+"</label>\n";
            content2 = content2+ "<el-collapse @change=\"handleChange('hasbook','"+json["orderid"]+"',"+i+")\">\n" +
                "                        <el-collapse-item title=\"查看学员\">\n" +
                "                            <table width=\"100%\">\n" +
                "                                <thead>\n" +
                "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                "                                </thead>\n" +
                "                                <tbody id='hasbook-tbody"+i+"'></tbody>\n" +
                "                            </table>\n" +
                "                        </el-collapse-item>\n" +
                "                    </el-collapse>\n" +
                "                </div>";
        }

        $("#hasbook-order-display").html(content2);
        c3.$mount("#hasbook-order-display");
    }

    /**
     * 分页展示已退订/配票失败的订单
     * @param val
     * @constructor
     */
    function PagingDisplay4(val) {
        var begin = (val-1)*5;
        var end = 0;
        if(list4.length-begin<5){
            end = list4.length;
        }else{
            end = begin+5;
        }

        var content3 ="";
        for(var i=begin;i<end;i++){
            var json = JSON.parse(JSON.stringify(list4[i]));

            content3 = content3+"<div class=\"show-one-order\">\n" +
                "                    <label class=\"label1\">"+json["time"]+"</label>\n" +
                "                    <label class=\"label1\">订单号："+json["orderid"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["username"]+"</label>\n" +
                "                    <br>\n" +
                "                    <label class=\"label2\">"+json["lessonname"]+"</label>\n";
            if(json["type"]==="选班级"){
                content3 = content3+"<label class=\"label2\">选班级："+json["classtype"]+"</label>\n";
            }else{
                content3 = content3+"<label class=\"label2\">不选班级</label>\n";
            }
            content3 = content3+ "<label class=\"label2\">学员数量："+json["num"]+"</label>\n" +
                "                    <label class=\"label2\"><span class=\"line\">￥"+json["price"]+"</span><span>￥"+json["actualpay"]+"</span></label>\n" +
                "                    <label class=\"label2\">订单状态："+json["state"]+"</label>\n "+
                "                    <el-collapse @change=\"handleChange('unsubscribe','"+json["orderid"]+"',"+i+")\">\n" +
                "                        <el-collapse-item title=\"查看学员\">\n" +
                "                            <table width=\"100%\">\n" +
                "                                <thead>\n" +
                "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                "                                </thead>\n" +
                "                                <tbody id='unsubscribe-tbody"+i+"'></tbody>\n" +
                "                            </table>\n" +
                "                        </el-collapse-item>\n" +
                "                    </el-collapse>\n" +
                "                </div>";
        }

        $("#unsubscribe-order-display").html(content3);
        c4.$mount("#unsubscribe-order-display");
    }

    /**
     * 分页展示已完成的订单
     * @param val
     * @constructor
     */
    function PagingDisplay5(val) {
        var begin = (val-1)*5;
        var end = 0;
        if(list5.length-begin<5){
            end = list5.length;
        }else{
            end = begin+5;
        }

        var content4 = "";
        for(var i=begin;i<end;i++){
            var json = JSON.parse(JSON.stringify(list5[i]));

            content4 = content4+"<div class=\"show-one-order\">\n" +
                "                    <label class=\"label1\">"+json["time"]+"</label>\n" +
                "                    <label class=\"label1\">订单号："+json["orderid"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["username"]+"</label>\n" +
                "                    <br>\n" +
                "                    <label class=\"label2\">"+json["lessonname"]+"</label>\n";
            if(json["type"]==="选班级"){
                content4 = content4+"<label class=\"label2\">选班级："+json["classtype"]+"</label>\n";
            }else{
                content4 = content4+"<label class=\"label2\">不选班级</label>\n";
            }
            content4 = content4+ "<label class=\"label2\">学员数量："+json["num"]+"</label>\n" +
                "                    <label class=\"label2\"><span class=\"line\">￥"+json["price"]+"</span><span>￥"+json["actualpay"]+"</span></label>\n" +
                "                    <label class=\"label2\">订单状态："+json["state"]+"</label>\n "+
                "                    <el-collapse @change=\"handleChange('finish','"+json["orderid"]+"',"+i+")\">\n" +
                "                        <el-collapse-item title=\"查看学员\">\n" +
                "                            <table width=\"100%\">\n" +
                "                                <thead>\n" +
                "                                <tr><th class=\"w1\">姓名</th><th class=\"w2\">性别</th><th class=\"w3\">教育程度</th><th class=\"w4\">分配班级</th></tr>\n" +
                "                                </thead>\n" +
                "                                <tbody id='finish-tbody"+i+"'></tbody>\n" +
                "                            </table>\n" +
                "                        </el-collapse-item>\n" +
                "                    </el-collapse>\n" +
                "                </div>";
        }

        $("#finish-order-display").html(content4);
        c5.$mount("#finish-order-display");
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
});