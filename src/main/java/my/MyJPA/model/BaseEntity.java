package my.MyJPA.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@ToString
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(updatable = false)
    private String createdBy;

    private String updatedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Optional<String> getCreatedBy() {
        return Optional.ofNullable(createdBy);
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Optional<LocalDateTime> getCreatedAt() {
        return null == createdAt ? Optional.empty() : Optional.of(LocalDateTime.ofInstant(createdAt.toInstant(), ZoneId.systemDefault()));
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = Date.from(createdAt.atZone(ZoneId.systemDefault()).toInstant());
    }

    public Optional<String> getUpdatedBy() {
        return Optional.ofNullable(updatedBy);
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Optional<LocalDateTime> getUpdatedAt() {
        return null == updatedAt ? Optional.empty() : Optional.of(LocalDateTime.ofInstant(updatedAt.toInstant(), ZoneId.systemDefault()));
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = Date.from(updatedAt.atZone(ZoneId.systemDefault()).toInstant());
    }

    @PrePersist
    public void onPrePersist() {
        setCreatedAt(LocalDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate() {
        setUpdatedAt(LocalDateTime.now());
    }

}
