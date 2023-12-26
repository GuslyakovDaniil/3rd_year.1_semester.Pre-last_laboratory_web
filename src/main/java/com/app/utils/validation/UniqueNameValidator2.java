package com.app.utils.validation;


import com.app.repositories.ModelsRepository;
import com.app.repositories.UsersRepository;
import com.app.utils.validation.*;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueNameValidator2 implements ConstraintValidator<UniqueNameN, String> {
    private final UsersRepository usersRepository;

    public UniqueNameValidator2(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return  usersRepository.findByUsername(value).isEmpty();
    }
}