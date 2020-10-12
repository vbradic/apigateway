package com.vukothersoftware.controller;


import org.springframework.web.bind.annotation.*;
import com.vukothersoftware.service.UserConsumer;
import com.vuksoftware.model.User;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserConsumer userConsumer;

    @RequestMapping(value = "/applabel", method = RequestMethod.GET)
    @ResponseBody
    public String getFoosBySimplePath() {
        return this.userConsumer.readDependencyAppLabel();
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getAllUsers() {
        return this.userConsumer.getAllUsers();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public User saveUser(@RequestBody User user) {
        return this.userConsumer.saveUser(user);
    }

    @RequestMapping(value="/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User getUserById(@PathVariable("id") String userId) {

        return this.userConsumer.getUserById(userId);
    }

}
