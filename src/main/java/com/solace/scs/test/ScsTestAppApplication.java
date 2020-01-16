package com.solace.scs.test;

import com.solace.scs.example.Messaging;
import com.solace.scs.example.SensorReading;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@SpringBootApplication
//@EnableBinding
@ComponentScan("com.solace.scs")
@Configuration
public class ScsTestAppApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(ScsTestAppApplication.class);

	@Autowired
	private Messaging messaging;

	public static void main(String[] args) {
		SpringApplication.run(ScsTestAppApplication.class);
	}

	@Override
	public void run(String... args) throws Exception {
		runExample();
	}

	private void runExample() throws Exception {
		messaging.setCallback(this::receive);

		boolean run = true;

		while (run) {
			SensorReading sensorReading = emitSensorReading();
			messaging.send(sensorReading);
			Thread.sleep(900);
		}

	}

	public void receive(SensorReading sensorReading) {
		log.info("Got one! " + sensorReading);
	}

	// copied from scs-example:
	private static final Random random = new Random(System.currentTimeMillis());
	private static final int RANDOM_MULTIPLIER = 100;
//
	public SensorReading emitSensorReading() {
		double temperature = random.nextDouble() * RANDOM_MULTIPLIER;
		temperature = Math.floor(temperature);

		SensorReading reading = new SensorReading();
		reading.setSensorID("sensor1");
		reading.setTemperature(temperature);
		reading.setBaseUnit(SensorReading.BaseUnit.FAHRENHEIT);

		//log.info("Emitting: " + reading);

		return reading;
	}
//
//
//	// FROM THE LIBRARY
//	private final EmitterProcessor<Message<?>> processor = EmitterProcessor.create();
//
//	Consumer<SensorReading> callback;
//
//	@Bean
//	public Supplier<Flux<Message<?>>> supplier() {
//		log.info("sensorReadingSupplier called.");
//		return () -> processor;
//	}
	//@Bean
//	public Supplier<Message<?>> supplier1() {
//		log.info("sensorReadingSupplier1 called.");
//		return () -> buildMessage(emitSensorReading());
//	}

//	@Bean
//	public Consumer<SensorReading> consumer() {
//		log.info("sensorReadingConsumer called.");
//		return s -> callback.accept(s);
//	}
//
//	public void setCallback(Consumer<SensorReading> callback) {
//		this.callback = callback;
//	}
//
//	public void send(SensorReading sensorReading) {
//		Message<?> message = buildMessage(sensorReading);
//		log.info("Sending------------  " + sensorReading);
//		processor.onNext(message);
//	}
//
//	private Message<?> buildMessage(SensorReading sensorReading) {
//		String topic = "sensor-t";
//		//String topic = "sensor/" + topicIndex++;
//		Message<SensorReading> message = MessageBuilder
//				.withPayload(sensorReading)
//				.setHeader("spring.cloud.stream.sendto.destination", topic)
//				.build();
//		return message;
//	}


}
