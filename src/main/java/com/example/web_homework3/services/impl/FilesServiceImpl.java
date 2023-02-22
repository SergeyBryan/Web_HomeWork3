package com.example.web_homework3.services.impl;

import com.example.web_homework3.controllers.FileProcessingException;
import com.example.web_homework3.services.FilesService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service()
public class FilesServiceImpl implements FilesService {

    @Override
    public boolean saveToFile(String json, String dataFilePath, String dataFileName) {
        try {
            cleanDataFile(dataFilePath, dataFileName);
            Files.writeString(Path.of(dataFilePath, dataFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String readFromFile(String dataFilePath, String dataFileName) {
        Path path = Path.of(dataFilePath, dataFileName);
        if (Files.exists(path)) {
            try {
                return Files.readString(path);
            } catch (IOException e) {
                e.printStackTrace();
                throw new FileProcessingException("Не удалось прочитать файл");
            }
        } else {
            return "{ }";
        }

    }

    @Override
    public boolean cleanDataFile(String dataFilePath, String dataFileName) {
        try {
            Path path = Path.of(dataFilePath, dataFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
