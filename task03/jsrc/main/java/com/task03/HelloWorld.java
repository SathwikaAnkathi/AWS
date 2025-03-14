package com.task03;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.RetentionSetting;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@LambdaHandler(
		lambdaName = "hello_world",
		roleName = "hello_world-role",
		isPublishVersion = true,
		aliasName = "${lambdas_alias_name}",
		logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
public class HelloWorld implements RequestHandler<Map<String, Object>, Map<String, Object>> {

	private static final Logger LOGGER = Logger.getLogger(HelloWorld.class.getName());

	@Override
	public Map<String, Object> handleRequest(Map<String, Object> event, Context context) {
		LOGGER.info("Received event: " + event);

		// TODO: Implement business logic
		return createResponse();
	}

	private Map<String, Object> createResponse() {
		Map<String, Object> response = new HashMap<>();
		response.put("statusCode", 200);
		response.put("message", "Hello from Lambda");
		return response;
	}
}
