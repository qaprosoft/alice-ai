package com.qaprosoft.module.api;

import static com.qaprosoft.module.Constants.URLS.RECOGNITION_MODULE_API_URL;

import com.qaprosoft.carina.core.foundation.dataprovider.annotations.XlsDataSourceParameters;
import com.qaprosoft.module.qa.api.utils.ValidationUtils;
import org.apache.http.HttpResponse;
import org.testng.Assert;
import org.testng.annotations.Test;



/**
 * Created by anazarenko on 7/4/17.
 */
public class RecognitionModuleTest  extends AbstractAPITest {


    @Test(dataProvider = "SingleDataProvider",description = "This test compare response")
    @XlsDataSourceParameters(path = "testData.xlsx", sheet = "List",  executeColumn = "Execute", executeValue = "exe", dsArgs = "model")
    public void testCheckJSONInResponse(String model) throws Exception {
        HttpResponse response = sendPOSTRequest(RECOGNITION_MODULE_API_URL+JSON_URL,"json", model);
        Assert.assertEquals(response.getStatusLine().getStatusCode(),200,"Response status code not the same!");
        Assert.assertTrue(ValidationUtils.isJsonValid( getStringFromFile(JSON_RESPONSE), getResponse(response)), "JSON not the same!!!");
    }

}