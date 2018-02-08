
'use strict'

var classes = new Array();

var students = new Array();

var lessonid = "";
var level = 0;
var actual = 0;

/**
 * 打开添加学员信息的div元素
 */
function openAddStudentDiv() {
    var max = 3;
    if(students.length==max){
        $("#add-order-error").html("每单不能超过"+max+"个学员！");
        $("#add-order-error").show();
        setTimeout(function () {
            $("#add-order-error").hide();
        },1000);
        console.log("每单不能超过"+max+"个学员！");
    }else{
        var classtype = $("#enter-class-type").val();
        if(classtype!=""){
            console.log(classes);
            for(var i=0;i<classes.length;i++){
                if(classes[i]["classtype"]==classtype){
                    if(parseInt(classes[i]["left"])==students.length){
                        $("#add-order-error").html("此类班级已只剩"+classes[i]["left"]+"个报名名额！");
                        $("#add-order-error").show();
                        setTimeout(function () {
                            $("#add-order-error").hide();
                        },1000);
                    }else{
                        $("#add-student-div").show();
                    }
                    break;
                }
            }
        }else{
            $("#add-student-div").show();
        }
    }
}

/**
 * 删除学员
 * @param name
 */
function deleteStudent(name) {
    for(var i=0;i<students.length;i++){
        if(students[i]["name"]==name){
            students.splice(students[i],1);
            break;
        }
    }
    showStudents();
}

/**
 * 展示已经添加的学生
 */
function showStudents() {
    var content = "";
    for(var i=0;i<students.length;i++){
        content = content+"<span class=\"my-tag\">"+students[i]["name"]+"<i class=\"el-icon-close my-tag-delete\" onclick=\"deleteStudent('"+students[i]["name"]+"')\"'></i></span>";
    }
    content = content+"<a onclick=\"openAddStudentDiv()\" id=\"add-student-a\"><i class=\"el-icon-plus\">新学员</i></a>";
    $("#student-tags").html(content);
}

$(function () {
    $(document).ready(function () {
        $.ajax({
            url: "/institution.getPlan",
            type: "post",
            dataType: "json",
            success: function (data) {
                var content = "";
                var state = "";
                if(data["state"]=="start"){
                    state = "开课中";
                }else{
                    state = "预订中";
                }
                content = content+"<div class=\"show-one-plan\">\n" +
                    "                    <label class=\"plan-name\">"+data["name"]+"</label>\n" +
                    "                    <label>"+data["type"]+"</label>\n" +
                    "                    <br>\n" +
                    "                    <span class=\"description\">"+data["description"]+"</span>\n" +
                    "                    <br>\n" +
                    "                    <label class=\"detail\">课程时间："+data["begin"]+"至"+data["end"]+"</label>\n" +
                    "                    <label class=\"detail\">地址："+data["address"]+"</label>\n" +
                    "                    <label class=\"detail\">状态："+state+"</label>\n" +
                    "                    <label class=\"detail\">价格："+data["price"]+"元</label>\n" +
                    "                    <el-collapse>\n" +
                    "                        <el-collapse-item title=\"查看班级\">\n" +
                    "                            <table width=\"100%\">";
                var teacherList = data["teacherList"];
                var typeList = data["typeList"];
                var classNumList = data["classNumList"];
                var stuNumList = data["stuNumList"];
                var priceList = data["priceList"];
                for(var k=0;k<teacherList.length;k++){
                    content = content+"<tr><td>教师："+teacherList[k]+"</td><td>班级类型："+typeList[k]+"</td><td>班级数："+classNumList[k]
                        +"</td><td>容纳人数："+stuNumList[k]+"</td><td>价格："+priceList[k]+"元</td></tr>";
                }
                content= content+"</table>\n" +
                    "                        </el-collapse-item>\n" +
                    "                    </el-collapse>\n" +
                    "                </div>";
                $("#lesson-message-div").html(content);
                new Vue().$mount("#lesson-message-div");

                lessonid = data["lessonid"];

                var leftList = data["leftList"];
                for(var i=0;i<typeList.length;i++){
                    var cla = {classtype: typeList[i],price: priceList[i],left: leftList[i]};
                    classes.push(cla);
                }

                var Main;
                if(typeList.length==1){
                    Main = {
                        data() {
                            return {
                                options: [{
                                    value: '选项1',
                                    label: typeList[0]
                                }],
                                value: ''
                            }
                        }
                    };
                }else if(typeList.length==2){
                    Main = {
                        data() {
                            return {
                                options: [{
                                    value: '选项1',
                                    label: typeList[0]
                                }, {
                                    value: '选项2',
                                    label: typeList[1]
                                }],
                                value: ''
                            }
                        }
                    }
                }else{
                    Main = {
                        data() {
                            return {
                                options: [{
                                    value: '选项1',
                                    label: typeList[0]
                                }, {
                                    value: '选项2',
                                    label: typeList[1]
                                }, {
                                    value: '选项3',
                                    label: typeList[2]
                                }],
                                value: ''
                            }
                        }
                    }
                }
                var Ctor = Vue.extend(Main);
                new Ctor().$mount('#select-class-type');
            }
        });
    });

    $("#onsite-pay-a").click(function () {
        $("#onsite-pay-a").attr("class","active");
        $("#check-a").removeAttr("class");
        $("#onsite-pay-div").show();
        $("#check-div").hide();
    });

    $("#check-a").click(function () {
        $("#onsite-pay-a").removeAttr("class");
        $("#check-a").attr("class","active");
        $("#onsite-pay-div").hide();
        $("#check-div").show();
    });

    $("#switch div").click(function () {
        if($("#switch div").attr("aria-checked")){
            $("#user-id-label").hide();
            $("#enter-user-id").hide();
            $("#test-vip-a").hide();
        }else{
            $("#user-id-label").show();
            $("#enter-user-id").show();
            $("#test-vip-a").show();
        }
    });

    /**
     * 取消添加学员
     */
    $("#cancel-add-student-btn").click(function () {
        $("#add-student-div").hide();
        clearAddStudentDiv();
    });

    /**
     * 添加学员
     */
    $("#add-student-btn").click(function () {
        var name = $("#enter-student-name").val();
        var gender = "";
        if($("#option-man").prop("checked")){
            gender = "男";
        }
        if($("#option-woman").prop("checked")){
            gender = "女";
        }
        var education = $("#select input").val();
        if(name==""||gender==""||education==""){
            $("#add-student-error").html("学员信息填写不完整！");
            $("#add-student-error").show();
            setTimeout(function () {
                $("#add-student-error").hide();
            },1000);
        }else{
            var student = {name: name, gender: gender, education: education};
            students.push(student);
            showStudents();
            $("#add-student-div").hide();

            clearAddStudentDiv();
        }
    });

    /**
     * 清空学员信息的输入框
     */
    function clearAddStudentDiv() {
        $("#enter-student-name").val("");
        $("#option-man").removeAttr("checked");
        $("#option-woman").removeAttr("checked");
        $("#select input").val("");
    }

    /**
     * 展示已经添加的学生
     */
    function showStudents() {
        var content = "";
        for(var i=0;i<students.length;i++){
            content = content+"<span class=\"my-tag\">"+students[i]["name"]+"<i class=\"el-icon-close my-tag-delete\" onclick=\"deleteStudent('"+students[i]["name"]+"')\"'></i></span>";
        }
        content = content+"<a onclick=\"openAddStudentDiv()\" id=\"add-student-a\"><i class=\"el-icon-plus\">新学员</i></a>";
        $("#student-tags").html(content);
    }

    /**
     * 计算总价
     */
    function calcucate() {
        var total = 0;
        var type = $("#enter-class-type").val();
        for(var i=0;i<classes.length;i++){
            if(classes[i]["classtype"]==type){
                total = students.length*parseFloat(classes[i]["price"]);
                break;
            }
        }
        actual = total*(1-level/100.0);
        actual = actual.toFixed(2);
        if(actual!=0){
            $("#actual-pay").html("需付"+actual+"元");
        }
    }

    /**
     * 检测是否会员
     */
    $("#test-vip-a").click(function () {
        $("#add-order-btn").removeAttr("disabled");
        $.ajax({
            url: "/insLesson.testVip",
            type: "post",
            dataType: "json",
            data: {
                userid: $("#enter-user-id").val(),
            },
            success: function (data) {
                var result = data["result"];
                if(result=="wrong id"){
                    $("#add-order-error").html("请将信息填写完整！");
                    $("#add-order-error").show();
                    setTimeout(function () {
                        $("#add-order-error").hide();
                    },1000);
                }else{
                    level = parseInt(result);
                    $("#add-order-error").html("验证成功！");
                    $("#add-order-error").show();
                    setTimeout(function () {
                        $("#add-order-error").hide();
                    },1000);
                    calcucate();
                }
            }
        });
    });

    /**
     * 立即下单
     */
    $("#add-order-btn").click(function () {
        var type = "";
        var userid = "";
        if($("#switch div").attr("aria-checked")=="true"){
            type = "是";
            userid = $("#enter-user-id").val();
        }else{
            type = "否";
        }
        var classtype = $("#enter-class-type").val();
        var name = new Array(students.length);
        var gender = new Array(students.length);
        var education = new Array(students.length);
        for(var i=0;i<students.length;i++){
            name[i] = students[i]["name"];
            gender[i] = students[i]["gender"];
            education[i] = students[i]["education"];
        }
        if(type=="是"&&userid==""){
            $("#add-order-error").html("请将信息填写完整！");
            $("#add-order-error").show();
            setTimeout(function () {
                $("#add-order-error").hide();
            },1000);
        }else if(name.length==0){
            $("#add-order-error").html("请将信息填写完整！");
            $("#add-order-error").show();
            setTimeout(function () {
                $("#add-order-error").hide();
            },1000);
        }else if(name.length>3){
            $("#add-order-error").html("每单不能超过3个学员！");
            $("#add-order-error").show();
            setTimeout(function () {
                $("#add-order-error").hide();
            },1000);
        }else{
            if($("#add-order-btn").text()=="计算总价"){
                calcucate();
                $("#add-order-btn").html("立即下单");
            }else{
                $.ajax({
                    url: "/myLesson.onSiteBook",
                    type: "post",
                    data: {
                        lessonid: lessonid,
                        type: type,
                        userid: userid,
                        classtype: classtype,
                        actual: actual,
                        nameList: name,
                        genderList: gender,
                        educationList: education,
                    },
                    dataType: "json",
                    success: function (data) {
                        $("#add-order-error").html("下单成功！");
                        $("#add-order-error").show();
                        setTimeout(function () {
                            $("#add-order-error").hide();
                        },1000);
                    }
                });
            }
        }
    });
});