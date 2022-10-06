package ru.saubulprojects.sausocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import ru.saubulprojects.sausocial.config.SwaggerConfig;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfig.class)
public class SausocialApplication {

	public static void main(String[] args) {
		SpringApplication.run(SausocialApplication.class, args);
	}

}
