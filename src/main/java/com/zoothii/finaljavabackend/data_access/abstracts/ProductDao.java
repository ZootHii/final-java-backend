package com.zoothii.finaljavabackend.data_access.abstracts;

import com.zoothii.finaljavabackend.entities.concretes.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {

}
