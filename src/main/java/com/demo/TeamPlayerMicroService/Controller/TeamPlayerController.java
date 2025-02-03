package com.demo.TeamPlayerMicroService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.TeamPlayerMicroService.Entity.TeamPlayer;
import com.demo.TeamPlayerMicroService.Service.TeamPlayersService;

import lombok.extern.slf4j.Slf4j;
@CrossOrigin("*")
	@RestController
	@RequestMapping("/api/team-players")
	public class TeamPlayerController {

	    @Autowired
	    private TeamPlayersService teamPlayersService;
	    

	    // Create or Update TeamPlayer
	    @PostMapping
	    public ResponseEntity<TeamPlayer> createOrUpdateTeamPlayer(@RequestBody TeamPlayer teamPlayer) {
	        TeamPlayer savedTeamPlayer = teamPlayersService.saveTeamPlayer(teamPlayer);
	        return ResponseEntity.ok(savedTeamPlayer);
	    }

		//Get all team players by teamId
		@GetMapping("/team/{teamId}")
		public ResponseEntity<List<TeamPlayer>> getAllPlayersByTeamId(@PathVariable int teamId){
			return new ResponseEntity<List<TeamPlayer>>(teamPlayersService.getAllPlayersByTeamId(teamId),HttpStatus.OK);
		}

	    // Get all TeamPlayers
	    @GetMapping
	    public ResponseEntity<List<TeamPlayer>> getAllTeamPlayers() {
	        List<TeamPlayer> teamPlayersList = teamPlayersService.getAllTeamPlayers();
	        return ResponseEntity.ok(teamPlayersList);
	    }

	    // Get a TeamPlayer by ID
	    @GetMapping("/{id}")
	    public ResponseEntity<TeamPlayer> getTeamPlayerById(@PathVariable int id) {
	        return teamPlayersService.getTeamPlayerById(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    // Delete a TeamPlayer by ID
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteTeamPlayerById(@PathVariable int id) {
	        teamPlayersService.deleteTeamPlayerById(id);
	        return ResponseEntity.noContent().build();
	    }
	}


