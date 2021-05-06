package com.storage.simple.consumerGroup;

import java.util.Properties;

public class ConsumerGroupProperties {
    public static Properties getProperties(String group) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", group);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put("value.deserializer", "org.apache.kafka.common.serializa-tion.StringDeserializer");
        return props;
    }
}
