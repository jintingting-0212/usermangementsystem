package org.demo.edgeservice;

import org.apache.servicecomb.springboot.starter.provider.EnableServiceComb;
import org.apache.servicecomb.tracing.zipkin.EnableZipkinTracing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableServiceComb
@EnableZipkinTracing
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}