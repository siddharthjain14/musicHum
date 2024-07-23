package com.example.proxeee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ProxeeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxeeeApplication.class, args);
	}

}
