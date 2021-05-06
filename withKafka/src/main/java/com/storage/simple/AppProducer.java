package com.storage.simple;

import com.storage.simple.producer.SimpleProducer;

public class AppProducer {
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Enter topic name");
            return;
        }
        new SimpleProducer().run(args[0]);
    }
}
