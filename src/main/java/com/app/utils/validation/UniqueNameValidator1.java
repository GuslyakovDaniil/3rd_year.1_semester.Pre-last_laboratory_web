package com.app.utils.validation;


import com.app.repositories.ModelsRepository;
import com.app.utils.validation.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueNameValidator1 implements ConstraintValidator<UniqueNameZ, String> {
    private final ModelsRepository modelsRepository;

    public UniqueNameValidator1(ModelsRepository modelsRepository) {
        this.modelsRepository=modelsRepository;
    }


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return  modelsRepository.findByName(value).isEmpty();
    }
}
