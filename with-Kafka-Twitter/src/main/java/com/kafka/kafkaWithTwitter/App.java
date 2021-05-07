package com.kafka.kafkaWithTwitter;

import com.kafka.kafkaWithTwitter.produsser.KafkaTwitterProducer;
import org.apache.log4j.Logger;

public class App {
    public static final Logger LOGGER = Logger.getLogger(App.class);

    public static void main(String[] args) {
        if(args.length < 5){
            LOGGER.warn("Usage: App " +
                    "<twitter-consumer-key> " +
                    "<twitter-consumer-secret> " +
                    "<twitter-access-token> " +
                    "<twitter-access-token-secret> " +
                    "<topic-name> " +
                    "<twitter-search-keywords>");
            return;
        }
        new KafkaTwitterProducer().run(args);
    }
}
