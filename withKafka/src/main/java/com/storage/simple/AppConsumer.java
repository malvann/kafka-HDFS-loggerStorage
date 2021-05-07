package com.storage.simple;

import com.storage.simple.consumer.SimpleConsumer;
import org.apache.log4j.Logger;

public class AppConsumer {
    private static final Logger LOGGER = Logger.getLogger(AppConsumer.class);

    public static void main(String[] args) {
        if(args.length == 0){
            LOGGER.warn("Enter topic name");
            return;
        }
        new SimpleConsumer().run(args[0]);
    }
}
