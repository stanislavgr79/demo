package com.example.demo.domain.model;

import com.example.demo.domain.entity.person.User;
import com.example.demo.service.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("regValidator")
public class RegistrationValidator implements Validator {

    private EmailValidator emailValidator = EmailValidator.getInstance();

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegistrationForm registrationForm = (RegistrationForm) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "NotEmpty.registrationForm.firstName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "NotEmpty.registrationForm.lastName");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "NotEmpty.registrationForm.country");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "NotEmpty.registrationForm.city");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", "NotEmpty.registrationForm.street");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "flat", "NotEmpty.registrationForm.flat");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.registrationForm.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.registrationForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.registrationForm.confirmPassword");

        if (!this.emailValidator.isValid(registrationForm.getEmail())) {
            errors.rejectValue("email", "Pattern.registrationForm.email");
        } else if (registrationForm.getId() == null) {
            User dbUser = userService.findUserByEmail(registrationForm.getEmail());
            if (dbUser != null) {
                errors.rejectValue("email", "Duplicate.registrationForm.email");
            }
        }

        if (!errors.hasErrors()) {
            if (!registrationForm.getConfirmPassword().equals(registrationForm.getPassword())) {
                errors.rejectValue("confirmPassword", "Match.registrationForm.confirmPassword");
            }
        }
    }
}
