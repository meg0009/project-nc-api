package com.chivapchichi.controller.rest;

import com.chivapchichi.model.Record;
import com.chivapchichi.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/record")
public class RecordRestController {

    private final RecordRepository recordRepository;

    @Autowired
    public RecordRestController(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @GetMapping("/get-records")
    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    @GetMapping("/get-records/by-tournament/{id}")
    public List<Record> getByTournament(@PathVariable("id") Integer id) {
        return recordRepository.findByTournament(id);
    }

    @GetMapping("/get-records/by-tournament/{id}/main-team")
    public List<Record> getTournamentMainTeam(@PathVariable("id") Integer id) {
        return recordRepository.findByTournamentMainTeam(id);
    }

    @GetMapping("/get-records/by-tournament/{id}/reserve")
    public List<Record> getTournamentReserve(@PathVariable("id") Integer id) {
        return recordRepository.findByTournamentReserve(id);
    }

    @PostMapping("/get-records/by-tournament/{id}")
    public Optional<Record> getByUserNameAndTournament(@PathVariable("id") Integer id, @RequestBody Map<String, String> request) {
        String userName = request.get("username");
        return recordRepository.findByUserNameAndTournament(userName, id);
    }

    @GetMapping("/get-record/{id}")
    public Optional<Record> getRecordById(@PathVariable("id") Integer id) {
        return recordRepository.findById(id);
    }
}
