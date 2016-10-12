(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);throw new Error("Cannot find module '"+o+"'")}var f=n[o]={exports:{}};t[o][0].call(f.exports,function(e){var n=t[o][1][e];return s(n?n:e)},f,f.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
var stompClient = null;

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


function sendMessage(userName) {
    stompClient.send("/app/teamId1", {}, JSON.stringify({'messageName': "mike",'text': $("#message").val()}));
    }

function showGreeting(message) {
    $("#messagesBox").append(message);
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendMessage(); });
});

window.addEventListener('load', function () {
    function connect() {
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/teamId1', function (message) {
            showGreeting(JSON.parse(message.body).text);

        });

        stompClient.subscribe('/topic/matchupId1', function (message) {
            showGreeting(JSON.parse(message.body).text);
        });
    });
}
    connect();
});

},{}]},{},[1])