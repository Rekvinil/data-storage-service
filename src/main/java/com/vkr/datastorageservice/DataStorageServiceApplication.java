package com.vkr.datastorageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class DataStorageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataStorageServiceApplication.class, args);
	}

}
