package com.skillforge.skillforge_monolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SkillforgeMonolithApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillforgeMonolithApplication.class, args);
	}

}
