package com.zoothii.finaljavabackend.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "products"})
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private int id;

    //@Column(name = "category_name")
    private String categoryName;

    //@Column(name = "description")
    private String description;

    //@Column(name = "picture")
    //private byte[] picture;

    @JsonIgnore
    // one (category) to many (product)
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
