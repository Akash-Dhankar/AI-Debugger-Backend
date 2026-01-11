package com.ad.aidebugger.debug.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DebugResponseDto {
    private String rootCause;
    private String fixSteps;
    private String whatNotToDo;
}
