
'use strict'

var lessonid = "";

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
                var result = data["result"];
                var content = "";
                var content1 = "";
                var content2 = "";
                var content3 = "";
                var content4 = "";

                for(var i=0;i<result.length;i++){
                    var json = JSON.parse(JSON.stringify(result[i]));

                    var state = json["state"];
                    if(state=="未开课"||state=="已退课"||state=="已结课"){
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
                            "                </div>";
                        if(state=="未开课"){
                            content1 = content1+"<div class=\"show-one-lesson\">\n" +
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
                                "                </div>";
                        }else if(state=="已退课"){
                            content2 = content2+"<div class=\"show-one-lesson\">\n" +
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
                                "                </div>";
                        }else{
                            content4 = content4+"<div class=\"show-one-lesson\">\n" +
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
                                "                </div>";
                        }
                    }else{
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
                            "                    <label class=\"label2\">成绩："+json["grade"]+"</label>\n"+
                            "                    <a onclick=\"withdrawLesson('"+json["lessonid"]+"')\">立即退课</a>\n" +
                            "                </div>";
                        content3 = content3+"<div class=\"show-one-lesson\">\n" +
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
                            "                    <label class=\"label2\">成绩："+json["grade"]+"</label>\n"+
                            "                    <a onclick=\"withdrawLesson('"+json["lessonid"]+"')\">立即退课</a>\n" +
                            "                </div>";
                    }
                }

                if(content==""){
                    $("#all-lesson-div").html("<p>您当前还没有学过任何课程！</p>");
                }else{
                    $("#all-lesson-div").html(content);
                }
                if(content1==""){
                    $("#nostart-lesson-div").html("<p>当前没有未开始的课程！</p>")
                }else{
                    $("#nostart-lesson-div").html(content1);
                }
                if(content2==""){
                    $("#unsubscribe-lesson-div").html("<p>当前没有已退选的课程！</p>");
                }else{
                    $("#unsubscribe-lesson-div").html(content2);
                }
                if(content3==""){
                    $("#ing-lesson-div").html("<p>当前没有正在上的课程！</p>");
                }else{
                    $("#ing-lesson-div").html(content3);
                }
                if(content4==""){
                    $("#end-lesson-div").html("<p>当前没有已结束的课程！</p>")
                }else{
                    $("#end-lesson-div").html(content4);
                }
            }
        });
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