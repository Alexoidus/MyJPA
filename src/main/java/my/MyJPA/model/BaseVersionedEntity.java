package my.MyJPA.model;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@MappedSuperclass
public abstract class BaseVersionedEntity extends BaseEntity {
    @ToString.Include
    @Version
    private Integer version;

}