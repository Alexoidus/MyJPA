package my.MyJPA.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Release extends BaseVersionedEntity {

    private String title;

    private LocalDate date;

    @ManyToOne
    private Artist artist;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JoinColumn(name = "owner_id")
    @OrderBy("name")
    private List<Track> tracks = new ArrayList<>();

}
