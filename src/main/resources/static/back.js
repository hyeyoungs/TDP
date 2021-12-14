let urlEndpoint = 'http://api.tildp.shop/subscribe';
let eventSource = new EventSource(urlEndpoint);
let domainURL= 'http://api.tildp.shop';
// let domainURL= 'http://localhost:8080';

eventSource.addEventListener("latestNews", function (event) {
    let articleData = JSON.parse(event.data);
    let title = articleData.tilTitle;
    let content = articleData.til_content;
    $('#til_title').text(title);
    $('#til_content').text(content);
    $('#liveToast').toast('show');
});


function toast_close(){
    $('#liveToast').toast('dispose')
}


function login_check(options, originalOptions, jqXHR){
    if(localStorage.getItem('token')) {
        jqXHR.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));
    } else {
        alert("로그인 해주세요")
        location.href =  `/index.html`
    }
}


function sign_out() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    alert('로그아웃!')
    window.location.href = "/"
}

function goback() {
    window.history.back();
}

function read_user() {
    $.ajax({
        type: "GET",
        url: `${domainURL}/user`,
        async: false,
        success: function (response) {
            user_info = response;
        }
    });
    return user_info;
}
