package com.chivapchichi.repository;

import com.chivapchichi.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Integer> {

    @Query(value = "select t.* from tournament as t, record as r, members as m where t.id=r.tournament and r.member=m.id and m.user_name=? order by id", nativeQuery = true)
    List<Tournament> findByUserName(String username);

    @Query(value = "select distinct division from tournament order by division", nativeQuery = true)
    List<String> getDivisions();

    @Query(value = "select distinct cast(date as date) from tournament order by date", nativeQuery = true)
    List<Date> getDates();
}
