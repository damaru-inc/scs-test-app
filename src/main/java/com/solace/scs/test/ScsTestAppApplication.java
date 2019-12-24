package com.solace.scs.test;

import com.solace.scs.example.FahrenheitTempSource;
import com.solace.scs.example.SensorReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.solace.scs")
public class ScsTestAppApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ScsTestAppApplication.class);

	@Autowired
	FahrenheitTempSource fahrenheitTempSource;

	public static void main(String[] args) {
		SensorReading sr = new SensorReading();
		SpringApplication.run(ScsTestAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		while (true) {
			SensorReading sensorReading = fahrenheitTempSource.emitSensorReading();
			log.info("Sending " + sensorReading);
			fahrenheitTempSource.sendIt(sensorReading);
			Thread.sleep(1000);
		}
	}
}
