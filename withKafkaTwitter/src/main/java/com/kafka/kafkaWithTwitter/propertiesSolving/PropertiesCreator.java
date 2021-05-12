package com.kafka.kafkaWithTwitter.propertiesSolving;

import java.util.Properties;

public class PropertiesCreator {
    private final static String kafkaPropertiesFileName = "kafka.properties";
    
    public static Properties getKafkaProperties(){
        Properties properties = new Properties();
        new PropertyLoader(kafkaPropertiesFileName).getPropMap().forEach(properties::put);
        return properties;
    }
}
