package com.theironyard.charlotte.controllers;

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
import java.util.Scanner;

// api key: RbY0qXPLrFzKjwZHf28oBaet7JOpAixG

/**
 * Created by mfahrner on 10/3/16.
 */
@Controller
public class FootballTrashTalkerController {

    @Autowired
    UserRepository users;

    @Autowired
    TeamRepository teams;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(Model model, HttpSession session, HttpServletResponse response, String userName, String password, String favTeam) throws Exception {
        TeamIdentifier teamIdentifier = teams.findFirstByName(favTeam);
        int teamId = teamIdentifier.getId();
        User user = users.findFirstByUserName(userName);
        if (user == null) {
            user = new User(userName, PasswordStorage.createHash(password), teamId);
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("Wrong password");
        }
        session.setAttribute("username", userName);
        return "index";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home(Model model) {
        return "home";
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String showChat(Model model) {
        return "index";
    }

    @RequestMapping(path = "/index", method = RequestMethod.POST)
    public String getJSON(Model model, String gameId) {
        final String uri = "https://profootballapi.com/game/?api_key=RbY0qXPLrFzKjwZHf28oBaet7JOpAixG&game_id=2016100207";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<String>("", headers);

        String foo = restTemplate.postForObject(uri, request, String.class);

        ObjectMapper mapper = new ObjectMapper();

        Game fooGame = null;
        try {
            fooGame = mapper.readValue(foo, Game.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int blah = Integer.valueOf(gameId);
        return "index";
    }

    @MessageMapping("/teamId1")
    @SendTo("/topic/teamId1")
    public Message greeting(Message message) throws Exception {
        Message m = new Message();
        m.setText(message.getText());
        return m;
    }

    @MessageMapping("/matchupId1")
    @SendTo("/topic/matchupId1")
    public Message testaroo(Message message) throws Exception {
        Message m = new Message();
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

                TeamIdentifier teamIdentifier = new TeamIdentifier(line);

                teams.save(teamIdentifier);
            }
        }
    }
}
