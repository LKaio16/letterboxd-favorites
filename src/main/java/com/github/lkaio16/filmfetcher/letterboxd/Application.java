package com.github.lkaio16.filmfetcher.letterboxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		FilmPicker filmPicker = new FilmPicker();
		filmPicker.getFavorites("lkaio16"); // insert your username
	}

}
