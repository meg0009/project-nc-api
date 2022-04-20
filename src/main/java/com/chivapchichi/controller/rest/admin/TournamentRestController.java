package com.chivapchichi.controller.rest.admin;

import com.chivapchichi.model.Tournament;
import com.chivapchichi.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("admin/api/tournament")
public class TournamentRestController {

    private final TournamentService tournamentService;

    @Autowired
    public TournamentRestController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping("/add-tournament")
    public Tournament addTournament(@RequestBody Tournament tournament) {
        return tournamentService.saveTournament(tournament);
    }

    @DeleteMapping("/delete-tournament/{id}")
    public Map<String, Boolean> deleteTournament(@PathVariable("id") Integer id) {
        Optional<Tournament> tournament = tournamentService.getTournamentById(id);
        Map<String, Boolean> res = new HashMap<>();

        if (tournament.isPresent()) {
            tournamentService.deleteTournament(id);
            res.put("deleted", true);
        } else {
            res.put("deleted", false);
        }
        return res;
    }

    @PutMapping("/change-tournament/{id}")
    public ResponseEntity<Tournament> changeTournament(@PathVariable("id") Integer id, @RequestBody Tournament tournament) throws Exception {
        Tournament tournamentRes = tournamentService.getTournamentById(id).orElseThrow(() -> new Exception("someerror"));
        tournament.setId(tournamentRes.getId());
        tournamentService.saveTournament(tournament);
        return ResponseEntity.ok(tournament);
    }
}
