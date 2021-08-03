package com.zoothii.finaljavabackend.data_access.abstracts;

import com.zoothii.finaljavabackend.entities.concretes.Product;
import com.zoothii.finaljavabackend.entities.dtos.ProductCategoryDetailsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedQuery;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product getByProductName(String productName);

    Product getByProductNameAndCategoryId(String productName, int categoryId);

    List<Product> getByProductNameOrCategoryId(String productName, int categoryId);

    List<Product> getByCategoryIdIn(int[] categoryIds);

    List<Product> getByProductNameContains(String productName);

    List<Product> getByProductNameStartsWith(String productName);

    @Query("FROM Product WHERE productName=:productName AND category.id=:categoryId")
    List<Product> getByNameAndCategory(String productName, int categoryId);

    // https://thorben-janssen.com/dto-projections/ JAVA DTO PROJECTION
    // select * from products p inner join categories c on p.id = c.
    // select product_id, product_name, category_name from categories c inner join products p on c.category_id = p.category_id
    //@Query("SELECT P.id, P.productName, C.categoryName FROM Category AS C INNER JOIN Product AS P ON C.id=P.category.id")
    //@Query("SELECT NEW com.zoothii.finaljavabackend.entities.dtos.ProductCategoryDetailsDto(p.id, p.productName, c.categoryName) FROM Category AS C INNER JOIN Product AS P ON C.id=P.category.id")
    @Query("SELECT NEW com.zoothii.finaljavabackend.entities.dtos.ProductCategoryDetailsDto(p.id, p.productName, c.categoryName) FROM Category AS c JOIN c.products AS p")
    List<ProductCategoryDetailsDto> getProductCategoryDetails();
}
