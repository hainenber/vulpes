package io.hainenber.vulpes.controller;

import io.hainenber.vulpes.entity.auditlog.AuditLog;
import io.hainenber.vulpes.repository.AuditLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class AuditLogController {
    private final AuditLogRepository auditLogRepository;

    AuditLogController(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    @RequestMapping("/audit-logs")
    public Page<AuditLog> all(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "asc") String order
    ) {
        final Sort sort = Objects.equals(order, "asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        final Pageable pageable = PageRequest.of(page - 1, size, sort);
        return auditLogRepository.findAll(pageable);
    }
}

