package io.hainenber.vulpes.controller;

import io.hainenber.vulpes.entity.auditlog.AuditLog;
import io.hainenber.vulpes.repository.AuditLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuditLogController {
    private final AuditLogRepository auditLogRepository;

    AuditLogController(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @GetMapping("/audit-logs")
    List<AuditLog> all() {
        return auditLogRepository.findAll();
    }
}

