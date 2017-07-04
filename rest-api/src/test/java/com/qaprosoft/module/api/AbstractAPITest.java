package com.qaprosoft.module.api;


import com.qaprosoft.carina.core.foundation.APITest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.testng.log4testng.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.*;
import java.util.Base64;


public abstract class AbstractAPITest extends APITest
{

    private static final Logger LOGGER = Logger.getLogger(AbstractAPITest.class);
    protected final static JSONParser parser = new JSONParser();
    private final static String PATH_TO_FILE = "src/test/resources/test.jpg";
    protected final static String JSON_URL = "/downloadJSON";
    protected final static String XML_URL = "/downloadXML";
    protected final static String IMAGE_URL = "/downloadImage";
    protected final static String JSON_RESPONSE = "src/test/resources/response.json";
    protected final static String XML_RESPONSE = "src/test/resources/response.xml";
    protected final static String IMG_RESPONSE = "src/test/resources/response.jpg";




    protected HttpResponse sendPOSTRequest(String url, String responseType, String model) throws IOException {
        HttpPost request = new HttpPost(url);
        HttpClient client = new DefaultHttpClient();
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        File file = new File(PATH_TO_FILE);
        FileBody fb = new FileBody(file);
        builder.addPart("file", fb);
        builder.addTextBody("name", model);
        builder.addTextBody("responseType", responseType);
        HttpEntity yourEntity = builder.build();
        request.setEntity(yourEntity);
        HttpResponse response = null;
        response = client.execute(request);
        return response;
    }



    protected JSONArray getJsonArray(String path) throws Exception
    {
        return  (JSONArray) parser.parse(new FileReader(path));
    }

    protected String getXmlFromFile(String path) throws Exception
    {
        return  (String) parser.parse(new FileReader(path));
    }

    protected String getResponse(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity(), "UTF-8");

    }


    public  String getStringFomImage(String path) {
        File file = new File(path);
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = new String(Base64.getEncoder().encode(bytes), "UTF-8");

        } catch (FileNotFoundException e) {
            LOGGER.info(e);
        } catch (IOException e) {
            LOGGER.info(e);
        }

        return encodedfile;
    }



    public  String main(String  pathToFile) throws ParserConfigurationException, IOException, SAXException {
        String filePath = pathToFile;
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        dBuilder = dbFactory.newDocumentBuilder();

        Document doc = dBuilder.parse(xmlFile);

        //System.out.println(doc.);
        return dBuilder.toString();
    }
}