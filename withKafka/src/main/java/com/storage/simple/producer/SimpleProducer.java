package com.storage.simple.producer;

import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SimpleProducer {
    private static final KafkaProducer producer = new KafkaProducer(ProducerProperties.getProperties());

    @SneakyThrows
    public void run(String topicName) {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Enter topic:key:value, q - Exit");
            String input;
            while (!(input = br.readLine()).equals("q")) {
                String[] split = input.split(":");
                switch (split.length) {
                    case 1 -> producer.send(new ProducerRecord(topicName, split[0]));// strategy by round
                    case 2 -> producer.send(new ProducerRecord(topicName, split[0], split[1]));// strategy by hash
                    case 3 -> producer.send(new ProducerRecord(topicName, Integer.valueOf(split[2]), split[0], split[1]));// strategy by partition
                    default -> System.out.println("Enter key:value, q - Exit");
                }
            }
            producer.close();
            System.out.println("Exit!");
            System.exit(0);
        }
    }
}
