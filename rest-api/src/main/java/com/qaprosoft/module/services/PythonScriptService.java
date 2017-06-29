package com.qaprosoft.module.services;

import org.apache.log4j.Logger;
import java.io.*;


/**
 * Created by anazarenko on 6/16/17.
 */
public class PythonScriptService extends BasicService{

    private static final Logger LOGGER = Logger.getLogger(PythonScriptService.class);

    public static void exeсutePythonScriptWithArguments(String model, String path, String type) throws IOException {

        String[] cmd = {"/usr/bin/python", AI_HOME + "/" +RECOGNIZE_SCRIPT, "--model", model,"--folder", path, "--output", type};

        for (int i = 0; i <cmd.length ; i++) {
            System.out.print(cmd[i]+" ");
        }
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



    public static void goToFolderWithScript() throws IOException {

        String[] cmd = {"cd", AI_HOME };

        for (int i = 0; i <cmd.length ; i++) {
            System.out.print(cmd[i]+" ");
        }
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
