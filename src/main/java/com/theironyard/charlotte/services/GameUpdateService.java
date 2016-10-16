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
import org.springframework.scheduling.annotation.Scheduled;
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

                // home team Id
                String homeAbrv = thisGame.getHome().getTeam();
                TeamIdentifier homeTeamAbrv = teams.findByAbbreviation(homeAbrv);
                int homeTeamId = homeTeamAbrv.getId();
                String homeStringTeamId = String.valueOf(homeTeamId);

                // away team Id
                String awayAbrv = thisGame.getAway().getTeam();
                TeamIdentifier awayTeamAbrv = teams.findByAbbreviation(awayAbrv);
                int awayTeamId = awayTeamAbrv.getId();
                String awayStringTeamId = String.valueOf(awayTeamId);

                // matchupId
                String matchupId = thisGame.getNfl_id();

                // getting score for gameUpdate
                int homeScore = thisGame.getHome_score();
                int awayScore = thisGame.getAway_score();

                // getting home stats for gameUpdate
                Collection<PassStat> homePassStats = thisGame.getHome().getStats().getPassing().values();
                Collection<RushStat> homeRushStats = thisGame.getHome().getStats().getRushing().values();
                Collection<RecStat> homeRecStats = thisGame.getHome().getStats().getReceiving().values();

                // getting away stats for gameUpdate
                Collection<PassStat> awayPassStats = thisGame.getAway().getStats().getPassing().values();
                Collection<RushStat> awayRushStats = thisGame.getAway().getStats().getRushing().values();
                Collection<RecStat> awayRecStats = thisGame.getAway().getStats().getReceiving().values();

                Play currentPlay = null;

                matchupUpdateMessage(homeScore, awayScore, homePassStats, homeRushStats, homeRecStats, awayPassStats,
                        awayRushStats, awayRecStats, matchupId);

                teamUpdateMessage(homeScore, awayScore, homePassStats, homeRushStats, homeRecStats, awayPassStats,
                        awayRushStats, awayRecStats, homeStringTeamId);

                teamUpdateMessage(homeScore, awayScore, homePassStats, homeRushStats, homeRecStats, awayPassStats,
                        awayRushStats, awayRecStats, awayStringTeamId);

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("this id works" + thisGame.getNfl_id());

        }

        return new AsyncResult<>(thisGame);
    }
//
//    @Scheduled(fixedRate = 100000)
//    public void botMessage() {
//        Message m = new Message("autobot", "test");
//        this.template.convertAndSend("/topic/teamId/32", m);
//    }

    public void matchupUpdateMessage(int homeScore, int awayScore,
                                     Collection<PassStat> homePassStats, Collection<RushStat> homeRushStats,
                                     Collection<RecStat> homeRecStats, Collection<PassStat> awayPassStats,
                                     Collection<RushStat> awayRushStats, Collection<RecStat> awayRecStats,
                                     String matchupId) {
        UpdateMessage m = new UpdateMessage("UpdateBot", "update", homeScore, awayScore, homePassStats, homeRushStats,
                homeRecStats, awayPassStats, awayRushStats, awayRecStats);
                this.template.convertAndSend("/topic/matchupId/" + matchupId, m);


    }

    public void teamUpdateMessage(int homeScore, int awayScore,
                                  Collection<PassStat> homePassStats, Collection<RushStat> homeRushStats,
                                  Collection<RecStat> homeRecStats, Collection<PassStat> awayPassStats,
                                  Collection<RushStat> awayRushStats, Collection<RecStat> awayRecStats,
                                  String teamId) {
        UpdateMessage m = new UpdateMessage("UpdateBot", "update", homeScore, awayScore, homePassStats, homeRushStats,
                homeRecStats, awayPassStats, awayRushStats, awayRecStats);
        this.template.convertAndSend("/topic/teamId/" + teamId, m);
    }

    public void matchupPlayMessage() {

    }

    public void teamPlayMessage() {

    }
}
