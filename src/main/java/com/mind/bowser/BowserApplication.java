package com.mind.bowser;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.stripe.Stripe;

import lombok.Value;

@SpringBootApplication
public class BowserApplication {
	
	/*
	 * @Value("${stripe.keys.public}") private String stripeKeyPublic;
	 * 
	 * @PostConstruct public void setup() { Stripe.apiKey = stripeKeyPublic; }
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(BowserApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("/**");
			}
		};
		
	}

}
