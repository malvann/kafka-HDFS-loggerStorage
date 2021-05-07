package com.kafka.kafkaWithTwitter.propertiesSolving;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class PropertyLoader {
    private final Map<String, String> propMap;

    @SneakyThrows
    public PropertyLoader(String fileName){
        String path = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(fileName)).getPath();
        try(Stream<String> lines = new BufferedReader(new FileReader(path)).lines()){
            propMap = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
        }
    }

    public Map<String, String> getPropMap() {
        return propMap;
    }
}
