let urlEndpoint = '/subscribe';
let eventSource = new EventSource(urlEndpoint);

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