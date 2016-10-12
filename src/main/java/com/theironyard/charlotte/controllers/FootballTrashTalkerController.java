package com.theironyard.charlotte.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.charlotte.entities.*;
import com.theironyard.charlotte.services.TeamRepository;
import com.theironyard.charlotte.services.UserRepository;
import com.theironyard.charlotte.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

// api key: RbY0qXPLrFzKjwZHf28oBaet7JOpAixG

// test game data api "https://profootballapi.com/game/?api_key=RbY0qXPLrFzKjwZHf28oBaet7JOpAixG&game_id=2016100207"

/**
 * Created by mfahrner on 10/3/16.
 */
@Controller
public class FootballTrashTalkerController {

    @Autowired
    UserRepository users;

    @Autowired
    TeamRepository teams;

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public String login(Model model, HttpSession session, HttpServletResponse response, String userName, String password, String favTeam, Principal principal) throws Exception {

        // code to establish date values
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        String month = String.valueOf((cal.get(Calendar.MONTH)) + 1);
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        String year = String.valueOf(cal.get(Calendar.YEAR));

        // code to create map of variables to inject into api call
        Map<String,String> vars = new HashMap<>();
        vars.put("year", year);
        vars.put("month", month);
        vars.put("day", day);

        // code for parsing schedule
        String scheduleURI = "https://profootballapi.com/schedule?api_key=RbY0qXPLrFzKjwZHf28oBaet7JOpAixG&&year=2016&month=10&day=9";
//        String uri = "https://profootballapi.com/schedule?api_key=RbY0qXPLrFzKjwZHf28oBaet7JOpAixG&&year={year}&month={month}&day={day}";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<String>("", headers);

        String scheduleString = restTemplate.postForObject(scheduleURI, request, String.class);

        ObjectMapper mapper = new ObjectMapper();

        List<Schedule> currentSchedule = null;
        try {
            currentSchedule = mapper.readValue(scheduleString, new TypeReference<List<Schedule>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }

        // finds fav team
        TeamIdentifier teamIdentifier = teams.findFirstByName(favTeam);

        // finds fav team id
        int teamId = teamIdentifier.getId();

        // finds fav team abbreviation
        String teamAbreviation = teamIdentifier.getAbbreviation();

        // finds matchupId
        String matchupId = null;
        if (matchupId == null) {
            for (int i = 0; i < currentSchedule.size();i++) {
                if (currentSchedule.get(i).getHome().equals(teamAbreviation)) {
                    matchupId = currentSchedule.get(i).getId();
                } else if (currentSchedule.get(i).getAway().equals(teamAbreviation)) {
                    matchupId = currentSchedule.get(i).getId();
                }
            }
        }

        vars.put("game_id", matchupId);

        // code for parsing game data
//        String gameURI = "https://profootballapi.com/game/?api_key=RbY0qXPLrFzKjwZHf28oBaet7JOpAixG&game_id=2016100207";
        String gameURI = "https://profootballapi.com/game/?api_key=RbY0qXPLrFzKjwZHf28oBaet7JOpAixG&game_id={game_id}";

        String gameString = restTemplate.postForObject(gameURI, request, String.class, vars);

        Game currentGame = null;
        try {
            currentGame = mapper.readValue(gameString, Game.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // data to pass into mustache
        int homeScore = currentGame.getHome_score();
        int awayScore = currentGame.getAway_score();

        String homeTeam = currentGame.getHome().getTeam();
        String awayTeam = currentGame.getAway().getTeam();

        // need to distill stats into usable data: have in a collection but need to access individual values
        // I'm thinking stream but need help figuring this out
//        Collection<PassStat> passingSomething = currentGame.getHome().getStats().getPassing().values();

        // finds current user, creates user if none exists
        User user = users.findFirstByUserName(userName);
        if (user == null) {
            user = new User(userName, PasswordStorage.createHash(password), teamId);
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("Wrong password");
        }

        session.setAttribute("username", userName);
        model.addAttribute("homeScore", homeScore);
        model.addAttribute("awayScore", awayScore);
        model.addAttribute("homeTeam", homeTeam);
        model.addAttribute("awayTeam", awayTeam);
        return "index";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "login";
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String showChat(Model model) {
        return "index";
    }

    // left this here incase a need a guide while refactoring
//    @RequestMapping(path = "/index", method = RequestMethod.POST)
//    public String getJSON(Model model, String gameId) {
//
//        // code for parsing game data
//        String uri = "https://profootballapi.com/game/?api_key=RbY0qXPLrFzKjwZHf28oBaet7JOpAixG&game_id=2016100207";
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> request = new HttpEntity<String>("", headers);
//
//        String gameString = restTemplate.postForObject(uri, request, String.class);
//
//        ObjectMapper mapper = new ObjectMapper();
//
//        Game currentGame = null;
//        try {
//            currentGame = mapper.readValue(gameString, Game.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return "index";
//    }

    @MessageMapping("/teamId1")
    @SendTo("/topic/teamId1")
    public Message greeting(Message message) throws Exception {
        Message m = new Message();
        m.setMessageName(message.getMessageName());
        m.setText(message.getText());
        return m;
    }

    @MessageMapping("/matchupId1")
    @SendTo("/topic/matchupId1")
    public Message testaroo(Message message) throws Exception {
        Message m = new Message();
        m.setMessageName(message.getMessageName());
        m.setText(message.getText());
        return m;
    }

    @PostConstruct
    public void init() throws IOException {
        if (teams.count() == 0) {
            File f = new File("teams.csv");
            Scanner fileScanner = new Scanner(f);

            while (fileScanner.hasNext()) {

                String line = fileScanner.nextLine();
                String[] columns = line.split(",");

                TeamIdentifier teamIdentifier = new TeamIdentifier(columns[0], columns[1]);

                teams.save(teamIdentifier);
            }
        }
    }
}
