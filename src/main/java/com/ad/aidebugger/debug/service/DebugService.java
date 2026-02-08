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

        Map<String, String> aiResult = debugAnalyzer.analyze(
                dto.getLanguage(),
                dto.getErrorMessage(),
                dto.getCodeSnippet()
        );

        DebugRequest request = new DebugRequest();
        request.setUsername(username);
        request.setLanguage(dto.getLanguage());
        request.setErrorMessage(dto.getErrorMessage());
        request.setCodeSnippet(dto.getCodeSnippet());
//        request.setAiIssue(aiResult.getOrDefault("issue", ""));
        request.setAiCause(aiResult.getOrDefault("rootCause", ""));
        request.setAiFix(aiResult.getOrDefault("fixSteps", ""));
        request.setAiIssue(aiResult.getOrDefault("whatNotToDo", ""));
        repository.save(request);

        return new DebugResponseDto(
                aiResult.getOrDefault("rootCause", ""),
                aiResult.getOrDefault("fixSteps", ""),
                aiResult.getOrDefault("whatNotToDo", "")
        );
    }
}
