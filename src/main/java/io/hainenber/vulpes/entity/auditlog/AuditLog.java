package io.hainenber.vulpes.entity.auditlog;

import com.fasterxml.jackson.annotation.JsonProperty;
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

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "audit_log")
public class AuditLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("id")
    private Long id;

    // Default constructor for JPA entity construction at runtime.
    // Source: https://www.baeldung.com/jpa-no-argument-constructor-entity-class#reasons-for-no-arg-constructor
    public AuditLog() { }

    public AuditLog(String previousCommitId, String currentCommitId, Map<DiffEntry.ChangeType, List<String>> changes) {
        this.previousCommitId = previousCommitId;
        this.currentCommitId = currentCommitId;
        this.changes = changes;
    }

    @Column(name = "previous_commit_id", nullable = false, updatable = false)
    @JsonProperty("previous_commit_id")
    private String previousCommitId;

    @Column(name = "current_commit_id")
    @JsonProperty("current_commit_id")
    private String currentCommitId;

    @Column(name = "created_at")
    @CreationTimestamp
    @JsonProperty("created_at")
    private Timestamp createdAt;

    @Column(name = "changes", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    @JsonProperty("changes")
    private Map<DiffEntry.ChangeType, List<String>> changes;

    public String getCurrentCommitId() {
        return currentCommitId;
    }

    public String getPreviousCommitId() {
        return previousCommitId;
    }

    public Map<DiffEntry.ChangeType, List<String>> getChanges() {
        return changes;
    }
}
