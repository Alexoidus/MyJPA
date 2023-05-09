package my.MyJPA.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@Entity
public class Artist extends BaseVersionedEntity {
    @ToString.Include
    private String name;

    @Column(length = 4096)
    private String description;

}
