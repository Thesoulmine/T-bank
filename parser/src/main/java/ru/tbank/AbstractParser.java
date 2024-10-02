package ru.tbank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public abstract class AbstractParser<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();
    private final Class<T> parsingClass;

    public final void parse(File file) throws IOException {
        log.info("Start parsing");
        T model = fromJSON(file);
        model = handle(model);
        String xmlData = toXML(model);
        saveFile(xmlData, file.getName().replaceFirst("[.][^.]+$", ".xml"));
        log.info("End parsing");
    }

    protected abstract T handle(T model);

    private T fromJSON(File file) throws IOException {
        log.info("Start converting from JSON to POJO file: {}", file.getName());
        try {
            return objectMapper.readValue(file, parsingClass);
        } catch (IOException e) {
            log.error("Converting from JSON to POJO exception: {}", e.getMessage());
            throw e;
        }
    }

    private String toXML(T model) throws IOException {
        log.info("Start converting from POJO to XML model: {}", model.toString());
        try {
            return xmlMapper.writeValueAsString(model);
        } catch (IOException e) {
            log.error("Converting from POJO to XML exception: {}", e.getMessage());
            throw e;
        }
    }

    private void saveFile(String data, String fileName) throws IOException {
        log.info("Start save file");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("parser/src/main/resources/xml", fileName)))) {
            writer.write(data);
            log.info("File was successfully saved");
        } catch (IOException e) {
            log.error("Save file exception: {}", e.getMessage());
            throw e;
        }
    }
}
