package com.theironyard.charlotte;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableOAuth2Sso
public class FootballTrashTalkerApplication {



	public static void main(String[] args) {
		SpringApplication.run(FootballTrashTalkerApplication.class, args);
	}

}
