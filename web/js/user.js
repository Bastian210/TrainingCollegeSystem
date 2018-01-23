
'use strict';
$(function () {
    $(document).ready(function(){
    });

    $("#login-btn").click(function(){
        console.log("success");
        $.ajax({
            url:"/index",
            dataType: 'json',
            type: 'post',
            data: {
                userid: $("#userid").val(),
                password: $("#password").val(),
            },
            success: function (data) {
                console.log(data);
                console.log(data["result"]);
                console.log(data["array"]);
                console.log(data["num"])
                var list = data["array"];
                for(var i=0;i<list.length;i++){
                    console.log(list[i]);
                }
            },
        });
    });
});
