var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

// function connect() {
//     var socket = new SockJS('/ws');
//     stompClient = Stomp.over(socket);
//     stompClient.connect({}, function (frame) {
//         setConnected(true);
//         console.log('Connected: ' + frame);
//         stompClient.subscribe('/topic/teamId1', function (message) {
//             showGreeting(JSON.parse(message.body).text);
//         });
//     });
// }

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function dropFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

window.addEventListener('click', function () {
  function logIn() {
      if (userInfo == "a" && passwordInfo == "a")
  window.open = "index.html";

}


function sendMessage() {
    stompClient.send("/app/teamId1", {}, JSON.stringify({'text': $("#message").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendMessage(); });
});

window.addEventListener('load', function () {
    function connect() {
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/teamId1', function (message) {
            showGreeting(JSON.parse(message.body).text);
        });
    });
}
});