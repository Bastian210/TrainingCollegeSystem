
'use strict'

var teacherslist = new Array(3);
var typelist = new Array(3);
var classnumlist = new Array(3);
var stunumlist = new Array(3);
var pricelist = new Array(3);

/**
 * 编辑班级
 * @param i
 */
function editClass(i) {
    $("#enter-teachers").val(teacherslist[i]);
    $("#select-class-type").val(typelist[i]);
    $("#enter-class-number input").val(classnumlist[i]);
    $("#enter-student-number input").val(stunumlist[i]);
    $("#enter-price").val(pricelist[i]);

    $("#edit-class-div").show();
    $("#add-class-a").hide();

    $("#edit-id").html(i);
    $("#type").html("edit");
}

/**
 * 删除班级
 * @param i
 */
function deleteClass(i) {
    teacherslist[i] = "";
    typelist[i] = "";
    classnumlist[i] = "";
    stunumlist[i] = "";
    pricelist[i] = "";

    var teacherList = new Array(3);
    var typeList = new Array(3);
    var classNumList = new Array(3);
    var stuNumList = new Array(3);
    var priceList = new Array(3);

    var k = 0;
    for(var i=0;i<3;i++){
        if(teacherslist[i]!=""){
            teacherList[k] = teacherslist[i];
            typeList[k] = typelist[i];
            classNumList[k] = classnumlist[i];
            stuNumList[k] = stunumlist[i];
            priceList[k] = pricelist[i];
            k++;
        }
    }
    for(;k<3;k++){
        teacherList[k] = "";
        typeList[k] = "";
        classNumList[k] = "";
        stuNumList[k] = "";
        priceList[k] = "";
    }

    for(var i=0;i<3;i++){
        teacherslist[i] = teacherList[i];
        typelist[i] = typeList[i];
        classnumlist[i] = classNumList[i];
        stunumlist[i] = stuNumList[i];
        pricelist[i] = priceList[i];
    }

    diaplayAllClass();
}

/**
 * 展示已保存的班级
 */
function diaplayAllClass() {
    var content = "";
    for(var i=0;i<3;i++){
        if(teacherslist[i]!==""){
            content = content+"<tr><td>教师："+teacherslist[i]+"</td><td>班级类型："+typelist[i]+"</td><td>班级数："+classnumlist[i]
                +"</td><td>容纳人数："+stunumlist[i]+"</td><td>价格："+pricelist[i]+"元</td><td><i class=\"el-icon-edit\" onclick='editClass("
                +i+")'></i><i class=\"el-icon-delete\" onclick='deleteClass("+i+")'></i></td></tr>";
        }
    }
    $("#display-class").html(content);
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
                var classhour = data["classhour"];

                var teacherList = data["teacherList"];
                var typeList = data["typeList"];
                var classNumList = data["classNumList"];
                var stuNumList = data["stuNumList"];
                var priceList = data["priceList"];

                $("#lessonid-span").html(lessonid);
                $("#enter-lesson-name").val(name);
                $("#cascader input").attr("value",type);
                $("#enter-begin-date").val(begin);
                $("#enter-end-date").val(end);
                $("#enter-class-description").val(description);
                $("#enter-class-hour input").val(classhour);

                var content = "";
                for(var i=0;i<teacherList.length;i++){
                    content = content+"<tr><td>教师："+teacherList[i]+"</td><td>班级类型："+typeList[i]+"</td><td>班级数："+classNumList[i]
                        +"</td><td>容纳人数："+stuNumList[i]+"</td><td>价格："+priceList[i]+"元</td><td><i class=\"el-icon-edit\" onclick='editClass("
                        +i+")'></i><i class=\"el-icon-delete\" onclick='deleteClass("+i+")'></i></td></tr>";
                }
                $("#display-class").html(content);

                for(var i=0;i<3;i++){
                    if(i<teacherList.length){
                        teacherslist[i] = teacherList[i];
                        typelist[i] = typeList[i];
                        classnumlist[i] = classNumList[i];
                        stunumlist[i] = stuNumList[i];
                        pricelist[i] = priceList[i];
                    }else{
                        teacherslist[i] = "";
                        typelist[i] = "";
                        classnumlist[i] = "";
                        stunumlist[i] = "";
                        pricelist[i] = "";
                    }
                }
            }
        });
    });

    /**
     * 添加新类型的班级
     */
    $("#add-class-a").click(function () {
        if(teacherslist[2]==""){
            $("#edit-class-div").show();
            $("#add-class-a").hide();
            $("#type").html("add");
        }else{
            $("#add-plan-error").html("班级类型已满！");
            $("#add-plan-error").show();
            setTimeout(function () {
                $("#add-plan-error").hide();
            },1000);
        }
    });

    /**
     * 添加教师
     */
    $("#add-teacher-a").click(function () {
        var teachers = $("#enter-teachers").val();
        if(teachers==""||teachers[teachers.length-1]==";"){
        }else{
            $("#enter-teachers").val(teachers+";");
        }
    });

    /**
     * 取消添加/编辑
     */
    $("#cancel-edit-btn").click(function () {
        $("#edit-class-div").hide();
        $("#add-class-a").show();
        $("#enter-teachers").val("");
        $("#select-class-type").val("");
        $("#enter-class-number input").val(1);
        $("#enter-student-number input").val(1);
        $("#enter-price").val("");
    });

    /**
     * 保存添加/编辑班级
     */
    $("#save-class-btn").click(function () {
        var teachers = $("#enter-teachers").val();
        var type = $("#select-class-type").val();
        var classNum = $("#enter-class-number input").val();
        var stuNum = $("#enter-student-number input").val();
        var price = $("#enter-price").val();
        var reg1 = /^[0-9]*$/;
        var reg2 = /^\d+(\.\d+)?$/;

        if(teachers==""||type==""||classNum==""||stuNum==""||stuNum==""){
            $("#edit-class-error").html("信息填写不完整！");
            $("#edit-class-error").show();
            setTimeout(function () {
                $("#edit-class-error").hide();
            },1000);
        }else if(!reg1.test(classNum)||!reg1.test(stuNum)||!reg2.test(price)){
            $("#edit-class-error").html("班级数和容纳人数不正确！");
            $("#edit-class-error").show();
            setTimeout(function () {
                $("#edit-class-error").hide();
            },1000);
        }else if(type==typelist[0]||type==typelist[1]||type==typelist[2]){
            $("#edit-class-error").html("已有此班级类型！");
            $("#edit-class-error").show();
            setTimeout(function () {
                $("#edit-class-error").hide();
            },1000);
        }else{
            var i = 0;
            if($("#type").text()=="add"){
                for(var i=0;i<3;i++){
                    if(teacherslist[i]==""){
                        teacherslist[i] = teachers;
                        typelist[i] = type;
                        classnumlist[i] = classNum;
                        stunumlist[i] = stuNum;
                        pricelist[i] = price;
                        break;
                    }
                }
            }else{
                i = $("#edit-id").text();
                teacherslist[i] = teachers;
                typelist[i] = type;
                classnumlist[i] = classNum;
                stunumlist[i] = stuNum;
                pricelist[i] = price;
            }

            $("#edit-class-div").hide();
            $("#add-class-a").show();
            diaplayAllClass();

            $("#enter-teachers").val("");
            $("#select-class-type").val("");
            $("#enter-class-number input").val(1);
            $("#enter-student-number input").val(1);
            $("#enter-price").val("");
        }
    });

    /**
     * 展示已保存的班级
     */
    function diaplayAllClass() {
        var content = "";
        for(var i=0;i<3;i++){
            if(teacherslist[i]!=""){
                content = content+"<tr><td>教师："+teacherslist[i]+"</td><td>班级类型："+typelist[i]+"</td><td>班级数："+classnumlist[i]
                    +"</td><td>容纳人数："+stunumlist[i]+"</td><td>价格："+pricelist[i]+"元</td><td><i class=\"el-icon-edit\" onclick='editClass("
                    +i+")'></i><i class=\"el-icon-delete\" onclick='deleteClass("+i+")'></i></td></tr>";
            }
        }
        $("#display-class").html(content);
    }

    /**
     * 添加计划
     */
    $("#add-plan-btn").click(function () {
        var teacherList = new Array();
        var typeList = new Array();
        var classNumList = new Array();
        var stuNumList = new Array();
        var priceList = new Array();

        var k = 0;
        for(var i=0;i<3;i++){
            if(teacherslist[i]!=""){
                teacherList[k] = teacherslist[i];
                typeList[k] = typelist[i];
                classNumList[k] = classnumlist[i];
                stuNumList[k] = stunumlist[i];
                priceList[k] = pricelist[i];
                k++;
            }
        }

        var name = $("#enter-lesson-name").val();
        var type = $("#cascader input").attr("value");
        var begin = $("#enter-begin-date").val();
        var end = $("#enter-end-date").val();
        var classhour = $("#enter-class-hour input").val();
        var description = $("#enter-class-description").val();

        var fulldate1 = begin.split("-");
        var date1 = new Date(fulldate1[0],fulldate1[1],fulldate1[2]);
        var fulldate2 = end.split("-");
        var date2 = new Date(fulldate2[0],fulldate2[1],fulldate2[2])
        var reg = /^[0-9]*$/;

        if(name==""||type==""||begin==""||end==""||classhour==""||description==""){
            $("#add-plan-error").html("信息填写不完整！");
            $("#add-plan-error").show();
            setTimeout(function () {
                $("#add-plan-error").hide();
            },1000);
        }else if(!reg.test(classhour)){
            $("#add-plan-error").html("课时必须为整数！");
            $("#add-plan-error").show();
            setTimeout(function () {
                $("#add-plan-error").hide();
            },1000);
        }else if(teacherList.length==0){
            $("#add-plan-error").html("必须添加班级！");
            $("#add-plan-error").show();
            setTimeout(function () {
                $("#add-plan-error").hide();
            },1000);
        }else if(date1>date2||date1<Date.now()){
            $("#add-plan-error").html("课程时间错误！");
            $("#add-plan-error").show();
            setTimeout(function () {
                $("#add-plan-error").hide();
            },1000);
        }else{
            $.ajax({
                url: "/editPlan.editPlan",
                type: "post",
                dataType: "json",
                data: {
                    lessonid: $("#lessonid-span").text(),
                    name: name,
                    type: type,
                    begin: begin,
                    end: end,
                    classhour: classhour,
                    description: description,
                    teacherList: teacherList,
                    typeList: typeList,
                    classNumList: classNumList,
                    stuNumList: stuNumList,
                    priceList: priceList,
                },
                success: function (data) {
                    $("#add-plan-error").html("更新成功！");
                    $("#add-plan-error").show();
                    setTimeout(function () {
                        $("#add-plan-error").hide();
                    },1000);
                }
            });
        }
    });
});