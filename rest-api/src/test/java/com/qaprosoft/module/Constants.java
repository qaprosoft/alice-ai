package com.qaprosoft.module;

import com.qaprosoft.carina.core.foundation.utils.Configuration;


public interface Constants 
{
	public interface URLS
	{
		String RECOGNITION_MODULE_API_URL = Configuration.getEnvArg("recognition.module.api.url");

	}

}
