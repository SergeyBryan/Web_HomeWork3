package com.example.web_homework3.services;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {


    boolean saveToFile(String json, String dataFilePath, String dataFileName);

    String readFromFile(String dataFilePath, String dataFileName);

    boolean cleanDataFile(String dataFilePath, String dataFileName);

    File getDataFile(String dataFilePath, String dataFileName);


    Path createTempFile(String dataFilePath, String suffix);
}
