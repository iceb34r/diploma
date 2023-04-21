package com.grisha.security.validator;

import com.grisha.security.entities.User;
import com.grisha.security.repositories.UserRepository;
import com.grisha.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean supports(Class<?> aClass) { return User.class.equals(aClass); }
    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (userService.findUserByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }
    }
}
