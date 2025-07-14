package org.valcir.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Producer extends PanacheEntity {
    @Getter @Column(unique = true, nullable = false) private String name;
    @ManyToMany(mappedBy = "producers") private Set<Movie> movies = new HashSet<>();

    public Producer(String name) {
        this.name = name;
        this.movies = new HashSet<>();
    }

}
