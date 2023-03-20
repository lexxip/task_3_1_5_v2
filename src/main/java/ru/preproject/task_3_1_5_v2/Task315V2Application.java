package ru.preproject.task_3_1_5_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Task315V2Application {

	public static void main(String[] args) {
		SpringApplication.run(Task315V2Application.class, args);
	}

}
