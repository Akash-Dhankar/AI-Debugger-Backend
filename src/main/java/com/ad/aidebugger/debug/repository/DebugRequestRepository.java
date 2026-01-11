package com.ad.aidebugger.debug.repository;

import com.ad.aidebugger.debug.entity.DebugRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebugRequestRepository extends JpaRepository<DebugRequest, Long> {
}
