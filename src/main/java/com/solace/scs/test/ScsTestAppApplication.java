package com.solace.scs.test;

import com.solace.scs.example.SensorReading;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.solace.scs")
public class ScsTestAppApplication {

	public static void main(String[] args) {
		SensorReading sr = new SensorReading();
		SpringApplication.run(ScsTestAppApplication.class, args);
	}

}
