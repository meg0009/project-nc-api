package com.chivapchichi.controller.rest;

import com.chivapchichi.model.UserAndMember;
import com.chivapchichi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("registration/api")
public class RegistrationRestController {

    private final UsersService usersService;

    @Autowired
    public RegistrationRestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public Map<String, String> register(@Valid @RequestBody UserAndMember user, BindingResult result) {
        Map<String, String> res = new HashMap<>();
        if (!result.hasErrors()) {
         if (usersService.addNewUserWithMember(user.getUsers(), user.getMembers())) {
             res.put("result", "added");
         } else {
             res.put("result", "user exists");
         }
        } else {
            res.put("result", result.toString());
        }
        return res;
    }
}
