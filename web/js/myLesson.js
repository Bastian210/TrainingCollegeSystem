
'use strict'

var lessonid = "";

var list1;
var list2;
var list3;
var list4;
var list5;

function withdrawLesson(id) {
    lessonid = id;
    $("#withdraw-lesson-modal").modal("show");
}

$(function () {
    $(document).ready(function () {
        getAllLesson();
    });

    /**
     * 得到所有课程
     */
    function getAllLesson() {
        $.ajax({
            url: "/myLesson.getAllUserLesson",
            type: "post",
            dataType: "json",
            success: function (data) {
                list1 = data["all"];
                list2 = data["nostart"];
                list3 = data["unsubscribe"];
                list4 = data["ing"];
                list5 = data["finish"];

                if(list1.length===0){
                    $("#all-lesson-div").html("<p>您当前还没有学过任何课程！</p>");
                }else{
                    if(list1.length>5){
                        var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"5\" :total=\"" + list1.length + "\" @current-change=\"handleCurrentChange\">\n" +
                            "                </el-pagination>";
                        $("#my-pagination1").html(content);

                        var Main = {
                            methods: {
                                handleCurrentChange(val) {
                                    PagingDisplay1(val);
                                }
                            }
                        };
                        var Ctor = Vue.extend(Main);
                        new Ctor().$mount('#my-pagination1');
                    }
                    PagingDisplay1(1);
                }
                if(list2.length===0){
                    $("#nostart-lesson-div").html("<p>当前没有未开始的课程！</p>")
                }else{
                    if(list2.length>5){
                        var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"5\" :total=\"" + list2.length + "\" @current-change=\"handleCurrentChange\">\n" +
                            "                </el-pagination>";
                        $("#my-pagination2").html(content);

                        var Main = {
                            methods: {
                                handleCurrentChange(val) {
                                    PagingDisplay2(val);
                                }
                            }
                        };
                        var Ctor = Vue.extend(Main);
                        new Ctor().$mount('#my-pagination2');
                    }
                    PagingDisplay2(1);
                }
                if(list3.length===0){
                    $("#unsubscribe-lesson-div").html("<p>当前没有已退选的课程！</p>");
                }else{
                    if(list3.length>5){
                        var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"5\" :total=\"" + list3.length + "\" @current-change=\"handleCurrentChange\">\n" +
                            "                </el-pagination>";
                        $("#my-pagination3").html(content);

                        var Main = {
                            methods: {
                                handleCurrentChange(val) {
                                    PagingDisplay3(val);
                                }
                            }
                        };
                        var Ctor = Vue.extend(Main);
                        new Ctor().$mount('#my-pagination3');
                    }
                    PagingDisplay3(1);
                }
                if(list4.length===0){
                    $("#ing-lesson-div").html("<p>当前没有正在上的课程！</p>");
                }else{
                    if(list4.length>5){
                        var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"5\" :total=\"" + list4.length + "\" @current-change=\"handleCurrentChange\">\n" +
                            "                </el-pagination>";
                        $("#my-pagination4").html(content);

                        var Main = {
                            methods: {
                                handleCurrentChange(val) {
                                    PagingDisplay4(val);
                                }
                            }
                        };
                        var Ctor = Vue.extend(Main);
                        new Ctor().$mount('#my-pagination4');
                    }
                    PagingDisplay4(1);
                }
                if(list5.length===0){
                    $("#end-lesson-div").html("<p>当前没有已结束的课程！</p>")
                }else{
                    if(list5.length>5){
                        var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"5\" :total=\"" + list5.length + "\" @current-change=\"handleCurrentChange\">\n" +
                            "                </el-pagination>";
                        $("#my-pagination5").html(content);

                        var Main = {
                            methods: {
                                handleCurrentChange(val) {
                                    PagingDisplay5(val);
                                }
                            }
                        };
                        var Ctor = Vue.extend(Main);
                        new Ctor().$mount("#my-pagination5");
                    }
                    PagingDisplay5(1);
                }
            }
        });
    }

    /**
     * 分页展示全部课程
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
            content = content+"<div class=\"show-one-lesson\">\n" +
                "                    <label class=\"label1\">"+json["institutionname"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["type"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["lessonname"]+"</label>\n" +
                "                    <br>\n" +
                "                    <label class=\"label3\">时间："+json["begin"]+"至"+json["end"]+"</label>\n" +
                "                    <span>"+json["description"]+"</span>\n" +
                "                    <br>\n" +
                "                    <label class=\"label2\">班级："+json["classtype"]+"</label>\n" +
                "                    <label class=\"label2\">老师："+json["teacher"]+"</label>\n" +
                "                    <label class=\"label2\">课程状态："+state+"</label>\n" +
                "                    <label class=\"label2\">成绩："+json["grade"]+"</label>\n";
            if(state==="已开课"){
                content = content+"<a onclick=\"withdrawLesson('"+json["lessonid"]+"')\">立即退课</a>\n";
            }
            content = content+ "</div>";
        }

        $("#all-lesson-display").html(content);
    }

    /**
     * 分页展示未开课课程
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

        var content = "";
        for(var i=begin;i<end;i++){
            var json = JSON.parse(JSON.stringify(list2[i]));

            var state = json["state"];
            content = content+"<div class=\"show-one-lesson\">\n" +
                "                    <label class=\"label1\">"+json["institutionname"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["type"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["lessonname"]+"</label>\n" +
                "                    <br>\n" +
                "                    <label class=\"label3\">时间："+json["begin"]+"至"+json["end"]+"</label>\n" +
                "                    <span>"+json["description"]+"</span>\n" +
                "                    <br>\n" +
                "                    <label class=\"label2\">班级："+json["classtype"]+"</label>\n" +
                "                    <label class=\"label2\">老师："+json["teacher"]+"</label>\n" +
                "                    <label class=\"label2\">课程状态："+state+"</label>\n" +
                "                    <label class=\"label2\">成绩："+json["grade"]+"</label>\n" +
                "                    </div>";
        }

        $("#nostart-lesson-display").html(content);
    }

    /**
     * 分页展示已退课课程
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

        var content = "";
        for(var i=begin;i<end;i++){
            var json = JSON.parse(JSON.stringify(list3[i]));

            var state = json["state"];
            content = content+"<div class=\"show-one-lesson\">\n" +
                "                    <label class=\"label1\">"+json["institutionname"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["type"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["lessonname"]+"</label>\n" +
                "                    <br>\n" +
                "                    <label class=\"label3\">时间："+json["begin"]+"至"+json["end"]+"</label>\n" +
                "                    <span>"+json["description"]+"</span>\n" +
                "                    <br>\n" +
                "                    <label class=\"label2\">班级："+json["classtype"]+"</label>\n" +
                "                    <label class=\"label2\">老师："+json["teacher"]+"</label>\n" +
                "                    <label class=\"label2\">课程状态："+state+"</label>\n" +
                "                    <label class=\"label2\">成绩："+json["grade"]+"</label>\n";
            content = content+ "</div>";
        }

        $("#unsubscribe-lesson-display").html(content);
    }

    /**
     * 分页展示正在上课的课程
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

        var content = "";
        for(var i=begin;i<end;i++){
            var json = JSON.parse(JSON.stringify(list4[i]));

            var state = json["state"];
            content = content+"<div class=\"show-one-lesson\">\n" +
                "                    <label class=\"label1\">"+json["institutionname"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["type"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["lessonname"]+"</label>\n" +
                "                    <br>\n" +
                "                    <label class=\"label3\">时间："+json["begin"]+"至"+json["end"]+"</label>\n" +
                "                    <span>"+json["description"]+"</span>\n" +
                "                    <br>\n" +
                "                    <label class=\"label2\">班级："+json["classtype"]+"</label>\n" +
                "                    <label class=\"label2\">老师："+json["teacher"]+"</label>\n" +
                "                    <label class=\"label2\">课程状态："+state+"</label>\n" +
                "                    <label class=\"label2\">成绩："+json["grade"]+"</label>\n";
            content = content+"<a onclick=\"withdrawLesson('"+json["lessonid"]+"')\">立即退课</a>\n";
            content = content+ "</div>";
        }

        $("#ing-lesson-display").html(content);
    }

    /**
     * 分页展示已完结的课程
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

        var content = "";
        for(var i=begin;i<end;i++){
            var json = JSON.parse(JSON.stringify(list5[i]));

            var state = json["state"];
            content = content+"<div class=\"show-one-lesson\">\n" +
                "                    <label class=\"label1\">"+json["institutionname"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["type"]+"</label>\n" +
                "                    <label class=\"label1\">"+json["lessonname"]+"</label>\n" +
                "                    <br>\n" +
                "                    <label class=\"label3\">时间："+json["begin"]+"至"+json["end"]+"</label>\n" +
                "                    <span>"+json["description"]+"</span>\n" +
                "                    <br>\n" +
                "                    <label class=\"label2\">班级："+json["classtype"]+"</label>\n" +
                "                    <label class=\"label2\">老师："+json["teacher"]+"</label>\n" +
                "                    <label class=\"label2\">课程状态："+state+"</label>\n" +
                "                    <label class=\"label2\">成绩："+json["grade"]+"</label>\n";
            content = content+ "</div>";
        }

        $("#end-lesson-display").html(content);
    }

    $("#all-lesson-a").click(function () {
        $("#all-lesson-a").attr("class","active");
        $("#nostart-lesson-a").removeAttr("class");
        $("#unsubscribe-lesson-a").removeAttr("class");
        $("#ing-lesson-a").removeAttr("class");
        $("#end-lesson-a").removeAttr("class");
        $("#all-lesson-div").show();
        $("#nostart-lesson-div").hide();
        $("#unsubscribe-lesson-div").hide();
        $("#ing-lesson-div").hide();
        $("#end-lesson-div").hide();
    });

    $("#nostart-lesson-a").click(function () {
        $("#all-lesson-a").removeAttr("class");
        $("#nostart-lesson-a").attr("class","active");
        $("#unsubscribe-lesson-a").removeAttr("class");
        $("#ing-lesson-a").removeAttr("class");
        $("#end-lesson-a").removeAttr("class");
        $("#all-lesson-div").hide();
        $("#nostart-lesson-div").show();
        $("#unsubscribe-lesson-div").hide();
        $("#ing-lesson-div").hide();
        $("#end-lesson-div").hide();
    });

    $("#unsubscribe-lesson-a").click(function () {
        $("#all-lesson-a").removeAttr("class");
        $("#nostart-lesson-a").removeAttr("class");
        $("#unsubscribe-lesson-a").attr("class","active");
        $("#ing-lesson-a").removeAttr("class");
        $("#end-lesson-a").removeAttr("class");
        $("#all-lesson-div").hide();
        $("#nostart-lesson-div").hide();
        $("#unsubscribe-lesson-div").show();
        $("#ing-lesson-div").hide();
        $("#end-lesson-div").hide();
    });

    $("#ing-lesson-a").click(function () {
        $("#all-lesson-a").removeAttr("class");
        $("#nostart-lesson-a").removeAttr("class");
        $("#unsubscribe-lesson-a").removeAttr("class");
        $("#ing-lesson-a").attr("class","active");
        $("#end-lesson-a").removeAttr("class");
        $("#all-lesson-div").hide();
        $("#nostart-lesson-div").hide();
        $("#unsubscribe-lesson-div").hide();
        $("#ing-lesson-div").show();
        $("#end-lesson-div").hide();
    });

    $("#end-lesson-a").click(function () {
        $("#all-lesson-a").removeAttr("class");
        $("#nostart-lesson-a").removeAttr("class");
        $("#unsubscribe-lesson-a").removeAttr("class");
        $("#ing-lesson-a").removeAttr("class");
        $("#end-lesson-a").attr("class","active");
        $("#all-lesson-div").hide();
        $("#nostart-lesson-div").hide();
        $("#unsubscribe-lesson-div").hide();
        $("#ing-lesson-div").hide();
        $("#end-lesson-div").show();
    })

    /**
     * 退课
     */
    $("#withdraw-lesson-btn").click(function () {
        $.ajax({
            url: "/myLesson.withdrawLesson",
            type: "post",
            dataType: "json",
            data: {
                lessonid: lessonid,
            },
            success: function (data) {
                getAllLesson();
                $("#withdraw-lesson-modal").modal("hide");
            }
        });
    });
});