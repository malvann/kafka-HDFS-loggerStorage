package com.storage.simple;

import com.storage.simple.producer.SimpleProducer;
import org.apache.log4j.Logger;

public class AppProducer {
    public static final Logger LOGGER = Logger.getLogger(AppProducer.class);

    public static void main(String[] args) {
        if(args.length == 0){
            LOGGER.warn("Enter topic name");
            return;
        }
        new SimpleProducer().run(args[0]);
    }
}
