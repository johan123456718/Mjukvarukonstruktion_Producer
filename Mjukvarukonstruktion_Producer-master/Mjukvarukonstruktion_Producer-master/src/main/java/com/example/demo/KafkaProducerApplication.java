package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaProducerApplication {
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		String ip ="";
		String port ="";

		System.out.print("Enter ip: ");
		ip = scan.nextLine();

		System.out.print("Enter port: ");
		port = scan.nextLine();

		Producer.InitProducer(ip, port);
		SpringApplication.run(KafkaProducerApplication.class, args);
	}
}
