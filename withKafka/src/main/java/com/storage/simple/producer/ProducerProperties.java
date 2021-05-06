package com.storage.simple.producer;

import java.util.Properties;

public class ProducerProperties {

    public static Properties getProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all"); //Set acknowledgements for producer requests.
        props.put("retries", 0); // automatically retry. when fails
        props.put("batch.size", 16384); // buffer size
        props.put("linger.ms", 1); //Reduce the no of requests less than 0
        props.put("buffer.memory", 33554432); // the total amount of memory available to the producer
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
}
