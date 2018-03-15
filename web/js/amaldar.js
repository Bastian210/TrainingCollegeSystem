
'use strict'

var register_array;
var changeMess_array;

/**
 * 拒绝注册申请
 * @param id
 */
function refuseRegister(id) {
    $.ajax({
        url: "/amaldar.refuseRegister",
        type: "post",
        dataType: 'json',
        data:{
            id: id,
        },
        success: function (data) {
            getRegisterApply();
        }
    });
}

/**
 * 同意注册申请
 * @param id
 */
function agreeRegister(id) {
    $.ajax({
        url: "/amaldar.agreeRegister",
        type: "post",
        dataType: "json",
        data: {
            id: id,
        },
        success: function (data) {
            getRegisterApply();
        }
    });
}

/**
 * 拒绝修改信息申请
 * @param id
 */
function refuseChange(id) {
    $.ajax({
        url: "/amaldar.refuseChange",
        type: "post",
        dataType: "json",
        data: {
            id: id,
        },
        success: function (data) {
            getChangeMessApply();
        }
    });
}

/**
 * 同意修改信息申请
 * @param id
 */
function agreeChange(id) {
    $.ajax({
        url: "/amaldar.agreeChange",
        type: "post",
        dataType: "json",
        data: {
            id: id,
        },
        success: function (data) {
            getChangeMessApply();
        }
    });
}

/**
 * 得到所有注册申请
 */
function getRegisterApply() {
    $.ajax({
        url:"/amaldar.getRegisterApply",
        type: "post",
        dataType: 'json',
        success: function (data) {
            register_array = data["result"];

            if(register_array.length==0){
                $("#register-apply-div").html("<p>没有等待处理的注册请求！</p>");
            }else{
                if(register_array.length>10){
                    var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"10\" :total=\""+register_array.length+"\" @current-change=\"handleCurrentChange\">\n" +
                        "                </el-pagination>";
                    $("#register-apply-pagination").html(content);

                    var Main = {
                        methods: {
                            handleCurrentChange(val) {
                                PagingDisplayRegister(val);
                            }
                        }
                    }
                    var Ctor = Vue.extend(Main);
                    new Ctor().$mount("#register-apply-pagination");
                }
                PagingDisplayRegister(1);
            }
        }
    });
}

/**
 * 得到所有消息修改申请
 */
function getChangeMessApply() {
    $.ajax({
        url:"/amaldar.getChangeMessApply",
        type: "post",
        dataType: 'json',
        success: function (data) {
            changeMess_array = data["result"];

            if(changeMess_array.length==0){
                $("#changeMess-apply-div").html("<p>没有等待处理的机构信息修改请求！</p>");
            }else{
                if(changeMess_array.length>10){
                    var content = "<el-pagination background layout=\"total, prev, pager, next, jumper\" :page-size=\"10\" :total=\""+changeMess_array.length+"\" @current-change=\"handleCurrentChange\">\n" +
                        "                </el-pagination>";
                    $("#changeMess-apply-pagination").html(content);

                    var Main = {
                        methods: {
                            handleCurrentChange(val) {
                                PagingDisplayChangeMess(val);
                            }
                        }
                    }
                    var Ctor = Vue.extend(Main);
                    new Ctor().$mount("#changeMess-apply-pagination");
                }
                PagingDisplayChangeMess(1);
            }
        }
    });
}

/**
 * 分页展示注册申请列表
 * @param val
 * @constructor
 */
function PagingDisplayRegister(val) {
    var begin = (val-1)*10;
    var end = 0;
    if(register_array.length-begin<10){
        end = register_array.length;
    }else{
        end = begin+10;
    }

    var content = "<table width=\"100%\">\n" +
        "                    <tr><th>机构名称</th><th>机构地址</th><th>联系方式</th><th>操作类型</th></tr>";
    for(var i=begin;i<end;i++){
        var json = JSON.parse(JSON.stringify(register_array[i]));
        content = content+"<tr><td>"+json["name"]+"</td><td>"+json["address"]+"</td><td>"+json["phone"]+"</td><td><a onclick=\"refuseRegister('"
            +json["id"]+"')\">拒绝</a><a onclick=\"agreeRegister('"+json["id"]+"')\">批准</a></td></tr>";
    }
    content = content+"</table>";
    $("#register-apply-div").html(content);
}

/**
 * 分页展示修改机构信息申请列表
 * @param val
 * @constructor
 */
function PagingDisplayChangeMess(val) {
    var begin = (val-1)*10;
    var end = 0;
    if(changeMess_array.length-begin<10){
        end = changeMess_array.length;
    }else{
        end = begin+10;
    }

    var content = "<table width=\"100%\">\n" +
        "                    <tr><th>机构识别号</th><th>机构名称</th><th>机构地址</th><th>联系方式</th><th>操作类型</th></tr>";
    for(var i=begin;i<end;i++){
        var json = JSON.parse(JSON.stringify(changeMess_array[i]));
        content = content+"<tr><td>"+json["id"]+"</td><td>"+json["name"]+"</td><td>"+json["address"]+"</td><td>"+json["phone"]+"</td><td><a onclick=\"refuseChange('"
            +json["id"]+"')\">拒绝</a><a onclick=\"agreeChange('"+json["id"]+"')\">批准</a></td></tr>";
    }
    content = content+"</table>";
    $("#changeMess-apply-div").html(content);
}

$(function () {
    $(document).ready(function () {
        getRegisterApply();
        getChangeMessApply();
    });

    $("#register-apply-a").click(function () {
        $("#register-apply-a").attr("class","active");
        $("#changeMess-apply-a").removeAttr("class");
        $("#register-apply-div").show();
        $("#changeMess-apply-div").hide();
    });

    $("#changeMess-apply-a").click(function () {
        $("#register-apply-a").removeAttr("class");
        $("#changeMess-apply-a").attr("class","active");
        $("#register-apply-div").hide();
        $("#changeMess-apply-div").show();
    });
});