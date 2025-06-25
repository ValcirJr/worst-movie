package org.valcir.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@NoArgsConstructor
public class Producer extends PanacheEntity {
    @Getter @Column(unique = true, nullable = false) private String name;
    @OneToMany(mappedBy = "producer", cascade = ALL) private List<Movie> movies;

    public Producer(String name) {
        this.name = name;
        this.movies = new ArrayList<Movie>();
    }

}
