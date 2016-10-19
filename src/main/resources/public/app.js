let app = angular.module('sportsApp', ['ui.router']);

/**
 * StateProvider is how we set up routes.
 */
app.config(function ($stateProvider) {
    $stateProvider.state({
        name: 'teamRoom',
        url: '/team',
        component: 'teamwindow',
    });

    $stateProvider.state({
        name: 'matchRoom',
        url: '/match',
        component: 'matchwindow',
    });

});

app.component('teamwindow', {
    templateUrl: 'components/teamRoom.html',
    controller: 'TeamController',
});

app.component('scoreboard', {
    templateUrl: 'components/scoreboard.html',
    controller: 'scoreController',
});

app.component('matchwindow', {
    templateUrl: 'components/matchRoom.html',
    controller: 'matchController',
});

app.controller('TeamController', function ($scope, $interval, messageService) {
    messageService.setCurrent('team');
    $scope.messages = messageService.getMessages();

});

app.controller('matchController', function ($scope, messageService) {
    messageService.setCurrent('match');
    $scope.messages = messageService.getMessages();
});

app.controller('scoreController', function($scope, messageService) {
    $scope.scorez = messageService.getScores();
    $scope.abbrvs = messageService.getAbrv();
})

app.controller('sendController', function ($scope, messageService) {
    $scope.send = function(){
    messageService.sendMessage($scope.messageValue);

    $scope.messageValue = '';
    };
})

// Factories return SERVICES.
app.factory("messageService", function ($rootScope) {
    let messages = {
        team: [],
        match: [],
    };
    let scores = {
        home: 0,
        away: 0,
    };

    let abbreviation = {
        aAbrv: awayTeam,
        hAbrv: homeTeam,
        tAbrv: teamTeam,
    };




    let currentRoom = 'team';
    let onUpdate = null;

    // websocket code here
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/teamId/' + teamId, function (message) {
            $rootScope.$apply(function () {
            let allMessage = JSON.parse(message.body);
            if (allMessage.messageType === 'chat') {
                messages.team.push(allMessage.body);
            } else if (allMessage.messageType === 'score') {

                scores.home = allMessage.body.homeScore;
                scores.away = allMessage.body.awayScore;
                console.log(scores);
            }




            });
        });

        stompClient.subscribe('/topic/matchupId/' + matchupId, function (message) {
            $rootScope.$apply(function () {
                let matchMessage = JSON.parse(message.body);
                if (matchMessage.messageType === 'chat') {
                messages.match.push(matchMessage.body);
                } else if (matchMessage.messageType === 'score') {
                    scores.match.push(matchMessage.body.homeScore);
                }
          
            });
        });
    });

    return {
        // 
        getMessages: function () {
            return messages[currentRoom];
        },

        onNewMessage: function (fn) {
            onUpdate = fn;
        },
        // need functions to get data from the services
        getScores: function () {
            return scores;
        },
        getAbrv: function () {
            return abbreviation;
        },

        sendMessage: function(valuable) {

            if (currentRoom === 'team') {
            stompClient.send("/app/teamId/" + teamId, {}, JSON.stringify({'messageName': username,'text': valuable}));
            }
            else {
            stompClient.send("/app/matchupId/" + matchupId, {}, JSON.stringify({'messageName': username,'text': valuable}));
            }
        },
        
        setCurrent: function (active) {
            currentRoom = active;
        },
    };
});

// (function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);throw new Error("Cannot find module '"+o+"'")}var f=n[o]={exports:{}};t[o][0].call(f.exports,function(e){var n=t[o][1][e];return s(n?n:e)},f,f.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
// var stompClient = null;

// let app = angular.module('sportsApp', ['ui.router']);

// app.factory("messageService", function () {
//     let active = 'matchRoom';

//     let rooms = {
//          matchRoom: ['oogabooga'],
//          teamRoom: [],      
//     };

//     return {

//         getCurrent: function() {
//             return rooms[active];
//         },
//     };
// });

// app.controller('roomController', function($scope, messageService){
//     $scope.messages = messageService.getCurrent();
//     $scope.setCurrent('matchRoom');
//     $scope.send();
//     console.log('working');
// })

// function disconnect() {
//     if (stompClient != null) {
//         stompClient.disconnect();
//     }
//     setConnected(false);
//     console.log("Disconnected");
// }

// function dropFunction() {
//     document.getElementById("myDropdown").classList.toggle("show");
// }


// function sendMessage(userName) {
//     stompClient.send("/app/teamId1", {}, JSON.stringify({'messageName': username,'text': $("#message").val()}));
//     }

// function showGreeting(message) {
//     $("#messagesBox").append(message);
// }

// $(function () {
//     $("form").on('submit', function (e) {
//         e.preventDefault();
//     });
//     $( "#send" ).click(function() { sendMessage(); });
// });

// window.addEventListener('load', function () {
    // function connect() {
    // let socket = new SockJS('/ws');
    // stompClient = Stomp.over(socket);
    // stompClient.connect({}, function (frame) {
    //     console.log('Connected: ' + frame);

    //     stompClient.subscribe('/topic/teamId1', function (message) {
    //         messages.team.push(JSON.parse(message.body));
    //     });

    //     stompClient.subscribe('/topic/matchupId1', function (message) {
    //         messages.match.push(JSON.parse(message.body));
    //     });
    // });
// }
    // connect();
// });