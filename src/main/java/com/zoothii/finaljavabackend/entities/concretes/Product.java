package com.zoothii.finaljavabackend.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;

    //@Column(name = "category_id")
    //private int categoryId;

    //@Column(name = "product_name")
    @NotBlank
    private String productName;

    //@Column(name = "quantity_per_unit")
    private String quantityPerUnit;

    //@Column(name = "unit_price")
    private double unitPrice;

    //@Column(name = "units_in_stock")
    private short unitsInStock;

    // many (product) to one (category)

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
