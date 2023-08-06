package com.stage.elearning.model.acticle;


import com.stage.elearning.model.categories.Categorie;
import com.stage.elearning.model.file.FileData;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "articles")
public class Article {


    @SequenceGenerator(
            name = "article_sequence",
            sequenceName = "article_sequence",
            allocationSize = 1
    )

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "article_sequence"
    )
    @Column(name = "id" , unique = true ,nullable = false)
    private long Id;


    @Column(name = "name")
    private String name;

    @Column(name ="price")
    @Min(value = 0, message = "Price cannot be less than 0")
    private float price;

    @Column(name = "starting_date")
    private Date startingDate;

    @Column(name = "ending_date")
    private Date endingDate;

    @Column(name = "certification")
    private boolean certification;

    @Column(name = "time_period")
    @Min(value = 0, message = "Price cannot be less than 0")
    private int timePeriod;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private FileData image;


}
