package com.example.SWAPIConsumer;

import com.example.SWAPIConsumer.entity.Actor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SWAPIConsumerApplication {

	private static final Logger log = LoggerFactory.getLogger(SWAPIConsumerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SWAPIConsumerApplication.class, args);
	}

	@Bean
	public void run() throws Exception {
		String url = "https://swapi.dev/api";
		WebClient webClient = WebClient.builder().baseUrl(url).build();
		Flux<Actor> flux = webClient.get()
				.uri("/people/1")
				.retrieve()
				.bodyToFlux(Actor.class);

		log.info(flux.blockFirst().toString());
	}

}
