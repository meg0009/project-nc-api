package com.chivapchichi.repository;

import com.chivapchichi.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    @Query(value = "select * from users where user_name=?1", nativeQuery = true)
    Optional<Users> findByLogin(String login);

    @Query(value = "select user_name from users order by user_name", nativeQuery = true)
    List<String> findAllLogins();
}
