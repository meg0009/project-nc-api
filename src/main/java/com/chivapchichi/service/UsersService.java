package com.chivapchichi.service;

import com.chivapchichi.model.Members;
import com.chivapchichi.model.Users;
import com.chivapchichi.repository.MembersRepository;
import com.chivapchichi.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    //private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;

    private final MembersRepository membersRepository;

    @Autowired
    public UsersService(PasswordEncoder passwordEncoder, UsersRepository usersRepository, MembersRepository membersRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.membersRepository = membersRepository;
    }

    public boolean addNewUserWithMember(Users user, Members members) {
        //PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (usersRepository.findByLogin(user.getUserName()).isPresent()) {
            return false;
        }

        Users newUser = new Users();
        newUser.setUserName(user.getUserName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null || user.getRole().equals("")) {
            newUser.setRole("ROLE_USER");
        } else {
            newUser.setRole(user.getRole());
        }
        usersRepository.save(newUser);

        if (!membersRepository.findByUserName(members.getUserName()).isPresent()) {
            Members newMember = new Members();
            newMember.setUserName(members.getUserName());
            newMember.setFio(members.getFio());
            membersRepository.save(newMember);
        }

        return true;
    }

    public String deleteUserWithMember(Users user, Members member) {
        usersRepository.delete(user);
        membersRepository.delete(member);
        return "deleted";
    }

    public String deleteUserWithMemberByUserName(String username) {
        usersRepository.deleteById(username);
        membersRepository.deleteByUserName(username);
        return "deleted";
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public List<Members> getAllMembers() {
        return membersRepository.findAll();
    }

    public Optional<Users> getUserByUserName(String username) {
        return usersRepository.findByLogin(username);
    }

    public Optional<Members> getMemberByUserName(String username) {
        return membersRepository.findByUserName(username);
    }

    public void updateUserMember(String userName, String role, String fio) {
        if (role != null) {
            Optional<Users> user = usersRepository.findByLogin(userName);
            if (user.isPresent()) {
                Users u = user.get();
                u.setRole(role);
                usersRepository.save(u);
            }
        }
        if (fio != null) {
            Optional<Members> member = membersRepository.findByUserName(userName);
            if (member.isPresent()) {
                Members m = member.get();
                m.setFio(fio);
                membersRepository.save(m);
            }
        }
    }

    /*@Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/
}
