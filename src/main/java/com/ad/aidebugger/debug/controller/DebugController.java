package com.ad.aidebugger.debug.controller;

import com.ad.aidebugger.debug.dto.DebugRequestDto;
import com.ad.aidebugger.debug.dto.DebugResponseDto;
import com.ad.aidebugger.debug.service.DebugService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/debug")
public class DebugController {

    private final DebugService debugService;

    public DebugController(DebugService debugService) {
        this.debugService = debugService;
    }

    @PostMapping("/analyze")
    public DebugResponseDto analyze(
            @RequestBody DebugRequestDto request,
            Authentication authentication
    ) {
        String username = authentication.getName();
        return debugService.analyze(request, username);
    }
}
