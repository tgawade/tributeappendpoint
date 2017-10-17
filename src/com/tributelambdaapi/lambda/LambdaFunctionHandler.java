package com.tributelambdaapi.lambda;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<Map<String,Object>, ApiGatewayResponse> {
	private static final ApiRequestRouter router = new ApiRequestRouter();
	private static final Logger log = Logger.getLogger(LambdaFunctionHandler.class);
	public ApiGatewayResponse handleRequest(Map<String,Object> input, Context context) {
        context.getLogger().log("Input: " + input);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");

        ApiGatewayResponse response = router.routeRequest(input,context);
        response.setHeaders(headers);
        context.getLogger().log("Lambda execution finished");
        return response;
    }
   
}
