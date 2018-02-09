
'use strict'

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
            var result = data["result"];

            var content = "<table width=\"100%\">\n" +
                "                    <tr><th>机构识别号<th>机构名称</th><th>机构地址</th><th>联系方式</th><th>操作类型</th></tr>";
            for(var i=0;i<result.length;i++){
                var json = JSON.parse(JSON.stringify(result[i]));
                content = content+"<tr><td>"+json["id"]+"</td><td>"+json["name"]+"</td><td>"+json["address"]+"</td><td>"+json["phone"]+"</td><td><a onclick=\"refuseRegister('"
                    +json["id"]+"')\">拒绝</a><a onclick=\"agreeRegister('"+json["id"]+"')\">批准</a></td></tr>";
            }
            content = content+"</table>";
            $("#register-apply-div").html(content);
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
            var result = data["result"];

            var content = "<table width=\"100%\">\n" +
                "                    <tr><th>机构名称</th><th>机构地址</th><th>联系方式</th><th>操作类型</th></tr>";
            for(var i=0;i<result.length;i++){
                var json = JSON.parse(JSON.stringify(result[i]));
                content = content+"<tr><td>"+json["name"]+"</td><td>"+json["address"]+"</td><td>"+json["phone"]+"</td><td><a onclick=\"refuseChange('"
                    +json["id"]+"')\">拒绝</a><a onclick=\"agreeChange('"+json["id"]+"')\">批准</a></td></tr>";
            }
            content = content+"</table>";
            $("#changeMess-apply-div").html(content);
        }
    });
}

$(function () {
    $(document).ready(function () {
        getRegisterApply();
        getChangeMessApply();
    });

    /**
     * 得到所有注册申请
     */
    function getRegisterApply() {
        $.ajax({
            url:"/amaldar.getRegisterApply",
            type: "post",
            dataType: 'json',
            success: function (data) {
                var result = data["result"];

                var content = "<table width=\"100%\">\n" +
                    "                    <tr><th>机构识别号<th>机构名称</th><th>机构地址</th><th>联系方式</th><th></th><th>操作类型</th></tr>";
                for(var i=0;i<result.length;i++){
                    var json = JSON.parse(JSON.stringify(result[i]));
                    content = content+"<tr><td>"+json["id"]+"</td><td>"+json["name"]+"</td><td>"+json["address"]+"</td><td>"+json["phone"]+"</td><td><a onclick=\"refuseRegister('"
                        +json["id"]+"')\">拒绝</a><a onclick=\"agreeRegister('"+json["id"]+"')\">批准</a></td></tr>";
                }
                content = content+"</table>";
                $("#register-apply-div").html(content);
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
                var result = data["result"];

                var content = "<table width=\"100%\">\n" +
                    "                    <tr><th>机构名称</th><th>机构地址</th><th>联系方式</th><th>操作类型</th></tr>";
                for(var i=0;i<result.length;i++){
                    var json = JSON.parse(JSON.stringify(result[i]));
                    content = content+"<tr><td>"+json["name"]+"</td><td>"+json["address"]+"</td><td>"+json["phone"]+"</td><td><a onclick=\"refuseChange('"
                        +json["id"]+"')\">拒绝</a><a onclick=\"agreeChange('"+json["id"]+"')\">批准</a></td></tr>";
                }
                content = content+"</table>";
                $("#changeMess-apply-div").html(content);
            }
        });
    }

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