package com.theironyard.charlotte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;

@SpringBootApplication
public class FootballTrashTalkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballTrashTalkerApplication.class, args);
	}
}
