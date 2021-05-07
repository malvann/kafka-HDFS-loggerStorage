package com.storage.simple;

import com.storage.simple.consumerGroup.ConsumerGroup;
import org.apache.log4j.Logger;

public class AppConsumerGroup {
    private static final Logger LOGGER = Logger.getLogger(AppConsumerGroup.class);

    public static void main(String[] args) {
        if(args.length < 2){
            LOGGER.warn("Usage: consumer <topic> <group_name>");
            return;
        }
        new ConsumerGroup(args[1]).run(args[0]);
    }
}
