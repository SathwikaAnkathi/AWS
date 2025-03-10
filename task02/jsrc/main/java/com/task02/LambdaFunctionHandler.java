package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.HashMap;
import java.util.Map;

public class LambdaFunctionHandler implements RequestHandler<Map<String, Object>, Map<String, Object>> {

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> event, Context context) {
		String path = (String) event.getOrDefault("rawPath", "");
		Map<String, Object> requestContext = (Map<String, Object>) event.get("requestContext");
		Map<String, Object> http = (Map<String, Object>) requestContext.get("http");
		String method = (String) http.get("method");

		Map<String, Object> response = new HashMap<>();
		response.put("headers", Map.of("Content-Type", "application/json"));

		if ("/hello".equals(path) && "GET".equalsIgnoreCase(method)) {
			response.put("statusCode", 200);
			response.put("body", "{\"message\": \"Hello from Lambda!\"}");
		} else {
			response.put("statusCode", 400);
			response.put("body", String.format("{\"error\": \"Bad Request\", \"message\": \"Cannot %s %s\"}", method, path));
		}

		return response;
	}
}
