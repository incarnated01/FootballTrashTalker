package com.theironyard.charlotte.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theironyard.charlotte.entities.*;
import com.theironyard.charlotte.services.*;
import com.theironyard.charlotte.utilities.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.*;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    ScheduleRepository schedule;

    @Autowired
    ApplicationContext context;

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public String login(Model model, String userName, String password, String favTeam) throws Exception {

        // code to establish day of year values
        java.util.Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);

        // finds fav team
        TeamIdentifier teamIdentifier = teams.findFirstByName(favTeam);

        // finds fav team id
        int teamId = teamIdentifier.getId();
        String teamIdString = String.valueOf(teamId);

        // finds fav team abbreviation
        String teamAbreviation = teamIdentifier.getAbbreviation();

//         finds matchupId
        String matchupId = null;
        int gameDay = 0;
        List<Schedule> teamSchedule = new ArrayList<>();
        if (matchupId == null) {
            teamSchedule = schedule.findByHomeOrAway(teamAbreviation, teamAbreviation);
            for (int i = 0;i < teamSchedule.size();i++) {
                if (teamSchedule.get(i).getDayOfYear() >= dayOfYear) {
                    matchupId = teamSchedule.get(i).getId();
                    gameDay = teamSchedule.get(i).getDayOfYear();
                    break;
                }
            }
        }

        // finds current user, creates user if none exists
        User user = users.findFirstByUserName(userName);
        if (user == null) {
            user = new User(userName, PasswordStorage.createHash(password), teamId);
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(password, user.getPassword())) {
            throw new Exception("Wrong password");
        }

        model.addAttribute("matchupId", matchupId);
        model.addAttribute("teamId", teamIdString);
        model.addAttribute("username", user.getUsername());
        return "index";
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String home() {
        return "login";
    }

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String showChat() {
        return "index";
    }

    @MessageMapping("/teamId/{teamId}")
    @SendTo("/topic/teamId/{teamId}")
    public Message sendTeamMessage(ChatMessage message, @DestinationVariable String teamId) throws Exception {
        return new Message(message);
    }

    @MessageMapping("/matchupId/{matchupId}")
    @SendTo("/topic/matchupId/{matchupId}")
    public Message sendMatchupMessage(ChatMessage message, @DestinationVariable String matchupId) throws Exception {
        return new Message(message);
    }


    @PostConstruct
    public void init() throws IOException, ParseException {
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

        if (schedule.count() == 0) {
            String scheduleURI = "https://profootballapi.com/schedule?api_key=RbY0qXPLrFzKjwZHf28oBaet7JOpAixG&&year=2016&season_type=REG";

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

            for (int i = 0;i < currentSchedule.size();i++) {
                Schedule scheduleObject = currentSchedule.get(i);
                int scheduleDay = Integer.valueOf(scheduleObject.getDay());
                int scheduleMonth = Integer.valueOf(scheduleObject.getMonth());
                int scheduleYear = 2016;
                String dateString = String.format("%d/%d/%d", scheduleMonth, scheduleDay, scheduleYear);
                int scheduleDayOfYear = getDayOfYear(dateString);
                scheduleObject.setDayOfYear(scheduleDayOfYear);
                schedule.save(scheduleObject);
            }
        }
    }
    private static int getDayOfYear(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date date = sdf.parse(dateString);
        GregorianCalendar greg = new GregorianCalendar();
        greg.setTime(date);
        return greg.get(greg.DAY_OF_YEAR);
    }
}
