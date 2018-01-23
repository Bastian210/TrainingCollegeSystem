<%--
  Created by IntelliJ IDEA.
  User: Bastian Fei
  Date: 2018/1/23
  Time: 15:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="/js/user.js"></script>
</head>
<body>
    <input type="text" id="userid">
    <input type="password" id="password">
    <button type="button" value="提交" id="login-btn">提交</button>

<script>
    // $("#login-btn").click(function(){
    //     console.log("success");
    //     $.ajax({
    //         url:"/index",
    //         dataType: 'json',
    //         type: 'post',
    //         data: {
    //             userid: $("#userid").val(),
    //             password: $("#password").val(),
    //         },
    //         success: function (data) {
    //             console.log(data);
    //             console.log(data["result"]);
    //             console.log(data["array"]);
    //             console.log(data["num"])
    //             list = data["array"];
    //             for(var i=0;i<list.length;i++){
    //                 console.log(list[i]);
    //             }
    //         },
    //     });
    // })
</script>
</body>
</html>
