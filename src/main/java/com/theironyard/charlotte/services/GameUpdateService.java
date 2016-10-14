package com.theironyard.charlotte.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.charlotte.entities.Game;
import com.theironyard.charlotte.entities.Message;
import com.theironyard.charlotte.entities.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by mfahrner on 10/12/16.
 */
@Service
public class GameUpdateService {
    private final RestTemplate restTemplate;

    @Autowired
    Game game;

    @Autowired
    private SimpMessagingTemplate template;

    public GameUpdateService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public Future<Game> findGame() throws InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>("", headers);

        ObjectMapper mapper = new ObjectMapper();

        List<Schedule> currentDaysGames = game.getCurrentGames();

        Map<String,String> vars = new HashMap<>();

        Game thisGame = null;

        for (int i = 0; i < currentDaysGames.size();i++) {
            vars.put("game_id", currentDaysGames.get(i).getId());

//        String gameURI = "https://profootballapi.com/game/?api_key=RbY0qXPLrFzKjwZHf28oBaet7JOpAixG&game_id=2016100207";
            String gameURI = "https://profootballapi.com/game/?api_key=RbY0qXPLrFzKjwZHf28oBaet7JOpAixG&game_id={game_id}";

            String gameString = restTemplate.postForObject(gameURI, request, String.class, vars);

            try {
                thisGame = mapper.readValue(gameString, Game.class);
                // send message to people. based off game.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new AsyncResult<>(thisGame);
    }
//
//    @Scheduled(fixedRate = 100000)
//    public void botMessage() {
//        Message m = new Message("autobot", "test");
//        this.template.convertAndSend("/topic/teamId/32", m);
//    }
}
