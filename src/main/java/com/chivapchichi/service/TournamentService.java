package com.chivapchichi.service;

import com.chivapchichi.model.Record;
import com.chivapchichi.model.Tournament;
import com.chivapchichi.repository.RecordRepository;
import com.chivapchichi.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TournamentService {

    private final RecordRepository recordRepository;

    private final TournamentRepository tournamentRepository;

    @Autowired
    public TournamentService(RecordRepository recordRepository, TournamentRepository tournamentRepository) {
        this.recordRepository = recordRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public boolean register(String userName, Integer id) {
        if (!recordRepository.findByUserNameAndTournament(userName, id).isPresent()) {
            recordRepository.saveRecord(userName, id);
            return true;
        }

        return false;
    }

    public boolean unregister(String userName, Integer id) {
        Optional<Record> record = recordRepository.findByUserNameAndTournament(userName, id);
        if (record.isPresent()) {
            recordRepository.deleteById(record.get().getId());
            return true;
        }
        return false;
    }

    public boolean isRegistered(String userName, Integer id) {
        return recordRepository.findByUserNameAndTournament(userName, id).isPresent();
    }

    public void makeRegistrationOrUnregister(String userName, Integer id) {
        if (isRegistered(userName, id)) {
            unregister(userName, id);
        } else {
            register(userName, id);
        }
    }

    public Tournament saveTournament(Calendar date, int rating, String address, String phone, String organizerName, double cost, int max, String name, String division) {
        return saveTournament(new Tournament(date, rating, address, phone, organizerName, cost, max, name, division));
    }

    public List<String> getDivisions() {
        return tournamentRepository.getDivisions();
    }

    public List<Calendar> getDates() {
        //return tournamentRepository.findAll().stream().map(Tournament::getDate).sorted().collect(Collectors.toList());
        return tournamentRepository.getDates().stream().map(d -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            return cal;
        }).collect(Collectors.toList());
    }

    public Tournament saveTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public void deleteTournament(Integer id) {
        tournamentRepository.deleteById(id);
    }

    public Optional<Tournament> getTournamentById(Integer id) {
        return tournamentRepository.findById(id);
    }

    public boolean makeRegistration(String userName, Integer id) {
        if (isRegistered(userName, id)) {
            return false;
        }
        register(userName, id);
        return true;
    }

    public boolean makeUnregistration(String userName, Integer id) {
        if (isRegistered(userName, id)) {
            unregister(userName, id);
            return true;
        }
        return false;
    }

    public List<Tournament> getByUserName(String userName) {
        return tournamentRepository.findByUserName(userName);
    }
}
