package com.formaciondbi.microservicios.app.alumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableEurekaClient//Habilita eureka
@EnableFeignClients
@SpringBootApplication
@EntityScan({"com.formaciondbi.microservicios.commons.alumnos.model.entity"})
public class MicroserviciosAlumnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosAlumnosApplication.class, args);
	}

}
