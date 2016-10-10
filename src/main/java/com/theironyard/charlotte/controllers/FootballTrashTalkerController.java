package com.theironyard.charlotte.controllers;

import com.theironyard.charlotte.entities.Game;
import com.theironyard.charlotte.entities.Message;
import com.theironyard.charlotte.entities.User;
import com.theironyard.charlotte.services.UserRepository;
import com.theironyard.charlotte.utilities.PasswordStorage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// api key: RbY0qXPLrFzKjwZHf28oBaet7JOpAixG

/**
 * Created by mfahrner on 10/3/16.
 */
@Controller
public class FootballTrashTalkerController {

    @Autowired
    UserRepository users;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(Model model, HttpSession session, HttpServletResponse response, String userName, String password, String favTeam) throws Exception {
        User user = users.findFirstByUserName(userName);
        if (user == null) {
            user = new User(userName, PasswordStorage.createHash(password), favTeam);
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
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
//        String foo = "";
//        HttpEntity<String> request = new HttpEntity<>(foo);
//        foo = restTemplate.postForObject(uri, request, String.class);
//        HttpEntity<JSONObject> request = new HttpEntity<>(new JSONObject());
//        JSONObject testGame = restTemplate.postForObject(uri, request, JSONObject.class);
        Game testGame = new Game();
        HttpEntity<Game> request = new HttpEntity<>(testGame);
        testGame = restTemplate.postForObject(uri, request, Game.class);
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
        
    }
}
