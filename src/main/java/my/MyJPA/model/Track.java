package my.MyJPA.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Track extends BaseVersionedEntity {

    private int position;

    private String title;

    @ManyToOne
    @JoinColumn(name = "release_id")
    private Release release;

}
