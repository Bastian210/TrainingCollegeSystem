'use strict';

var result;

var vue;
var vue1;

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

        var list1 = ["语文", "数学", "英语", "物理", "化学", "政治", "历史", "地理", "生物"];
        var list2 = ["哲学","经济学","法学","教育学","文学","历史学","理学","工学","农学","医学","军事学","管理学","艺术学"];
        var list3 = ["语文", "数学", "英语", "物理", "化学"];
        var list4 = ["语文", "数学", "英语"];

        vue1 = new Vue({
            el: "#checkbox",
            data: {
                checkedSubjects: [],
                subjects: list4
            }
        });

        vue = new Vue({
            el: "#radio",
            data: {
                radio: '',
            },
            methods: {
                change(){
                    if(this.radio==="大学"){
                        vue1.checkedSubjects = [];
                        vue1.subjects = list2;
                    }else if(this.radio==="高中"){
                        vue1.checkedSubjects = [];
                        vue1.subjects = list1;
                    }else if(this.radio==="初中"){
                        vue1.checkedSubjects = [];
                        vue1.subjects = list3;
                    }else{
                        vue1.checkedSubjects = [];
                        vue1.subjects = list4;
                    }
                }
            }
        });

        $.ajax({
            url: "/index.getLessonList",
            type: "post",
            dataType: "json",
            success: function (data) {
                displayLesson(data);
            }
        });
    });

    /**
     * 搜索课程
     */
    $("#search-lesson-btn").click(function () {
        var lessonName = $("#enter-lesson-name").val();
        var school = vue.radio;
        console.log(school);

        var subject = vue1.checkedSubjects;
        console.log(subject);
        
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