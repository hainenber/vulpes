package io.hainenber.vulpes.repository;

import io.hainenber.vulpes.entity.auditlog.AuditLog;
import org.springframework.data.repository.CrudRepository;

public interface AuditLogRepository extends CrudRepository<AuditLog, Long> {
   // TBD
}
