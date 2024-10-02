package ru.tbank;

import ru.tbank.model.City;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {

    private static List<File> getAllJsonFiles(File directory) {
        return Arrays.stream(Objects.requireNonNull(directory.listFiles()))
                .filter(File::isFile)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Parser<City> parser = new Parser<>(
                City.class,
                ((log, model) -> {
                    log.debug("Produced POJO: {}", model.toString());
                    return model;
                })
        );

        File jsonFilesDirectory = new File("parser/src/main/resources/json");
        System.out.println(jsonFilesDirectory.getAbsolutePath());

        for (File file : getAllJsonFiles(jsonFilesDirectory)) {
            try {
                parser.parse(file);
            } catch (IOException ignored) {

            }
        }
    }
}
