/*
package com.zoothii.finaljavabackend.business.validation;

import com.zoothii.finaljavabackend.entities.concretes.Product;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("beforeCreateProductValidator")
public class ProductValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");

        Product product = (Product) o;

        if (product.getCategory().getId() == 0){
            errors.rejectValue("categoryId", "categoryId.required");
        }

    }
}
*/
