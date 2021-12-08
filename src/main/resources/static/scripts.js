var stompClient = null;
var notificationCount = 0;

$(document).ready(function() {
    console.log("Index page is ready");
    connect();

    $("#send").click(function() {
        sendMessage();
    });

    $("#send-private").click(function() {
        sendPrivateMessage();
    });

    $("#notifications").click(function() {
        resetNotificationCount();
    });
});

function connect() {
    var socket = new SockJS('/our-websocket');
    let options = {debug: false, protocols: Stomp.VERSIONS.supportedProtocols()};
    stompClient = Stomp.over(socket, options);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        let headers = {Authorization: sessionStorage.getItem('access_token')};

        // STOMP Client의 header 부분에 집어넣어줍니다.
        this.stompClient.connect(headers, (frame) => {
            this.connected = true
            console.log('소켓 연결 성공', frame);
            this.stompClient.subscribe('/exchange/chat-exchange/msg.' + this.chatRequestDto.roomId, (tick) => {
                console.log(tick.body);
                this.chatLogs.push(JSON.parse(tick.body));
            })
        }, (error) => {
            console.log('연결실패');
            console.log(error)
            this.connected = false
        })




        updateNotificationDisplay();
        stompClient.subscribe('/topic/messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });

        stompClient.subscribe('/user/topic/private-messages', function (message) {
            showMessage(JSON.parse(message.body).content);
        });

        stompClient.subscribe('/topic/global-notifications', function (message) {
            notificationCount = notificationCount + 1;
            updateNotificationDisplay();
        });

        stompClient.subscribe('/user/topic/private-notifications', function (message) {
            notificationCount = notificationCount + 1;
            updateNotificationDisplay();
        });
    });
}

function showMessage(message) {
    $("#messages").append("<tr><td>" + message + "</td></tr>");
}

function sendMessage() {
    console.log("sending message");
    stompClient.send("/ws/message", {}, JSON.stringify({'messageContent': $("#message").val()}));
}

function sendPrivateMessage() {
    console.log("sending private message");
    stompClient.send("/ws/private-message", {}, JSON.stringify({'messageContent': $("#private-message").val()}));
}

function updateNotificationDisplay() {
    if (notificationCount == 0) {
        $('#notifications').hide();
    } else {
        $('#notifications').show();
        $('#notifications').text(notificationCount);
    }
}

function resetNotificationCount() {
    notificationCount = 0;
    updateNotificationDisplay();
}