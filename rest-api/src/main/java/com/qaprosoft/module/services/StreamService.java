package com.qaprosoft.module.services;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Created by anazarenko on 6/21/17.
 */
public class StreamService extends BasicService{

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
        try {
            while (in.available()>0) str+=(char)in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(str);
        return str;
    }


    public static String getStringFromURL(String url){
        return getStringFromInputStream(getInputStreamFromNet(url));
    }


    public static void saveFileOnLocalDisk(String url){
        URL link = null;
        try {
            link = new URL(url);
        } catch (MalformedURLException e) {
            LOGGER.info(e);
        }
        File file = new File(PATH_TO_IMG_FILE);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            LOGGER.info(e);
        }
        InputStream in = null;
        try {
            in = new BufferedInputStream(link.openStream());
        } catch (IOException e) {
            LOGGER.info(e);
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n ;
        try {
            while (-1!=(n=in.read(buf)))
            {
                fos.write(buf, 0, n);
            }
        } catch (IOException e) {
            LOGGER.info(e);
        }

        try {
            fos.close();
            out.close();
            in.close();
        } catch (IOException e) {
            LOGGER.info(e);
        }

    }

    public static void deleteFile(){
        File file = new File(PATH_TO_IMG_FILE);
        file.delete();
    }


    public static  String saveImage(MultipartFile file1){
        File file = new File(PATH_TO_IMG_FILE);

        FileOutputStream fos =null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStream inputStream =null;
        try {
         inputStream = file1.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (inputStream.available()>0) fos.write(inputStream.read());

            return PATH_TO_IMG_FILE;
        } catch (IOException e) {
            e.printStackTrace();
        }


        return PATH_TO_IMG_FILE;
    }

}



