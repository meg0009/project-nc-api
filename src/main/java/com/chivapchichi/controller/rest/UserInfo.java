package com.chivapchichi.controller.rest;

import com.chivapchichi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("myinfo")
public class UserInfo {

    private final UsersService usersService;

    @Autowired
    public UserInfo(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/get-user-info")
    public ResponseEntity<?> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipal = authentication.getName();
        return ResponseEntity.ok(usersService.getUserByUserName(currentPrincipal));
    }

    @GetMapping("/get-member-info")
    public ResponseEntity<?> getMemberInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipal = authentication.getName();
        return ResponseEntity.ok(usersService.getMemberByUserName(currentPrincipal));
    }
}
