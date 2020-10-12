package com.vukothersoftware.service;

import com.vukothersoftware.exception.BadUserParametersException;
import com.vukothersoftware.validation.UserValidator;
import com.vukothersoftware.exception.UserNotFoundException;
import com.vuksoftware.exception.DataNotFoundException;
import com.vuksoftware.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vuksoftware.service.UserProcessor;
import org.springframework.util.StringUtils;

import javax.management.BadAttributeValueExpException;
import java.util.List;

@Service
public class UserConsumer implements UserValidator {

    public UserProcessor userProcessor;

    @Autowired
    public UserConsumer(UserProcessor userProcessor) {
        this.userProcessor = userProcessor;
    }

    public String readDependencyAppLabel() {
        return this.userProcessor.AppLabel();
    }

    public List<User> getAllUsers() {
        return this.userProcessor.getAllUsers();
    }

    public User saveUser(User user) {

        try {
            this.validateUserData(user);
        } catch (BadAttributeValueExpException e) {
            throw new BadUserParametersException("User data incorect");
        }
        return this.userProcessor.addUser(user);
    }

    public User getUserById(String userId) {

        try {
            User user = this.userProcessor.getUserById(userId);
            return user;
        } catch(DataNotFoundException e) {
            throw new UserNotFoundException("User with id:"+userId +" not found");
        }
    }


    @Override
    public void validateUserData(User user) throws BadAttributeValueExpException {

        if(StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getSurname()) || StringUtils.isEmpty(user.getBirthdayYear())) {
            throw new BadAttributeValueExpException("User data incorect");
        }
    }

}
