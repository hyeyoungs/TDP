$(document).ready(function () {
    if (localStorage.getItem('token')) {
        location.href = `/home.html`
    }
});

function cambiar_login() {
    document.querySelector('.cont_forms').className = "cont_forms cont_forms_active_login";
    document.querySelector('.cont_form_login').style.display = "block";
    document.querySelector('.cont_form_sign_up').style.opacity = "0";

    setTimeout(function () {
        document.querySelector('.cont_form_login').style.opacity = "1";
    }, 400);

    setTimeout(function () {
        document.querySelector('.cont_form_sign_up').style.display = "none";
    }, 200);
}

function cambiar_sign_up(at) {
    document.querySelector('.cont_forms').className = "cont_forms cont_forms_active_sign_up";
    document.querySelector('.cont_form_sign_up').style.display = "block";
    document.querySelector('.cont_form_login').style.opacity = "0";

    setTimeout(function () {
        document.querySelector('.cont_form_sign_up').style.opacity = "1";
    }, 100);

    setTimeout(function () {
        document.querySelector('.cont_form_login').style.display = "none";
    }, 400);
}

function ocular_login_sign_up() {

    document.querySelector('.cont_forms').className = "cont_forms";
    document.querySelector('.cont_form_sign_up').style.opacity = "0";
    document.querySelector('.cont_form_login').style.opacity = "0";


    setTimeout(function () {
        document.querySelector('.cont_form_sign_up').style.display = "none";
        document.querySelector('.cont_form_login').style.display = "none";
    }, 500);
}

function login() {
    let info = {
        username: $('#user_id').val(),
        password: $('#user_pw').val()
    }
    $.ajax({
        type: 'POST',
        url: `${domainURL}/login`,
        contentType: "application/json",
        data: JSON.stringify(info),
        success: function (response) {
            if (response) {
                localStorage.setItem('token', response['token']);
                localStorage.setItem('username',response['username']);
                alert('로그인 완료!');
                window.location.href = '/home.html';
            } else {
                alert('error');
            }
        }
    })
}

function sign_up() {
    let info = {
        username: $('#sign_id').val(),
        password: $('#sign_pw').val(),
        nickname: $('#sign_nick').val(),
        password_check: $('#sign_confirm_pw').val()
    }
    $.ajax({
        type: 'POST',
        url: `${domainURL}/signup`,
        contentType: "application/json",
        data: JSON.stringify(info),
        success: function (response) {
            if (response) {
                alert('회원가입이 완료되었습니다.');
                window.location.href = '/';
            } else {
                alert('error');
            }
        }
    })
}

function check_dup() {
    let user_id = $("#sign_id").val()
    let user_pw = $("#sign_pw").val()
    let user_nick = $("#sign_nick").val()
    let user_check_pw = $("#sign_confirm_pw").val()
    if (user_id === "") {
        alert("아이디를 입력해주세요")
        return
    }
    if (user_pw === "") {
        alert("비밀번호를 입력해주세요")
        return
    }
    if (user_nick === "") {
        alert("닉네임을 입력해주세요.")
        return
    }
    if(user_pw===user_check_pw){
        sign_up()
    }else{
        alert("비밀번호를 확인해주세요.")
    }
}

Kakao.init('0b012b5872605ba270c2c0573b61eb78');
function kakao_login() {
    Kakao.Auth.login({

        success: function(authObj) {
            let access_token=authObj['access_token'];
            $.ajax({
                type: 'POST',
                url: `${domainURL}/login/kakao`,
                contentType: "application/json",
                data: JSON.stringify({'token':access_token}),
                success: function (response) {
                    localStorage.setItem("access_token",access_token);
                    localStorage.setItem("token", response['token']);
                    localStorage.setItem("username", response['username']);
                    location.href = '/';
                }
            })
        },
        fail: function(err) {
            alert(JSON.stringify(err))
        },
    })
}