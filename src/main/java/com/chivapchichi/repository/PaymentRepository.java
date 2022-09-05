package com.chivapchichi.repository;

import com.chivapchichi.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    @Query(value = "select p.* from payment as p join record as r on p.record_id=r.id join members on m.id=r.member where m.user_name=?", nativeQuery = true)
    List<Payment> selectAllByUserName(String username);
}
