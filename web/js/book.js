
'use strict'

var lessonid = "";
var institutionid = "";

var students = new Array();

var classes = new Array();

var total = 0;
var discount = 0;
var reserve = 0;
var actual = 0;


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
                lessonid = data["lessonid"];
                institutionid = data["institutionid"];
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

                for(var i=0;i<typeList.length;i++){
                    var cla = {classtype: typeList[i],price: priceList[i]};
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

        $.ajax({
            url: "/book.getUserPayMessage",
            type: "post",
            dataType: "json",
            success: function (data) {
                reserve = (parseInt(data["points"])/100).toFixed(2);
                $("#points").html("当前积分:"+data["points"]+",使用后可优惠"+reserve+"元");
                $("#discount").html("您是"+data["level"]+"级会员，为您优惠"+data["level"]+"%");
                if(data["payid"]=="not"){
                    $("#no-payid-modal").modal("show");
                }
                discount = parseInt(data["level"])/100;
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
            students.push(student);
            showStudents();
            $("#add-student-div").hide();

            calcucate();
            clearAddStudentDiv();
        }
    });

    $('#enter-class-type').bind('input propertychange', function() {
        console.log("success");
        calcucate();
    });

    $("#use-points").click(function () {
        calcucate();
    })


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
        if(!$("#switch div").attr("aria-checked")){ //不选班级按最高价计算
            var max = 0;
            for(var i=0;i<classes.length;i++){
                if(parseFloat(classes[i]["price"])>max){
                    max = parseFloat(classes[i]["price"]);
                }
            }
            total = max*students.length;
        }else{
            var type = $("#enter-class-type").val();
            for(var i=0;i<classes.length;i++){
                if(classes[i]["classtype"]==type){
                    total = students.length*parseFloat(classes[i]["price"]);
                    break;
                }
            }
        }
        $("#need-price").html("合计"+total+"元");
        actual = total*(1-discount);
        if($("#use-points").prop("checked")){
            actual = actual-reserve;
        }
        actual = actual.toFixed(2);
        $("#actual-pay").html("需付"+actual+"元");
    }

    $("#bind-pay-account-btn").click(function () {
        window.open("/accountManagement","_self");
    });

    /**
     * 立即下单
     */
    $("#add-order-btn").click(function () {
        var type = "";
        if(!$("#switch div").attr("aria-checked")){
            type = "不选班级";
        }else{
            type = "选班级";
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
        if(type=="选班级"||classtype==""){

        }
        $.ajax({
            url: "/book.addOrder",
            type: "post",
            data: {
                lessonid: lessonid,
                institutionid: institutionid,
                type: type,
                price: total,
                actualpay: actual,
                classtype: classtype,
                nameList: name,
                genderList: gender,
                educationList: education,
            },
            dataType: "json",
            success: function (data) {
                console.log(data["deadline"]);
                $("#add-order-form").hide();
                $("#order-success-div").show();
                $("#order-success-message").html("下单成功！请在"+data["deadline"]+"之前支付，否则将自动取消订单！")
            }
        });
    });
});