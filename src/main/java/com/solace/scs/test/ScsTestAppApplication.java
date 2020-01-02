package com.solace.scs.test;

import com.solace.scs.example.FahrenheitTempSource;
import com.solace.scs.example.SensorReading;
import com.solace.scs.gen.CelsiusChannel;
import com.solace.scs.gen.FahrenheitChannel;
import com.solace.scs.gen.TempReading;
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

//	@Autowired
//	FahrenheitTempSource fahrenheitTempSource;

	@Autowired
	FahrenheitChannel fahrenheitChannel;

	@Autowired
	CelsiusChannel celsiusChannel;


	public static void main(String[] args) {
		SensorReading sr = new SensorReading();
		SpringApplication.run(ScsTestAppApplication.class, args);
	}

//	public void receive(SensorReading sensorReading) {
//		log.info("Got one! " + sensorReading);
//	}
	public void receiveF(TempReading tempReading) {
		double t = tempReading.getTemperature();
		log.info("Got F! " + tempReading.getBaseUnit() + " " + t);
		TempReading tr = new TempReading();
		tr.setTemperature((t - 32) * 5.0 / 9.0);
		tr.setBaseUnit(TempReading.BaseUnit.CELSIUS);
		celsiusChannel.send(tr);
	}

	public void receiveC(TempReading tempReading) {
		log.info("Got C! " + tempReading.getBaseUnit() +  " " + tempReading.getTemperature());
	}
	@Override
	public void run(String... args) throws Exception {
		fahrenheitChannel.setCallback(this::receiveF);
		celsiusChannel.setCallback(this::receiveC);

		for (int i = 0; i < 1; i++) {
			TempReading tr = new TempReading();
			tr.setBaseUnit(TempReading.BaseUnit.FAHRENHEIT);
			tr.setSensorId("sensor 0");
			tr.setTemperature( 212.0 );
			fahrenheitChannel.send(tr);
		}

		Thread.sleep(1000);
	}

	//	@Override
//	public void run(String... args) throws Exception {
//		fahrenheitTempSource.setCallback(this::receive);
//
//		while (true) {
//			SensorReading sensorReading = fahrenheitTempSource.emitSensorReading();
//			log.info("Sending " + sensorReading);
//			fahrenheitTempSource.sendIt(sensorReading);
//			Thread.sleep(1000);
//		}
//	}
}
