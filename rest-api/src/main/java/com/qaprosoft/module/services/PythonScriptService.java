package com.qaprosoft.module.services;

import org.apache.log4j.Logger;
import java.io.*;


/**
 * Created by anazarenko on 6/16/17.
 */
public class PythonScriptService extends BasicService{

    private static final Logger LOGGER = Logger.getLogger(PythonScriptService.class);

    public static void exeсutePythonScriptWithArguments(String model, String path, String type) throws IOException {
         callPythonScript(AI_HOME + "/" +RECOGNIZE_SCRIPT,model, path, type);

    }


    public static void callPythonScript(String pathToScript,String model, String path, String type){
        String[] cmd = {"/usr/bin/python", pathToScript, "--model", model,"--folder", path, "--output", type};
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

    }


}
