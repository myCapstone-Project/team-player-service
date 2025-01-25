package com.demo.TeamPlayerMicroService.Controller;

import com.demo.TeamPlayerMicroService.DTO.PlayerStatsDTO;
import com.demo.TeamPlayerMicroService.DTO.TeamPlayerRequestDTO;
import com.demo.TeamPlayerMicroService.DTO.TeamPlayerResponseDTO;
import com.demo.TeamPlayerMicroService.Entity.TeamPlayer;
import com.demo.TeamPlayerMicroService.Repository.TeamPlayerRepository;
import com.demo.TeamPlayerMicroService.Service.TeamPlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/teamplayers")
@Slf4j
public class TeamPlayerController {

    @Autowired
    private TeamPlayerService teamPlayerService;
    @Autowired

    private TeamPlayerRepository teamPlayerRepository;

    @GetMapping
    public List<TeamPlayerResponseDTO> getAllTeamPlayers() {
        return teamPlayerService.getAllTeamPlayers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamPlayerResponseDTO> getTeamPlayerById(@PathVariable Long id) {
        TeamPlayerResponseDTO responseDTO = teamPlayerService.getTeamPlayerById(id);
        return responseDTO != null ? ResponseEntity.ok(responseDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public TeamPlayerResponseDTO createTeamPlayer(@RequestBody TeamPlayerRequestDTO requestDTO) {
        return teamPlayerService.createTeamPlayer(requestDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamPlayerResponseDTO> updateTeamPlayer(@PathVariable Long id, @RequestBody TeamPlayerRequestDTO requestDTO) {
        TeamPlayerResponseDTO responseDTO = teamPlayerService.updateTeamPlayer(id, requestDTO);
        return responseDTO != null ? ResponseEntity.ok(responseDTO) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeamPlayer(@PathVariable Long id) {
        teamPlayerService.deleteTeamPlayer(id);
        return ResponseEntity.noContent().build();
    }
    // ... existing code ...

    @GetMapping("/stats/{playerId}")
    public ResponseEntity<PlayerStatsDTO> getPlayerStats(@PathVariable Long playerId) {
        TeamPlayer teamPlayer = teamPlayerService.findByPlayerId(playerId);
        if (teamPlayer == null) {
            return ResponseEntity.notFound().build();
        }

        PlayerStatsDTO stats = new PlayerStatsDTO();
        stats.setPlayerId(playerId);
        stats.setRuns(teamPlayer.getPlayerRuns());
        stats.setWickets(teamPlayer.getPlayerWickets());
        stats.setCatches(teamPlayer.getPlayerCatches());
        stats.setBallsFacedByBatsman(teamPlayer.getBallsFacedByBatsman());
        stats.setRunsConcededByBowler(teamPlayer.getRunsConcededByBowler());

        return ResponseEntity.ok(stats);
    }
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<TeamPlayerResponseDTO>> getPlayersByTeamId(@PathVariable Long teamId) {
        try {
            List<TeamPlayerResponseDTO> players = teamPlayerService.getPlayersByTeamId(teamId);
            return ResponseEntity.ok(players);
        } catch (Exception e) {
            log.error("Error getting players for team {}: {}", teamId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/team/name/{teamName}")
    public ResponseEntity<List<TeamPlayerResponseDTO>> getPlayersByTeamName(@PathVariable String teamName) {
        try {
            List<TeamPlayerResponseDTO> players = teamPlayerService.getPlayersByTeamName(teamName);
            if (!players.isEmpty()) {
                return ResponseEntity.ok(players);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error getting players by team name: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/test/player/{playerId}")
    public ResponseEntity<?> testPlayerConnection(@PathVariable Long playerId) {
        try {
            String playerName = teamPlayerService.getPlayerName(playerId);
            return ResponseEntity.ok(Map.of(
                    "playerId", playerId,
                    "playerName", playerName,
                    "status", "success"
            ));
        } catch (Exception e) {
            log.error("Error testing player connection: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", e.getMessage(),
                            "status", "failed"
                    ));
        }
    }

}
