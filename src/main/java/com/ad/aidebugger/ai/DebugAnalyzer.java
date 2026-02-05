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
            requestBody.put("max_tokens", 200);
            requestBody.put("temperature", 0.2);

            String responseJson = webClient.post()
                    .uri(aiServiceUrl)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return objectMapper.readValue(responseJson, Map.class);

        } catch (Exception e) {
            return errorFallback(e);
        }
    }

    // WHEN ERROR OCCURS IN AI , I HAVE KEPT A FALLBACK RESPONSE

    private Map<String, String> errorFallback(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("issue", "AI service error");
        error.put("rootCause", e.getMessage());
        error.put("fixSteps", "Ensure Flask AI service is running and reachable");
        error.put("whatToDo", "Do not expose raw stack traces to users");
        return error;
    }
}
