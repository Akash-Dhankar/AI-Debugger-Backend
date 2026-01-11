package com.ad.aidebugger.ai;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DebugAnalyzer {

    public Map<String, String> analyze(String input) {
        Map<String, String> response = new HashMap<>();

        if (input.toLowerCase().contains("nullpointer")) {
            response.put("issue", "NullPointerException detected");
            response.put("cause", "An object is being accessed without initialization");
            response.put("fix", "Check for null before accessing or initialize the object");
        } else if (input.toLowerCase().contains("sql")) {
            response.put("issue", "Database Error");
            response.put("cause", "SQL syntax or connection problem");
            response.put("fix", "Verify SQL query and database connectivity");
        } else {
            response.put("issue", "Unknown Error");
            response.put("cause", "AI is analyzing...");
            response.put("fix", "Use AI suggestions or review logs");
        }

        return response;
    }
}
