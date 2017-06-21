package com.qaprosoft.module.services;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by anazarenko on 6/21/17.
 */
public class StreamService {
    private static final Logger LOGGER = Logger.getLogger(StreamService.class);

    public static InputStream getInputStreamFromNet(String url){
        URL link=null;

        try {
            link  = new URL(url);
        } catch (MalformedURLException e) {
            LOGGER.info(e);
        }

        InputStream inputStream = null;
        URLConnection connection = null;
        try {
            connection = link.openConnection();
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            LOGGER.info(e);
        }

        return inputStream;
    }


    public static String getStringFromInputStream(InputStream in){
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


    public static String getStringFromURL(String url){
        return getStringFromInputStream(getInputStreamFromNet(url));
    }
}
