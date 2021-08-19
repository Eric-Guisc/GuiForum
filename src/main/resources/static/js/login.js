var contextPath = "/forum";

function refresh_kaptcha() {
    var path = "/forum/kaptcha?p=" + Math.random();
    $("#kaptcha1").attr("src", path);
  };

$(document).ready(function () {
    const userName = localStorage.getItem("userName");
    const userPass = localStorage.getItem("userPass");
    if (userName != null) {
        $("#userName").val(userName);
    }
    if (userPass != null) {
        $("#userPass").val(userPass);
    }
});

$('#btn-login').click(function () {
    let prevLink = document.referrer;
    $('#btn-login').button('loading');
    const usernameOrEmail = $("#usernameOrEmail").val();
    const password = $("#password").val();
    const kaptcha = $("#kaptcha").val();
    const rememberMe = $("#rememberMe").prop("checked");
    if (usernameOrEmail == "" || password == "") {
        showMsg("请输入完整信息！", "error", 1000);
        $('#btn-login').button('reset');
    } else {
        $.ajax({
            type: 'POST',
            url: contextPath + '/login',
            async: false,
            data: {
                'usernameOrEmail': usernameOrEmail,
                'password': password,
                'kaptcha': kaptcha,
                'rememberMe': rememberMe
            },
            success: function (data) {
                if (rememberMe) {
                    localStorage.setItem('usernameOrEmail', $("#usernameOrEmail").val());
                    localStorage.setItem('password', $("#password").val());
                } else {
                    localStorage.setItem('usernameOrEmail', '');
                    localStorage.setItem('password', '');
                }
                if (data.code == 1) {
                    if ($.trim(prevLink) == '' || $.trim(prevLink).indexOf('/login') != -1 || $.trim(prevLink).indexOf('/register') != -1 || $.trim(prevLink).indexOf('/forget') != -1) {
                        prevLink = contextPath + '/index';
                    }
                    showMsgAndRedirect(data.msg, "success", 1000, prevLink);
                } else {
                    showMsg(data.msg, "error", 1000);
                    $('#btn-login').button('reset');
                    refresh_kaptcha();
                }
            }
        });
    }
})


$('#btn-register').click(function () {
    $('#btn-register').button('loading');
    const username = $("#username").val();
    const password = $("#password").val();
    const email = $("#email").val();
    const kaptcha = $("#kaptcha").val();
    if (username == "" || password == "" || email == "") {
        showMsg("请输入完整信息！", "error", 1000);
        $('#btn-register').button('reset');
    } else {
        $.ajax({
            type: 'POST',
            url: contextPath + '/register',
            async: false,
            data: {
                'username': username,
                'password': password,
                'email': email,
                'kaptcha': kaptcha
            },
            success: function (data) {
                if (data.code == 1) {
                    showMsgAndRedirect(data.msg, "success", 2000, contextPath + "/login");
                } else {
                    showMsg(data.msg, "error", 1000);
                    $('#btn-register').button('reset');
                    localStorage.setItem('username', username);
                    refresh_kaptcha();
                }
            }
        });
    }
});


$('#btn-forget').click(function () {
    const userName = $("#userName").val();
    const userEmail = $("#userEmail").val();
    if (userName == "" || userEmail == "") {
        showMsg("请输入完整信息！", "error", 1000);
        $('#btn-register').button('reset');
    } else {
        $.ajax({
            type: 'POST',
            url: '/forget',
            async: false,
            data: {
                'userName': userName,
                'userEmail': userEmail
            },
            success: function (data) {
                if (data.code == 1) {
                    showMsgAndRedirect(data.msg, "success", 2000, "/login");
                } else {
                    showMsg(data.msg, "error", 1000);
                    $('#btn-forget').button('reset');
                    localStorage.setItem('userName', userName);
                }
            }
        });
    }
});
