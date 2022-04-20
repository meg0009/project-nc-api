package com.chivapchichi.repository;

import com.chivapchichi.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

    @Modifying
    @Transactional
    @Query(value = "insert into record (member, tournament) " +
            "values ((select id from members where user_name=:member)," +
            "(select id from tournament where id=:tournament))", nativeQuery = true)
    int saveRecord(@Param("member") String memberNickname, @Param("tournament") int tournament);

    @Override
    @Query(value = "select r.id as id, m.user_name as member, m.fio as fio, t.id as tournament from record as r, members as m, tournament as t where r.member = m.id and r.tournament = t.id and r.id=?", nativeQuery = true)
    Optional<Record> findById(Integer integer);

    @Override
    @Query(value = "select r.id as id, m.user_name as member, m.fio as fio, t.id as tournament from record as r, members as m, tournament as t where r.member = m.id and r.tournament = t.id order by r.id", nativeQuery = true)
    List<Record> findAll();

    @Query(value = "select r.id as id, m.user_name as member, m.fio as fio, t.id as tournament from record as r, members as m, tournament as t where r.member = m.id and r.tournament = t.id and r.tournament=? order by r.id", nativeQuery = true)
    List<Record> findByTournament(Integer integer);

    @Query(value = "select r.id as id, m.user_name as member, m.fio as fio, t.id as tournament from record as r, members as m, tournament as t where r.member = m.id and r.tournament = t.id and r.tournament=?1 order by r.id limit (select max from tournament where id=?1)", nativeQuery = true)
    List<Record> findByTournamentMainTeam(Integer integer);

    @Query(value = "select r.id as id, m.user_name as member, m.fio as fio, t.id as tournament from record as r, members as m, tournament as t where r.member = m.id and r.tournament = t.id and r.tournament=?1 order by r.id offset (select max from tournament where id=?1)", nativeQuery = true)
    List<Record> findByTournamentReserve(Integer integer);

    @Query(value = "select r.id as id, m.user_name as member, m.fio as fio, t.id as tournament from record as r, members as m, tournament as t where r.member = m.id and r.tournament = t.id and m.user_name=:user and t.id=:tournament", nativeQuery = true)
    Optional<Record> findByUserNameAndTournament(@Param("user") String userName, @Param("tournament") Integer tournament);

    @Override
    @Modifying
    @Transactional
    @Query(value = "delete from record where id=?", nativeQuery = true)
    void deleteById(Integer integer);
}
