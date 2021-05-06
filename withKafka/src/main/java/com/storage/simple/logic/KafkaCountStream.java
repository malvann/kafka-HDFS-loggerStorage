package com.storage.simple.logic;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

class KafkaCountStream {
    public static void main(final String[] args){

        // Check arguments length value
        if (args.length != 2) {
            System.out.println("Enter topic name, appId");
            return;
        }

        String topicName = args[0];
        String appId = args[1];
        System.out.println("Count stream topic=" + topicName +", app=" + appId);

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, appId);
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 2000);
        config.put(StreamsConfig.STATE_DIR_CONFIG, "C:/kafka_2.11-1.1.0/state");

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream(topicName);
        // State store
        KTable<String, Long> wordCounts = textLines
                .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
                .groupBy((key, word) -> word)
                .count();

        // out to another topic
        KStream<String, String> stringKStream = wordCounts.toStream()
                .map((k, v) -> new KeyValue<>(appId + "." + k, v.toString()));
        stringKStream.to("out-topic", Produced.with(Serdes.String(), Serdes.String()));

        KafkaStreams streams = new KafkaStreams(builder.build(), config);

        // additional to complete the work
        final CountDownLatch latch = new CountDownLatch(1);
        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                System.out.println("Kafka Stream close");
                streams.close();
                latch.countDown();
            }
        });

        try {
            System.out.println("Kafka Stream start");
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.out.println("Kafka Stream exit");
        System.exit(0);
    }

}
