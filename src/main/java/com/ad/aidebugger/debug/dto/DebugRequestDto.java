package com.ad.aidebugger.debug.dto;
import lombok.Data;

@Data
public class DebugRequestDto {
    private String language;
    private String errorMessage;
    private String codeSnippet;
}

