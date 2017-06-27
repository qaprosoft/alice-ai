package com.qaprosoft.module.controllers;

import javax.validation.Valid;
import com.qaprosoft.module.controllers.models.URLRequest;
import com.qaprosoft.module.services.PythonScriptService;
import com.qaprosoft.module.services.StreamService;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;


@Controller
@CrossOrigin
public class UrlController
{

	private static final Logger LOGGER = Logger.getLogger(UrlController.class);


	@RequestMapping("/")
	public String index() {
		return "index.html";
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/request", method = RequestMethod.POST, produces = MediaType.APPLICATION_XML_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getJson(@RequestBody @Valid URLRequest request)
	{
		String url = request.getUrl();
		String model = request.getModel();
		String responseScript = null;

		StreamService.saveFileOnLocalDisk(url);

		try {
			responseScript = PythonScriptService.exeсutePythonScriptWithArguments(model,url);
		} catch (IOException e) {
			LOGGER.info("Can't get response!");
		}

		JSONObject jsonObject = new JSONObject(responseScript);
		String metadata = (String) jsonObject.get("output_metadata");
		System.out.println(metadata);
		String response = StreamService.getStringFromURL(metadata);

		//StreamService.deleteFile();


		System.out.println(response);
		return response;
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/download", method = RequestMethod.POST,produces = MediaType.TEXT_XML_VALUE)
	public @ResponseBody String singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam ("name") String model,
											  RedirectAttributes redirectAttributes) throws IOException {
		String url = StreamService.saveImage(file);

		String responseScript = "sfsf";
		System.out.println(responseScript);
		try {
			responseScript = PythonScriptService.exeсutePythonScriptWithArguments(model,url);
			System.out.println(responseScript + " response script");
		} catch (IOException e) {
			LOGGER.info("Can't get response!");
		}



		JSONObject jsonObject = new JSONObject(responseScript);
		String metadata = (String) jsonObject.get("output_metadata");
		String response = StreamService.getStringFromURL(metadata);

		System.out.println(response);
		//StreamService.deleteFile();
		return response;
	}






}