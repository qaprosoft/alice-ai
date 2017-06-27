package com.qaprosoft.module.services;

import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by anazarenko on 6/22/17.
 */

public class BasicService {
    protected final static String PATH_TO_IMG_FILE;
    protected final static String AI_HOME;
    protected final static String RECOGNIZE_SCRIPT;

    private static final Logger LOGGER = Logger.getLogger(BasicService.class);

    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/property.properties"));
        } catch (IOException e) {
            LOGGER.info(e);
        }

        AI_HOME= properties.getProperty("AI_HOME");
        RECOGNIZE_SCRIPT = properties.getProperty("RECOGNIZE_SCRIPT");
        PATH_TO_IMG_FILE=properties.getProperty("AI_HOME")+"/"+properties.getProperty("INPUT_IMAGE_FILENAME");
    }

}
