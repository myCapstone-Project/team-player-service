package com.demo.TeamPlayerMicroService.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.TeamPlayerMicroService.Entity.TeamPlayer;
import com.demo.TeamPlayerMicroService.Repository.TeamPlayersRepository;

@Service
public class TeamPlayersService {
	
	@Autowired
    private TeamPlayersRepository teamPlayersRepository;

    // Create or Update a TeamPlayer
    public TeamPlayer saveTeamPlayer(TeamPlayer teamPlayer) {
        return teamPlayersRepository.save(teamPlayer);
    }

    // Get all TeamPlayers
    public List<TeamPlayer> getAllTeamPlayers() {
        return teamPlayersRepository.findAll();
    }

    // Get a TeamPlayer by ID
    public Optional<TeamPlayer> getTeamPlayerById(int id) {
        return teamPlayersRepository.findById(id);
    }

    // Delete a TeamPlayer by ID
    public void deleteTeamPlayerById(int id) {
        teamPlayersRepository.deleteById(id);
    }


} // Make sure PlayerResponse matches exactly with Player service response


