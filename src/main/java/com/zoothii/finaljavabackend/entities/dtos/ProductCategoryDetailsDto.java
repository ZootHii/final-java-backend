package com.zoothii.finaljavabackend.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDetailsDto {
    private int id;
    private String productName;
    private String categoryName;
}
