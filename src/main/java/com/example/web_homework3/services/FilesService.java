package com.example.web_homework3.services;

public interface FilesService {


    boolean saveToFile(String json, String dataFilePath, String dataFileName);

    String readFromFile(String dataFilePath, String dataFileName);

    boolean cleanDataFile(String dataFilePath, String dataFileName);

}
