package management_system.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

/**
 * Base entity class to provide common auditing fields for all tables.
 * The @MappedSuperclass annotation ensures these fields are inherited 
 * by all child entities and mapped as columns in their database tables.
 */
@Data
@MappedSuperclass
public abstract class BaseEntity {

    // Automatically assigns the current timestamp when the record is created
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private ZonedDateTime createdAt;

    // Automatically updates the timestamp whenever the record is modified
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    // Used for the soft-delete logic. Null means active, a timestamp means deleted.
    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;
}