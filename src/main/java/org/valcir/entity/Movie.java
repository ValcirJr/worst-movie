package org.valcir.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends PanacheEntity {

    private String title;
    @Column(name = "release_year") private Integer year;
    @ManyToOne @JoinColumn(name = "studio_id") private Studio studio;
    @ManyToOne @JoinColumn(name = "producer_id") private Producer producer;
    private Boolean goldenRaspberryWinner;

}
