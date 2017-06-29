package com.qaprosoft.module.controllers;

import com.qaprosoft.module.services.PythonScriptService;
import com.qaprosoft.module.services.StreamService;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.nio.file.Paths;


@Controller
@CrossOrigin
public class UrlController
{

	private static final Logger LOGGER = Logger.getLogger(UrlController.class);


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/downloadXML", method = RequestMethod.POST,produces = MediaType.TEXT_XML_VALUE)
	public @ResponseBody String uploadXML(@RequestParam("file") MultipartFile file, @RequestParam ("name") String model,
												 @RequestParam("responseType") String type, RedirectAttributes redirectAttributes) throws IOException, InterruptedException {

		String tmpPath = StreamService.getPathTempFolder();
		String path = StreamService.saveImage(file, tmpPath);

		try {
			PythonScriptService.exeсutePythonScriptWithArguments(model,getParentPath(path),type);
		} catch (IOException e) {
			LOGGER.info("Can't get response!");
		}

		String response = StreamService.getStringFromFile(path+"/out/"+"test_file."+type);

		System.out.println(response);

		return response;
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/downloadJSON", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String uploadJSON(@RequestParam("file") MultipartFile file, @RequestParam ("name") String model,
												 @RequestParam("responseType") String type, RedirectAttributes redirectAttributes) throws IOException {
		String tmpPath = StreamService.getPathTempFolder();
		String path = StreamService.saveImage(file, tmpPath);

		try {
			PythonScriptService.exeсutePythonScriptWithArguments(model,getParentPath(path),type);
		} catch (IOException e) {
			LOGGER.info("Can't get response!");
		}

		String response = StreamService.getStringFromFile(path+"/out/"+"test_file."+type);


		System.out.println(response);

		return response;
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/downloadImage", method = RequestMethod.POST,produces = MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody String uploadImage(@RequestParam("file") MultipartFile file, @RequestParam ("name") String model,
										   @RequestParam("responseType") String type, RedirectAttributes redirectAttributes) throws IOException {

		String tmpPath = StreamService.getPathTempFolder();
		String path = StreamService.saveImage(file, tmpPath);

		try {
			PythonScriptService.exeсutePythonScriptWithArguments(model,getParentPath(path),type);
		} catch (IOException e) {
			LOGGER.info("Can't get response!");
		}

		String response = StreamService.getStringFromFile(path+"/out/"+"test_file."+type);

		System.out.println(response);


		return response;
	}


	private static String getParentPath(String absolutePath){
		return  Paths.get(absolutePath).getParent().toString();
	}

}