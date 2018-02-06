
'use strict'

var students = new Array();

/**
 * 打开添加学员信息的div元素
 */
function openAddStudentDiv() {
    $("#add-student-div").show();
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
                var lessonid = data["lessonid"];
                var name = data["name"];
                var type = data["type"];
                var begin = data["begin"];
                var end = data["end"];
                var description = data["description"];
                var address = data["address"];
                var classhour = data["classhour"];
                var price = data["price"];

                var teacherList = data["teacherList"];
                var typeList = data["typeList"];
                var classNumList = data["classNumList"];
                var stuNumList = data["stuNumList"];
                var priceList = data["priceList"];

                var content = "";
                var state = "待定中";
                content = content+"<div class=\"show-one-plan\">\n" +
                    "                    <label class=\"plan-name\">"+name+"</label>\n" +
                    "                    <label>"+type+"</label>\n" +
                    "                    <br>\n" +
                    "                    <span class=\"description\">"+description+"</span>\n" +
                    "                    <br>\n" +
                    "                    <label class=\"detail\">课程时间："+begin+"至"+end+"</label>\n" +
                    "                    <label class=\"detail\">地址："+address+"</label>\n" +
                    "                    <label class=\"detail\">状态："+state+"</label>\n" +
                    "                    <label class=\"detail\">价格："+price+"元</label>\n" +
                    "                    <el-collapse>\n" +
                    "                        <el-collapse-item title=\"查看班级\">\n" +
                    "                            <table width=\"100%\">";
                for(var k=0;k<teacherList.length;k++){
                    content = content+"<tr><td>教师："+teacherList[k]+"</td><td>班级类型："+typeList[k]+"</td><td>班级数："+classNumList[k]
                        +"</td><td>容纳人数："+stuNumList[k]+"</td><td>价格："+priceList[k]+"元</td></tr>";
                }
                content = content+"</table>\n" +
                    "                        </el-collapse-item>\n" +
                    "                    </el-collapse>\n" +
                    "                    </div>";
                $("#lesson-message-div").html(content);
                new Vue().$mount("#lesson-message-div");

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

    $("#switch div").click(function () {
        if($("#switch div").attr("aria-checked")){
            $("#class-type-label").hide();
            $("#select-class-type").hide();
        }else{
            $("#class-type-label").show();
            $("#select-class-type").show();
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
            console.log(student);
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
     * 立即下单
     */
    $("#add-order-btn").click(function () {
        console.log("success");

    });
});