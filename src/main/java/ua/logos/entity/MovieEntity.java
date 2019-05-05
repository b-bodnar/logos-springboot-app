package ua.logos.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "movies")
public class MovieEntity extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

//    @Column(nullable = false)
//    private String category;

    @Column(nullable = false)
    private String duration;

    @Column(name = "age_rating", nullable = false)
    private String ageRating;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
}
