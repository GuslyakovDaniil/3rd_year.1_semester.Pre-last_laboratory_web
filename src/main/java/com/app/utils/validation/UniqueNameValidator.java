package com.app.utils.validation;

import com.app.repositories.BrandsRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {
    private final BrandsRepository brandsRepository;

    public UniqueNameValidator(BrandsRepository brandsRepository) {
        this.brandsRepository = brandsRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return  brandsRepository.findByName(value).isEmpty();
    }
}
