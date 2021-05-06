package com.storage.simple.consumerGroup;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;

public class ConsumerGroup {
    private KafkaConsumer<String, String> consumer;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");

    public ConsumerGroup(String group) {
        consumer = new KafkaConsumer(ConsumerGroupProperties.getProperties(group));
    }

    public void run(String topic) {
        consumer.subscribe(Collections.singletonList(topic));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("***********************\ntopic: %s \noffset: %d\n key: %s\n value: %s\n time: %s\n***********************",
                        record.topic(), record.offset(), record.key(), record.value(), SDF.format(LocalDate.now()));
        }
    }
}
