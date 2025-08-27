package com.bookspot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookspotApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookspotApplication.class, args);
	}

}
