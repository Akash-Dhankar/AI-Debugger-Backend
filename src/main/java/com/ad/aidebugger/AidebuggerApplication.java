package com.ad.aidebugger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class AidebuggerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AidebuggerApplication.class, args);
	}
}

