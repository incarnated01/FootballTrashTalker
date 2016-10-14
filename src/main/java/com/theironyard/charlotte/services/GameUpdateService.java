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
    private SimpMessagingTemplate template;

    public GameUpdateService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public Future<Game> findGame() throws InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<String>("", headers);

        ObjectMapper mapper = new ObjectMapper();

        Game currentGame = null;

        List<Schedule> currentDaysGames = currentGame.getCurrentGames();

        Map<String,String> vars = new HashMap<>();

        for (int i = 0; i < currentDaysGames.size();i++) {
            vars.put("game_id", currentDaysGames.get(i).getId());

//        String gameURI = "https://profootballapi.com/game/?api_key=RbY0qXPLrFzKjwZHf28oBaet7JOpAixG&game_id=2016100207";
            String gameURI = "https://profootballapi.com/game/?api_key=RbY0qXPLrFzKjwZHf28oBaet7JOpAixG&game_id={game_id}";

            String gameString = restTemplate.postForObject(gameURI, request, String.class, vars);

            try {
                currentGame = mapper.readValue(gameString, Game.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Thread.sleep(1000);
        return new AsyncResult<>(currentGame);
    }

    @Scheduled(fixedRate = 10)
    public void botMessage() {
        Message m = new Message("autobot", "test");
        this.template.convertAndSend("topic/teamId/{teamId}", m);
    }
}
