package ru.preproject.task_3_1_5_v2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Role() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
