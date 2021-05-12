package com.kafka.kafkaWithTwitter.produsser;

import com.kafka.kafkaWithTwitter.listener.KafkaStatusListener;
import com.kafka.kafkaWithTwitter.propertiesSolving.PropertiesCreator;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class KafkaTwitterProducer {
    private static final Logger LOGGER = Logger.getLogger(KafkaTwitterProducer.class);

    @SneakyThrows
    public void run(String[] args) {
        String consumerKey = args[0];
        String consumerSecret = args[1];
        String accessToken = args[2];
        String accessTokenSecret = args[3];
        String topicName = args[4];
        String[] arguments = args.clone();
        String[] keyWords = Arrays.copyOfRange(arguments, 5, arguments.length);

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        twitterStream.addListener(new KafkaStatusListener());

        FilterQuery query = new FilterQuery().track(keyWords);
        twitterStream.filter(query);
        Thread.sleep(5000);

        try (Producer<String, String> producer = new KafkaProducer<>(PropertiesCreator.getKafkaProperties())){
            LinkedBlockingQueue<Status> queue = new LinkedBlockingQueue<>(1000);
            AtomicInteger counter = new AtomicInteger();
            for (int i = 0; i<10; i++){
                Status ret = queue.poll();
                if (ret == null) {
                    Thread.sleep(100);
                    continue;
                }
                Arrays.stream(ret.getHashtagEntities()).forEach(hashtagEntity -> {
                    LOGGER.info("Hashtag: " + hashtagEntity.getText());
                    producer.send(new ProducerRecord<>(topicName, Integer.toString(counter.getAndIncrement()), hashtagEntity.getText()));
                });
            }
        }
        Thread.sleep(5000);
        twitterStream.shutdown();
    }
}
