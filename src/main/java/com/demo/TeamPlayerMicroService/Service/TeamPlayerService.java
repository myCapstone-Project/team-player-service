package com.demo.TeamPlayerMicroService.Service;

import com.demo.TeamPlayerMicroService.Entity.TeamPlayer;
import com.demo.TeamPlayerMicroService.Repository.TeamPlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamPlayerService {

    private final TeamPlayerRepository teamPlayerRepository;

    public TeamPlayerService(TeamPlayerRepository teamPlayerRepository) {
        this.teamPlayerRepository = teamPlayerRepository;
    }

    public List<TeamPlayer> getAllTeamPlayers() {
        return teamPlayerRepository.findAll();
    }

    public TeamPlayer createTeamPlayer(TeamPlayer teamPlayer) {
        return teamPlayerRepository.save(teamPlayer);
    }

    public TeamPlayer getTeamPlayerById(Long id) {
        return teamPlayerRepository.findById(id).orElseThrow(() -> new RuntimeException("TeamPlayer not found!"));
    }
    public TeamPlayer saveTeamPlayer(TeamPlayer teamPlayer) {
        return teamPlayerRepository.save(teamPlayer);
    }

}
