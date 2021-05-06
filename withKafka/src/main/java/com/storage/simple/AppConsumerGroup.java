package com.storage.simple;

import com.storage.simple.consumerGroup.ConsumerGroup;

public class AppConsumerGroup {
    public static void main(String[] args) {
        if(args.length < 2){
            System.out.println("Usage: consumer <topic> <group_name>");
            return;
        }
        new ConsumerGroup(args[1]).run(args[0]);
    }
}
