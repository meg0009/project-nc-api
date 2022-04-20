package com.chivapchichi.repository;

import com.chivapchichi.model.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface MembersRepository extends JpaRepository<Members, Integer> {

    @Query(value = "select * from members where user_name=?", nativeQuery = true)
    Optional<Members> findByUserName(String username);

    @Modifying
    @Transactional
    @Query(value = "delete from members where user_name=?", nativeQuery = true)
    void deleteByUserName(String username);
}
