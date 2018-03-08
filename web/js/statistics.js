
'use strict'

var institution_array;
var user_array;

$(function () {
    $(document).ready(function () {
        $.ajax({
            url: "/statistics.getAllInstitution",
            type: "post",
            dataType: "json",
            success: function (data) {
                institution_array = data["result"];

                if(institution_array.length>10){
                    var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"10\" :total=\""+institution_array.length+"\" @current-change=\"handleCurrentChange\">\n" +
                        "                </el-pagination>";
                    $("#all-institution-pagination").html(content);

                    var Main = {
                        methods: {
                            handleCurrentChange(val) {
                                PagingDisplayInstitution(val);
                            }
                        }
                    }
                    var Ctor = Vue.extend(Main);
                    new Ctor().$mount("#all-institution-pagination");
                }
                PagingDisplayInstitution(1);
            }
        });

        $.ajax({
            url: "/statistics.getAllUser",
            type: "post",
            dataType: "json",
            success: function (data) {
                user_array = data["result"];

                if(user_array.length>10){
                    var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"10\" :total=\""+user_array.length+"\" @current-change=\"handleCurrentChange\">\n" +
                        "                </el-pagination>";
                    $("#all-user-pagination").html(content);

                    var Main = {
                        methods: {
                            handleCurrentChange(val) {
                                PagingDisplayUser(val);
                            }
                        }
                    }
                    var Ctor = Vue.extend(Main);
                    new Ctor().$mount("#all-user-pagination");
                }
                PagingDisplayUser(1);
            }
        });

        $.ajax({
            url: "/statistics.getProfit",
            type: "post",
            dataType: "json",
            success: function (data) {
                var result = data["result"];
                $("#profit-label").html(result+"元");

                var month = data["month"];
                var profit = data["profit"];

                if(month.length>0){
                    var myChart = echarts.init(document.getElementById('system-profit-echarts'));

                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: "系统月利润额统计图"
                        },
                        tooltip: {},
                        legend: {
                            data:['利润额']
                        },
                        xAxis: {
                            data: month
                        },
                        yAxis: {},
                        series: [{
                            name: '利润额',
                            type: 'line',
                            data: profit
                        }]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }else{
                    $("#system-profit-echarts").html("<label>当前没有利润！</label>")
                }
            }
        });
    });

    $("#all-institution-a").click(function () {
        $("#all-institution-a").attr("class","active");
        $("#all-user-a").removeAttr("class");
        $("#profit-a").removeAttr("class");
        $("#all-institution-div").show();
        $("#all-user-div").hide();
        $("#profit-div").hide();
    });

    $("#all-user-a").click(function () {
        $("#all-institution-a").removeAttr("class");
        $("#all-user-a").attr("class","active");
        $("#profit-a").removeAttr("class");
        $("#all-institution-div").hide();
        $("#all-user-div").show();
        $("#profit-div").hide();
    });

    $("#profit-a").click(function () {
        $("#all-institution-a").removeAttr("class");
        $("#all-user-a").removeAttr("class");
        $("#profit-a").attr("class","active");
        $("#all-institution-div").hide();
        $("#all-user-div").hide();
        $("#profit-div").show();
    });

    /**
     * 分页展示机构列表
     * @param val
     * @constructor
     */
    function PagingDisplayInstitution(val) {
        var begin = (val-1)*10;
        var end = 0;
        if(institution_array.length-begin<10){
            end = institution_array.length;
        }else{
            end = begin+10;
        }

        var content = "<table width=\"100%\">\n" +
            "                    <tr><th>识别码</th><th>机构名称</th><th>机构地址</th><th>联系方式</th><th>盈利(元)</th><th>计划数量(个)</th><th>订单数量(个)</th></tr>";
        for(var i=begin;i<end;i++){
            var json = JSON.parse(JSON.stringify(institution_array[i]));
            content = content+"<tr><td>"+json["id"]+"</td><td>"+json["name"]+"</td><td>"+json["address"]+"</td><td>"+json["phone"]+"</td><td>"
                +json["consumption"]+"</td><td>"+json["plans"]+"</td><td>"+json["orders"]+"</td></tr>";
        }
        content = content+"</table>";

        $("#all-institution-div").html(content);
    }

    /**
     * 分页展示用户列表
     * @param val
     * @constructor
     */
    function PagingDisplayUser(val) {
        var begin = (val-1)*10;
        var end = 0;
        if(user_array.length-begin<10){
            end = user_array.length;
        }else{
            end = begin+10;
        }

        var content = "<table width=\"100%\">\n" +
            "                    <tr><th>会员号</th><th>用户名</th><th>邮箱</th><th>性别</th><th>学历</th><th>等级</th><th>积分</th><th>消费量(元)</th><th>课程数量(个)</th><th>订单数量(个)</th></tr>";
        for(var i=begin;i<end;i++){
            var json = JSON.parse(JSON.stringify(user_array[i]));
            content = content+"<tr><td>"+json["id"]+"</td><td>"+json["name"]+"</td><td>"+json["email"]+"</td><td>"+json["gender"]+"</td><td>"
                +json["education"]+"</td><td>"+json["level"]+"</td><td>"+json["points"]+"</td><td>" +json["consumption"]+"</td><td>"+json["lesson"]+"</td><td>"+json["order"]+"</td></tr>";
        }
        content = content+"</table>";

        $("#all-user-div").html(content);
    }
});