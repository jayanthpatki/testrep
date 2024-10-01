package org.acme;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Entity
@Data
@Table(name = "blogs")
public class Blog extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    private Date createdDate;

    @OneToMany(mappedBy = "blog", fetch = FetchType.EAGER)
    private List<Comment> comments;

    // Getters and Setters
}
