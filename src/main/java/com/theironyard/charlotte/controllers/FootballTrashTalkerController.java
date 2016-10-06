package com.theironyard.charlotte.controllers;

import com.theironyard.charlotte.entities.Message;
import com.theironyard.charlotte.entities.User;
import com.theironyard.charlotte.services.UserRepository;
import com.theironyard.charlotte.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
}
