package com.storage.simple.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;

public class SimpleConsumer {
    private static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");
    private static final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(ConsumerProperties.getProperties());

    public void run(String topicName){
        consumer.subscribe(Collections.singleton(topicName));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("***********************\ntopic: %s \noffset: %d\n key: %s\n value: %s\n time: %s\n***********************",
                        record.topic(), record.offset(), record.key(), record.value(), SDF.format(LocalDate.now()));
        }
    }
}