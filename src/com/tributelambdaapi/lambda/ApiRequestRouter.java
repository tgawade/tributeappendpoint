package com.tributelambdaapi.lambda;

import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;


import org.apache.log4j.Logger;

//import com.tribute.exception.*;
import com.tributelambdaapi.processor.Processor;
import com.amazonaws.services.lambda.runtime.Context;


//import java.util.HashMap;
import java.util.Map;

public class ApiRequestRouter {
	private static final Processor processor = new Processor();
	private static final Logger log = Logger.getLogger(ApiRequestRouter.class);

	public ApiGatewayResponse routeRequest(Map<String, Object> request,Context context) {
		String resource = (String) request.get("resource");
		String httpMethod = (String) request.get("httpMethod");
		Map<String, Object> pathParams = (Map<String, Object>) request.get("pathParameters");
        Map<String, Object> queryStringParams = (Map<String, Object>) request.get("queryStringParameters");
        Object body = null;
        if (StringUtils.equals(httpMethod, "GET")) {
            if (StringUtils.equals(resource, "/")) {
                body = processor.getAllMartyrs("",context);
            }
            if (StringUtils.equals(resource, "/{search_keyword}")) {
                String searchKeyword;
				try {
					searchKeyword = extractSearchKayWord(pathParams);
					context.getLogger().log(" Search Keyword >>>> " + searchKeyword);
					
					body = processor.getAllMartyrs(searchKeyword,context);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					context.getLogger().log(" Exception in ApiGatewayResponse " + e.getMessage());
					e.printStackTrace();
				}
                //PageRequest pageRequest = extractPageRequest(queryStringParams);
                
            }
        }
        return prepareResponse(body);
	}
	private ApiGatewayResponse prepareResponse(Integer errorCode, Object body) {
        return new ApiGatewayResponse(errorCode, body, null);
    }

    private ApiGatewayResponse prepareResponse(Object body) {
        return prepareResponse(200, body);
    }

    private String extractSearchKayWord(Map<String, Object> pathParams) throws Exception {
    	String searchKeyWord = null;
        try {
            //log.info("Pathparams:" + pathParams);
            log.info("Search keyword :" + pathParams.get("search_keyword"));
        	searchKeyWord = (String) pathParams.get("search_keyword");
        } catch (Exception e) {
            e.printStackTrace();
           // throw new BadRequestException("Request path should contain a numeric product category id");
        }
        return searchKeyWord;
    }
}
