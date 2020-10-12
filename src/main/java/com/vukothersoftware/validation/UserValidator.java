package com.vukothersoftware.validation;

import com.vuksoftware.model.User;

import javax.management.BadAttributeValueExpException;

public interface UserValidator {

    public void validateUserData(User user) throws BadAttributeValueExpException;
}
