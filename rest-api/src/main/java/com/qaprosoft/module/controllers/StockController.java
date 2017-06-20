package com.qaprosoft.module.controllers;

import javax.validation.Valid;
import com.qaprosoft.module.controllers.models.URLRequest;
import com.qaprosoft.module.controllers.models.URLResult;
import com.qaprosoft.module.services.PythonScriptService;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;



@Controller
@CrossOrigin
public class StockController
{
	private static final Logger LOGGER = Logger.getLogger(StockController.class);

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/request", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody URLResult getJson(@RequestBody @Valid URLRequest request)
	{
		String url = request.getUrl();
		String model = request.getModel();
		String response = null;
		URLResult result = new URLResult();

		try {
			response = PythonScriptService.exeсutePythonScriptWithArguments(model,url);
		} catch (IOException e) {
			LOGGER.info("Can't get response!");
		}

		JSONObject jsonObject = new JSONObject(response);
		result.setInput_image((String) jsonObject.get("input_image"));
		result.setOutput_image((String) jsonObject.get("output_image"));
		result.setMetadata((String) jsonObject.get("metadata"));

		return result;
	}



}