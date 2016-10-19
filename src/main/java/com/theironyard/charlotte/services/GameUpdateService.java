package com.theironyard.charlotte.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.charlotte.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collection;
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
    TeamRepository teams;

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

        // generates a list of current days schedule
        List<Schedule> currentDaysGames = game.getGamesToUpdate();

        Map<String,String> vars = new HashMap<>();

        Game updatedGame = null;

        // for loop to contact api for every game in the game update list
        for (int i = 0; i < currentDaysGames.size();i++) {
            vars.put("game_id", currentDaysGames.get(i).getId());

            // contacts api
            String gameURI = "https://profootballapi.com/game/?api_key=RbY0qXPLrFzKjwZHf28oBaet7JOpAixG&game_id={game_id}";

            // saves response as a string
            String gameResponseString = restTemplate.postForObject(gameURI, request, String.class, vars);

            // uses object mapper to parse response string into various classes
            try {
                updatedGame = mapper.readValue(gameResponseString, Game.class);

                // find home team Id
                String homeAbrv = updatedGame.getHome().getTeam();
                TeamIdentifier homeTeamAbrv = teams.findByAbbreviation(homeAbrv);
                int homeTeamId = homeTeamAbrv.getId();
                String homeStringTeamId = String.valueOf(homeTeamId);

                // find away team Id
                String awayAbrv = updatedGame.getAway().getTeam();
                TeamIdentifier awayTeamAbrv = teams.findByAbbreviation(awayAbrv);
                int awayTeamId = awayTeamAbrv.getId();
                String awayStringTeamId = String.valueOf(awayTeamId);

                // find matchupId
                String matchupId = updatedGame.getNfl_id();

                // getting score for gameUpdate
                int homeScore = updatedGame.getHome_score();
                int awayScore = updatedGame.getAway_score();

                // sending score message to all three appropriate subscribed endpoints
                matchupScoreMessage(homeScore, awayScore, matchupId);
                teamScoreMessage(homeScore, awayScore, homeStringTeamId);
                teamScoreMessage(homeScore, awayScore,awayStringTeamId);

                // getting home stats for statUpdate for future feature
                Collection<PassStat> homePassStats = updatedGame.getHome().getStats().getPassing().values();
                Collection<RushStat> homeRushStats = updatedGame.getHome().getStats().getRushing().values();
                Collection<RecStat> homeRecStats = updatedGame.getHome().getStats().getReceiving().values();

                // getting away stats for statUpdate for future feature
                Collection<PassStat> awayPassStats = updatedGame.getAway().getStats().getPassing().values();
                Collection<RushStat> awayRushStats = updatedGame.getAway().getStats().getRushing().values();
                Collection<RecStat> awayRecStats = updatedGame.getAway().getStats().getReceiving().values();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new AsyncResult<>(updatedGame);
    }

    public void matchupScoreMessage(int homeScore, int awayScore, String matchupId) {
        UpdateMessage scoreMessage = new UpdateMessage(homeScore, awayScore);
        Message message = new Message(scoreMessage);
        this.template.convertAndSend("/topic/matchupId/" + matchupId, message);
    }


    public void teamScoreMessage(int homeScore, int awayScore, String teamId) {
        UpdateMessage scoreMessage = new UpdateMessage(homeScore, awayScore);
        Message message = new Message(scoreMessage);
        this.template.convertAndSend("/topic/teamId/" + teamId, message);
    }
}
