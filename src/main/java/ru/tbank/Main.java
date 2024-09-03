package ru.tbank;

import ru.tbank.model.City;
import ru.tbank.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    private static List<File> getAllJsonFiles(File directory) {
        List<File> jsonFiles = new ArrayList<>();

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isFile()) {
                jsonFiles.add(file);
            }
        }

        return jsonFiles;
    }

    public static void main(String[] args) {
        Parser<City> parser = new Parser<>(
                City.class,
                ((log, model) -> {
                    log.debug("Produced POJO: {}", model.toString());
                    return model;
                })
        );
        File jsonFilesDirectory = new File("src/main/resources/json");

        for (File file : getAllJsonFiles(jsonFilesDirectory)) {
            try {
                parser.parse(file);
            } catch (IOException ignored) {

            }
        }
    }
}
