package com.qaprosoft.module.services;

import org.apache.log4j.Logger;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by anazarenko on 6/18/17.
 */
public class ImageService
{
    private static final Logger LOGGER = Logger.getLogger(PythonScriptService.class);

    public static void saveImg(String path, InputStream inputStream)  {

        try {
            Files.copy(inputStream, Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
