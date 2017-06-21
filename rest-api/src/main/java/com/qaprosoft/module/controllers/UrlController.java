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
import java.io.IOException;



@Controller
@CrossOrigin
public class UrlController
{
	private static final Logger LOGGER = Logger.getLogger(UrlController.class);

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/request", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getJson(@RequestBody @Valid URLRequest request)
	{
		String url = request.getUrl();
		String model = request.getModel();
		String responseScript = null;

		try {
			responseScript = PythonScriptService.exeсutePythonScriptWithArguments(model,url);
		} catch (IOException e) {
			LOGGER.info("Can't get response!");
		}

		JSONObject jsonObject = new JSONObject(responseScript);
		String metadata = (String) jsonObject.get("output_metadata");
		String response = StreamService.getStringFromURL(metadata);

		return response;
	}



}