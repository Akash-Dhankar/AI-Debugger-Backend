package com.ad.aidebugger.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class DebugAnalyzer {

    @Value("${ai.service.url}")
    private String aiServiceUrl;

    private final WebClient webClient = WebClient.builder().build();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public Map<String, String> analyze(
            String language,
            String errorMessage,
            String codeSnippet
    ) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("language", language);
            requestBody.put("errorMessage", errorMessage);
            requestBody.put("codeSnippet", codeSnippet);

            String responseJson = webClient.post()
                    .uri(aiServiceUrl)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Map<String, String> ai = objectMapper.readValue(responseJson, Map.class);

            Map<String, String> normalized = new HashMap<>();
            normalized.put("rootCause", ai.getOrDefault("rootCause", ""));
            normalized.put("fixSteps", ai.getOrDefault("fixSteps", ""));
            normalized.put("whatNotToDo", ai.getOrDefault("whatNotToDo", ""));

            return normalized;

        } catch (Exception e) {
            return errorFallback(e);
        }
    }

    private Map<String, String> errorFallback(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("rootCause", e.getMessage());
        error.put("fixSteps", "Ensure Flask AI service is running");
        error.put("whatNotToDo", "Do not expose internal errors to users");
        return error;
    }
}