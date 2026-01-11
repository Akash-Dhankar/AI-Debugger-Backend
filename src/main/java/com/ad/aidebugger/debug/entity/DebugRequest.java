package com.ad.aidebugger.debug.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "debug_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DebugRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String language;

    @Column(length = 3000)
    private String errorMessage;

    @Column(length = 8000)
    private String codeSnippet;

    @Column(length = 1000)
    private String aiIssue;

    @Column(length = 2000)
    private String aiCause;

    @Column(length = 3000)
    private String aiFix;
}
