package com.task03;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.RetentionSetting;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(
		lambdaName = "hello_world",
		roleName = "hello_world-role",
		isPublishVersion = true,
		aliasName = "${lambdas_alias_name}",
		logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
public class HelloWorld implements RequestHandler<Map<String, Object>, Map<String, Object>> {

	private final ObjectMapper objectMapper = new ObjectMapper(); // JSON Serializer

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> request, Context context) {
		context.getLogger().log("Hello from Lambda");

		// Create response map
		Map<String, Object> response = new HashMap<>();
		response.put("statusCode", 200);
		response.put("headers", Map.of("Content-Type", "application/json"));

		// JSON body
		Map<String, String> responseBody = new HashMap<>();
		responseBody.put("message", "Hello from Lambda");

		try {
			response.put("body", objectMapper.writeValueAsString(responseBody));
		} catch (JsonProcessingException e) {
			context.getLogger().log("Error serializing response: " + e.getMessage());
			response.put("statusCode", 500);
			response.put("body", "{\"message\": \"Internal server error\"}");
		}

		return response;
	}
}
