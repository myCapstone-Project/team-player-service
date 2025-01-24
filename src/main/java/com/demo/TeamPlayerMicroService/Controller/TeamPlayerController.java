package com.demo.TeamPlayerMicroService.Controller;

import com.demo.TeamPlayerMicroService.Entity.TeamPlayer;
import com.demo.TeamPlayerMicroService.Service.TeamPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team-players")
public class TeamPlayerController {

    @Autowired
    private TeamPlayerService teamPlayerService;

    // Get all team players
    @GetMapping
    public ResponseEntity<List<TeamPlayer>> getAllTeamPlayers() {
        return ResponseEntity.ok(teamPlayerService.getAllTeamPlayers());
    }

    // Get team player by ID
    @GetMapping("/{id}")
    public ResponseEntity<TeamPlayer> getTeamPlayerById(@PathVariable Long id) {
        return ResponseEntity.ok(teamPlayerService.getTeamPlayerById(id));
    }

    // Create a new team player
    @PostMapping
    public ResponseEntity<TeamPlayer> createTeamPlayer(@RequestBody TeamPlayer teamPlayer) {
        TeamPlayer createdTeamPlayer = teamPlayerService.createTeamPlayer(teamPlayer);
        return ResponseEntity.ok(createdTeamPlayer);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TeamPlayer> updateTeamPlayer(@PathVariable Long id, @RequestBody TeamPlayer updatedTeamPlayer) {
        TeamPlayer existingTeamPlayer = teamPlayerService.getTeamPlayerById(id);
        existingTeamPlayer.setTeamId(updatedTeamPlayer.getTeamId());
        existingTeamPlayer.setPlayerId(updatedTeamPlayer.getPlayerId());
        existingTeamPlayer.setPlayerRuns(updatedTeamPlayer.getPlayerRuns());
        existingTeamPlayer.setPlayerWickets(updatedTeamPlayer.getPlayerWickets());
        existingTeamPlayer.setPlayerCatches(updatedTeamPlayer.getPlayerCatches());
        existingTeamPlayer.setPlayerBallsFaced(updatedTeamPlayer.getPlayerBallsFaced());
        existingTeamPlayer.setPlayerBallsDelivered(updatedTeamPlayer.getPlayerBallsDelivered());
        TeamPlayer savedTeamPlayer = teamPlayerService.saveTeamPlayer(existingTeamPlayer);
        return ResponseEntity.ok(savedTeamPlayer);
    }

}
