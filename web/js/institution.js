
'use strict';

var teacherslist = new Array(3);
var typelist = new Array(3);
var classnumlist = new Array(3);
var stunumlist = new Array(3);
var pricelist = new Array(3);

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
                +"</td><td>容纳人数："+stunumlist[i]+"</td><td>价格："+pricelist[i]+"</td><td><i class=\"el-icon-edit\" onclick='editClass("
                +i+")'></i><i class=\"el-icon-delete\" onclick='deleteClass("+i+")'></i></td></tr>";
        }
    }
    $("#display-class").html(content);
}

$(function () {
    $(document).ready(function () {
        for(var i=0;i<3;i++){
            teacherslist[i] = "";
            typelist[i] = "";
            classnumlist[i] = "";
            stunumlist[i] = "";
            pricelist[i] = "";
        }
    });
    /**
     * 机构注册
     */
    $("#institution-register-btn").click(function () {
        var name = $("#enter-institution-name").val();
        var address = $("#enter-address").val();
        var phone = $("#enter-phone").val();
        var password1 = $("#enter-institution-password1").val();
        var password2 = $("#enter-institution-password2").val();
        var reg1 = /^1(3|4|5|7|8)\\d{9}$/;
        var reg2 = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
        if(name==""||address==""||phone==""||password1==""||password2==""){
            $("#institution-register-error").html("请将注册信息填写完整！");
            $("#institution-register-error").show();
            setTimeout(function () {
                $("#institution-register-error").hide();
            },1000);
        }else if(password1.length<6||password1.length>16){
            $("#institution-register-error").html("密码长度不规范！");
            $("#institution-register-error").show();
            setTimeout(function () {
                $("#institution-register-error").hide();
            },1000);
        }else if(password2!=password1){
            $("#institution-register-error").html("两次密码不相同！");
            $("#institution-register-error").show();
            setTimeout(function () {
                $("#institution-register-error").hide();
            },1000);
        }else if(!(reg1.test(phone)||reg2.test(phone))){
            $("#institution-register-error").html("联系方式填写不正确！");
            $("#institution-register-error").show();
            setTimeout(function () {
                $("#institution-register-error").hide();
            },1000);
        }else{
            $.ajax({
                url: "/register.institution",
                type: "post",
                data: {
                    name: name,
                    address: address,
                    phone: phone,
                    password: password1
                },
                dataType: "json",
                success: function (data) {
                    var result = data["result"];
                    if(result=="has register"){
                        $("#institution-register-error").html("该机构已被注册！");
                        $("#institution-register-error").show();
                        setTimeout(function () {
                            $("#institution-register-error").hide();
                        },1000);
                    }else{
                        //显示识别码，并让其等待审核
                        $("#ins-id").html("此机构识别码为"+result+"！");
                        $("#insReg-success-modal").modal("show");
                    }
                }
            });
        }
    });

    /**
     * 机构登录
     */
    $("#institution-login-btn").click(function () {
        var id = $("#enter-institution-id").val();
        var password = $("#enter-institution-password").val();
        if(id==""||password==""){
            $("#institution-login-error").html("登陆信息填写不完整！");
            $("#institution-login-error").show();
            setTimeout(function () {
                $("#institution-login-error").hide();
            },1000);
        }else{
            $.ajax({
                url: "/login.institution",
                type: "post",
                dataType: "json",
                data: {
                    id: id,
                    password: password
                },
                success: function (data) {
                    var result = data["result"];
                    if(result=="wrong id"){
                        $("#institution-login-error").html("识别码错误！");
                        $("#institution-login-error").show();
                        setTimeout(function () {
                            $("#institution-login-error").hide();
                        },1000);
                    }else if(result=="wrong password"){
                        $("#institution-login-error").html("密码错误！");
                        $("#institution-login-error").show();
                        setTimeout(function () {
                            $("#institution-login-error").hide();
                        },1000);
                    }else if(result=="still in check"){
                        $("#institution-login-error").html("此账号仍在审核中！");
                        $("#institution-login-error").show();
                        setTimeout(function () {
                            $("#institution-login-error").hide();
                        },1000);
                    }else{
                        //登录成功
                        window.open("/institution","_self");
                    }
                }
            });
        }
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
     * 保存添加/编辑
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
                    +"</td><td>容纳人数："+stunumlist[i]+"</td><td>价格："+pricelist[i]+"</td><td><i class=\"el-icon-edit\" onclick='editClass("
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
        var type = $("#enter-lesson-type").val();
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
                url: "/institution.addPlan",
                type: "post",
                dataType: "json",
                data: {
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
                    $("#add-plan-error").html("添加成功！");
                    $("#add-plan-error").show();
                    setTimeout(function () {
                        $("#add-plan-error").hide();
                    },1000);
                }
            });
        }
    });
});