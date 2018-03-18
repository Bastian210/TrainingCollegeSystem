
'use strict'

$(function () {
    $("#logout-a").click(function () {
       $.ajax({
           url: "/user.logout",
           type: "post",
           dataType: "json",
           success: function (data) {

           }
       });
    });

    $("#ins-logout-a").click(function () {
        $.ajax({
            url: "/institution.logout",
            type: "post",
            dataType: "json",
            success: function (data) {

            }
        });
    });

    $("#manager-logout-a").click(function () {
        $.ajax({
            url: "/manager.logout",
            type: "post",
            dataType: "json",
            success: function (data) {

            }
        });
    });
});