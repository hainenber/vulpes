package io.hainenber.vulpes.repository;

import io.hainenber.vulpes.entity.auditlog.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> { }
