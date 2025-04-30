package io.hainenber.vulpes.entity.auditlog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.eclipse.jgit.diff.DiffEntry;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Map;

@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public AuditLog(String previousCommitId, String currentCommitId, Map<String, DiffEntry.ChangeType> changedStates) {
        this.previousCommitId = previousCommitId;
        this.currentCommitId = currentCommitId;
        this.changedStates = changedStates;
    }

    @Column(name = "previous_commit_id", nullable = false, updatable = false)
    private final String previousCommitId;

    @Column(name = "current_commit_id")
    private final String currentCommitId;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "changed_states")
    @JdbcTypeCode(SqlTypes.JSON)
    private final Map<String, DiffEntry.ChangeType> changedStates;
}
