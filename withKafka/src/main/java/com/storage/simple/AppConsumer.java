package com.storage.simple;

import com.storage.simple.consumer.SimpleConsumer;

public class AppConsumer {
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Enter topic name");
            return;
        }
        new SimpleConsumer().run(args[0]);
    }
}
