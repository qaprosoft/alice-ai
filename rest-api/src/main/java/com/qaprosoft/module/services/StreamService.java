package com.qaprosoft.module.services;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.apache.tomcat.util.http.fileupload.FileUtils.deleteDirectory;


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




    public static String getStringFromFile(String path){



       File file = new File(path);

        InputStream inputStream =null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            LOGGER.info(e);
        }

        StringBuilder builder = new StringBuilder();


        BufferedInputStream in = new BufferedInputStream(inputStream);

        byte[] buf = new byte[1024];
        try {
            while (-1!=in.read(buf))
            {
                builder.append(buf);
            }

        } catch (IOException e) {
            LOGGER.info(e);
        }

        return builder.toString();
    }


//    public static void saveFileOnLocalDisk(String url){
//        URL link = null;
//        try {
//            link = new URL(url);
//        } catch (MalformedURLException e) {
//            LOGGER.info(e);
//        }
//        File file = new File(PATH_TO_IMG_FILE);
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(file);
//        } catch (FileNotFoundException e) {
//            LOGGER.info(e);
//        }
//        InputStream in = null;
//
//        try {
//            in = new BufferedInputStream(link.openStream());
//        } catch (IOException e) {
//            LOGGER.info(e);
//        }
//
//            byte[] buf = new byte[1024];
//            int n ;
//        try {
//            while (-1!=(n=in.read(buf)))
//            {
//                fos.write(buf, 0, n);
//            }
//        } catch (IOException e) {
//            LOGGER.info(e);
//        }
//
//        try {
//            fos.close();
//            in.close();
//        } catch (IOException e) {
//            LOGGER.info(e);
//        }
//
//    }

    public static void deleteTempFolder(String path){

        File file = new File(path);

        if(!file.exists())
            return;
        if(file.isDirectory())
        {
            for(File f : file.listFiles())
                deleteTempFolder(f.getAbsolutePath());
            file.delete();
        }
        else
        {
            file.delete();
        }


    }


    public static String saveImage(MultipartFile inputFile, String path){

        String postfix = getPostfix(inputFile.getOriginalFilename());

        File file = new File(path +"/"+ IMAGE_NAME+postfix);//File.createTempFile(IMAGE_NAME,postfix);


        FileOutputStream fos =null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        InputStream inputStream =null;
        try {
         inputStream = inputFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedInputStream in = new BufferedInputStream(inputStream);

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

        return file.getAbsolutePath();
    }



    private static String getPostfix(String str){
    return str.substring(str.lastIndexOf("."),str.length());
    }


    public static String getPathTempFolder(){

        File file  = new File (PATH_TO_TMP_FOLDER+"fdsfsfsfsfsf");
        file.mkdir();
        return file.getAbsolutePath();


    }



}



