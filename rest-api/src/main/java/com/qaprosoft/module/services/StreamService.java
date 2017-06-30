package com.qaprosoft.module.services;

import java.util.Base64;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.UUID;


/**
 * Created by anazarenko on 6/21/17.
 */
public class StreamService extends BasicService{

    private static final Logger LOGGER = Logger.getLogger(StreamService.class);

    public static String getStringFromFile(String path){
        File file = new File(path);

        BufferedReader fin = null;
        try {
            fin = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            LOGGER.info(e);
        }
        String response="";
        String line;

        try {
            while ((line = fin.readLine()) != null) response+=line;
        } catch (IOException e) {
            LOGGER.info(e);
        }

        try {
            fin.close();
        } catch (IOException e) {
            LOGGER.info(e);
        }

        return response;
    }


    public static String getIputStreamFromFile(String path){
        File file  =new File(path);




        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.getEncoder().encode(bytes), "UTF-8");


        } catch (FileNotFoundException e) {
          LOGGER.info(e);
        } catch (IOException e) {
            LOGGER.info(e);
        }


        return encodedfile;




//        BufferedImage image = null;
//        try {
//            image = ImageIO.read(file);
//        } catch (IOException e) {
//            LOGGER.info(e);
//        }
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        String postfix = getPostfixWithoutDot(path);
//
//        try {
//            ImageIO.write(image, postfix, baos);
//        } catch (IOException e) {
//            LOGGER.info(e);
//        }
//
//        String encodedImage = Base64.encode(baos.toByteArray());
//        return encodedImage;
    }



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

        File file = new File(path +"/"+ inputFile.getOriginalFilename());

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

        try {
            fos.flush();
            out.flush();
            in.close();
            out.close();
            fos.close();
        } catch (IOException e) {
            LOGGER.info(e);
        }

        return file.getAbsolutePath();
    }



    private static String getPostfix(String str){
    return str.substring(str.lastIndexOf("."),str.length());
    }

    private static String getPostfixWithoutDot(String str){
        return str.substring(str.lastIndexOf(".")+1,str.length());
    }


    private static String getPrefix(String str){
        return str.substring(0,str.lastIndexOf("."));
    }


    public static String getPathTempFolder(){
        String folderName = generateRandomFolderName();
        File file  = new File (PATH_TO_TMP_FOLDER+ folderName);
        file.mkdir();
        return file.getAbsolutePath();
    }

    public static String generateRandomFolderName() {
        UUID id = UUID.randomUUID();
        String filename = id.toString().replaceAll("-","");
        return filename;
    }



}



