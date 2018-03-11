'use strict';

var result;

/**
 * 保存lessonid到java静态变量中
 * @param lessonid
 */
function book(lessonid) {
    $.ajax({
        url: "/institution.saveLessonid",
        type: "post",
        dataType:"json",
        data:{
            lessonid: lessonid,
        },
        success: function (data) {
            window.open("/book");
        }
    });
}

$(function () {
    $(document).ready(function () {
        $("#college-subject").hide();

        $.ajax({
            url: "/index.getLessonList",
            type: "post",
            dataType: "json",
            success: function (data) {
                displayLesson(data);
            }
        });
    });
    
    $("#primary-school").click(function () {
        if($("#primary-school input").prop("checked")){
            $("#under-college-subject").show();
            $("#college-subject").hide();

            $("#physics input").attr("disabled",true);
            $("#chemistry input").attr("disabled",true);
            $("#politics input").attr("disabled",true);
            $("#history input").attr("disabled",true);
            $("#geography input").attr("disabled",true);
            $("#biology input").attr("disabled",true);
        }
    });

    $("#middle-school").click(function () {
        if($("#middle-school input").prop("checked")){
            $("#under-college-subject").show();
            $("#college-subject").hide();

            $("#physics input").removeAttr("disabled");
            $("#chemistry input").removeAttr("disabled");
            $("#politics input").attr("disabled",true);
            $("#history input").attr("disabled",true);
            $("#geography input").attr("disabled",true);
            $("#biology input").attr("disabled",true);
        }
    });

    $("#high-school").click(function () {
        if($("#high-school input").prop("checked")){
            $("#under-college-subject").show();
            $("#college-subject").hide();

            $("#physics input").removeAttr("disabled");
            $("#chemistry input").removeAttr("disabled");
            $("#politics input").removeAttr("disabled");
            $("#history input").removeAttr("disabled");
            $("#geography input").removeAttr("disabled");
            $("#biology input").removeAttr("disabled");
        }
    });

    $("#college").click(function () {
        if($("#college input").prop("checked")){
            $("#under-college-subject").hide();
            $("#college-subject").show();
        }
    });

    /**
     * 搜索课程
     */
    $("#search-lesson-btn").click(function () {
        var lessonName = $("#enter-lesson-name").val();
        var school = "";
        if($("#primary-school input").prop("checked")){
            school = "小学";
        }
        if($("#middle-school input").prop("checked")){
            school = "初中";
        }
        if($("#high-school input").prop("checked")){
            school = "高中";
        }
        if($("#college input").prop("checked")){
            school = "大学";
        }
        var subject = new Array();
        if(school=="大学"){
            if($("#philosophy input").prop("checked")){
                subject.push("哲学");
            }
            if($("#economy input").prop("checked")){
                subject.push("经济学");
            }
            if($("#law input").prop("checked")){
                subject.push("法学");
            }
            if($("#education input").prop("checked")){
                subject.push("教育学");
            }
            if($("#literature input").prop("checked")){
                subject.push("文学");
            }
            if($("#college-history input").prop("checked")){
                subject.push("历史学");
            }
            if($("#science input").prop("checked")){
                subject.push("理学");
            }
            if($("#engineering input").prop("checked")){
                subject.push("工学");
            }
            if($("#agronomy input").prop("checked")){
                subject.push("农学");
            }
            if($("#medical input").prop("checked")){
                subject.push("医学");
            }
            if($("#military input").prop("checked")){
                subject.push("军事学");
            }
            if($("#management input").prop("checked")){
                subject.push("管理学");
            }
            if($("#art input").prop("checked")){
                subject.push("艺术学");
            }
        }else{
            if($("#chinese input").prop("checked")){
                subject.push("语文");
            }
            if($("#math input").prop("checked")){
                subject.push("数学");
            }
            if($("#english input").prop("checked")){
                subject.push("英语");
            }
            if($("#physics input").prop("checked")){
                subject.push("物理");
            }
            if($("#chemistry input").prop("checked")){
                subject.push("化学");
            }
            if($("#politics input").prop("checked")){
                subject.push("政治");
            }
            if($("#history input").prop("checked")){
                subject.push("历史");
            }
            if($("#geography input").prop("checked")){
                subject.push("地理");
            }
            if($("#biology input").prop("checked")){
                subject.push("生物");
            }
        }
        
        $.ajax({
            url: "/index.searchLessonList",
            type: "post",
            data: {
                lessonname: lessonName,
                school: school,
                subject: subject,
            },
            dataType: "json",
            success: function (data) {
                displayLesson(data);
            }
        });
    });

    /**
     * 展示课程
     * @param data
     */
    function displayLesson(data) {
        result = data["result"];

        if(result.length===0){
            $("#lesson-list-div").html("<p>当前没有推荐的课程！</p>");
        }else{
            if(result.length>5){
                var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"5\" :total=\""+result.length+"\" @current-change=\"handleCurrentChange\">\n" +
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

    /**
     * 分页展示
     * @param val
     * @constructor
     */
    function PagingDisplay(val) {
        var content = "";

        var begin = (val-1)*5;
        var last = 0;
        if(result.length-begin<5){
            last = result.length;
        }else{
            last = begin+5;
        }
        for(var i=begin;i<last;i++){
            var json = JSON.parse(JSON.stringify(result[i]));
            content = content+"<div class=\"show-one-plan\">\n" +
                "                    <label class=\"plan-name\">"+json["name"]+"</label>\n" +
                "                    <label>"+json["type"]+"</label>\n" +
                "                    <br>\n" +
                "                    <span class=\"description\">"+json["description"]+"</span>\n" +
                "                    <br>\n" +
                "                    <label class=\"detail\">课程时间："+json["begin"]+"至"+json["end"]+"</label>\n" +
                "                    <label class=\"detail\">地址："+json["address"]+"</label>\n" +
                "                    <label class=\"detail\">状态：预订中</label>\n" +
                "                    <label class=\"detail\">价格："+json["price"]+"元</label>\n" +
                "                    <el-collapse>\n" +
                "                        <el-collapse-item title=\"查看班级\">\n" +
                "                            <table width=\"100%\">";
            var teacherList = json["teacherList"];
            var typeList = json["typeList"];
            var classNumList = json["classNumList"];
            var stuNumList = json["stuNumList"];
            var priceList = json["priceList"];
            for(var k=0;k<teacherList.length;k++){
                content = content+"<tr><td>教师："+teacherList[k]+"</td><td>班级类型："+typeList[k]+"</td><td>班级数："+classNumList[k]
                    +"</td><td>容纳人数："+stuNumList[k]+"</td><td>价格："+priceList[k]+"元</td></tr>";
            }
            content = content+"</table>\n" +
                "                        </el-collapse-item>\n" +
                "                    </el-collapse>\n" +
                "                    <button class=\"my-button\" onclick=\"book('"+json["lessonid"]+"')\">立即预订</button></div>";
        }
        $("#lesson-list-div").html(content);
        new Vue().$mount("#lesson-list-div");
    }
});