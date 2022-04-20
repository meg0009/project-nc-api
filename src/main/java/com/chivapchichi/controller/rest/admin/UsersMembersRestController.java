package com.chivapchichi.controller.rest.admin;

import com.chivapchichi.model.Members;
import com.chivapchichi.model.Users;
import com.chivapchichi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("admin/api/users")
public class UsersMembersRestController {

    private final UsersService usersService;

    @Autowired
    public UsersMembersRestController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/get-users")
    public List<Users> getUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/get-members")
    public List<Members> getMembers() {
        return usersService.getAllMembers();
    }

    @GetMapping("/user/{username}")
    public Optional<Users> getUserByUsername(@PathVariable("username") String username) throws Exception {
        return usersService.getUserByUserName(username);
    }

    @GetMapping("/member/{username}")
    public Optional<Members> getMemberByUserName(@PathVariable("username") String username) throws Exception {
        return usersService.getMemberByUserName(username);
    }

    @DeleteMapping("/delete-user/{username}")
    public Map<String, Boolean> deleteUserMember(@PathVariable("username") String username) {
        Optional<Users> user = usersService.getUserByUserName(username);
        Map<String, Boolean> res = new HashMap<>();

        if (user.isPresent()) {
            usersService.deleteUserWithMemberByUserName(username);
            res.put("deleted", true);
        } else {
            res.put("deleted", false);
        }
        return res;
    }

    @PatchMapping("/user/{username}")
    public ResponseEntity<Users> changeUser(@PathVariable("username") String username, @RequestBody Map<String, String> request) throws Exception {
        String role = request.get("role");
        if (role != null) {
            usersService.updateUserMember(username, role, null);
        }
        Users user = usersService.getUserByUserName(username).orElseThrow(() -> new Exception("someerror"));
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/member/{username}")
    public ResponseEntity<Members> changeMember(@PathVariable("username") String username, @RequestBody Map<String, String> request) throws Exception {
        String fio = request.get("fio");
        if (fio != null) {
            usersService.updateUserMember(username, null, fio);
        }
        Members member = usersService.getMemberByUserName(username).orElseThrow(() -> new Exception("someerror"));
        return ResponseEntity.ok(member);
    }
}
