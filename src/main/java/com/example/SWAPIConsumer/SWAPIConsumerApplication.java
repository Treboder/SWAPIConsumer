package com.example.SWAPIConsumer;

import com.example.SWAPIConsumer.entity.Character;
import com.example.SWAPIConsumer.entity.CharacterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class SWAPIConsumerApplication {

	private static final Logger log = LoggerFactory.getLogger(SWAPIConsumerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SWAPIConsumerApplication.class, args);
	}

	@Bean
	public void getLukeSkywalkerMono() throws Exception {
		String url = "https://swapi.dev/api";
		WebClient webClient = WebClient.builder().baseUrl(url).build();
		Mono<Character> mono = webClient.get()
				.uri("/people/1")
				.retrieve()
				.bodyToMono(Character.class);

		log.info(mono.block().toString());
	}

	@Bean
	public void getLukeSkywalkerFlux() throws Exception {
		String url = "https://swapi.dev/api";
		WebClient webClient = WebClient.builder().baseUrl(url).build();
		Flux<Character> flux = webClient.get()
				.uri("/people/1")
				.retrieve()
				.bodyToFlux(Character.class);

		log.info(flux.blockFirst().toString());
	}

	@Bean
	public void getAllPeopleFromFirstPage() throws Exception {
		String url = "https://swapi.dev/api";

		Flux<CharacterList> flux = WebClient
				.create(url + "/people/")
				.get()
				.retrieve()
				.bodyToFlux(CharacterList.class);

		CharacterList characterList = flux.blockFirst();
		Iterator iter = characterList.getResults().iterator();
		while(iter.hasNext())
			log.info(iter.next().toString());
	}

}
