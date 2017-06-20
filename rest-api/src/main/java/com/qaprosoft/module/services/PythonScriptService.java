package com.qaprosoft.module.services;

import org.apache.log4j.Logger;
import java.io.*;
import java.util.Properties;

/**
 * Created by anazarenko on 6/16/17.
 */
public class PythonScriptService {

    private static final Logger LOGGER = Logger.getLogger(PythonScriptService.class);

    public static String exeсutePythonScriptWithArguments(String model, String url) throws IOException {

        Properties properties = new Properties();
        properties.load(new FileInputStream("src/main/resources/property.properties"));
        String aiHome = properties.getProperty("AI_HOME");
        String recognizeScript = properties.getProperty("RECOGNIZE_SCRIPT");
        InputStream in = callPythonScript(aiHome + "/" +recognizeScript,model, url);
        String str = getStringFromInputstream(in);
        return str;
    }


    public static InputStream callPythonScript(String pathToScript,String model, String url){
        String[] cmd = {"python", pathToScript, "--model"+ model,"--url" + url};
        Process p = null;
        try {
            p = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            LOGGER.info(e);
        }
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            LOGGER.info(e);
        }

        return p.getInputStream();
    }


    public static String getStringFromInputstream(InputStream in){
        String str = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        try {
            while (br.ready())
                str+=br.readLine()+"\n";
        } catch (IOException e) {
            LOGGER.info(e);
        }

        return str;
    }

}
