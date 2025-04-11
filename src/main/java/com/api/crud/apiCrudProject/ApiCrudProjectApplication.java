package com.api.crud.apiCrudProject;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ApiCrudProjectApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(ApiCrudProjectApplication.class, args);

		Environment env = context.getEnvironment();
		System.out.println("Active profiles: " + Arrays.toString(env.getActiveProfiles()));
	}

}
