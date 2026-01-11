package com.ad.aidebugger.debug.service;

import com.ad.aidebugger.ai.DebugAnalyzer;
import com.ad.aidebugger.debug.dto.DebugRequestDto;
import com.ad.aidebugger.debug.dto.DebugResponseDto;
import com.ad.aidebugger.debug.entity.DebugRequest;
import com.ad.aidebugger.debug.repository.DebugRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DebugService {

    private final DebugRequestRepository repository;
    private final DebugAnalyzer debugAnalyzer;

    @Autowired
    public DebugService(DebugRequestRepository repository, DebugAnalyzer debugAnalyzer) {
        this.repository = repository;
        this.debugAnalyzer = debugAnalyzer;
    }

    public DebugResponseDto analyze(DebugRequestDto dto, String username) {
        
        String combinedInput =
                "Language: " + dto.getLanguage() + "\n" + "Error: " + dto.getErrorMessage() + "\n" + "Code:\n" + dto.getCodeSnippet();

        Map<String, String> aiResult = debugAnalyzer.analyze(combinedInput);

        DebugRequest request = new DebugRequest();
        request.setUsername(username);
        request.setLanguage(dto.getLanguage());
        request.setErrorMessage(dto.getErrorMessage());
        request.setCodeSnippet(dto.getCodeSnippet());
        request.setAiIssue(aiResult.get("issue"));
        request.setAiCause(aiResult.get("cause"));
        request.setAiFix(aiResult.get("fix"));

        repository.save(request);

        return new DebugResponseDto(
                aiResult.get("issue"),
                aiResult.get("cause"),
                aiResult.get("fix")
        );
    }
}
