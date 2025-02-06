package com.cricket.team_player_service.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cricket.team_player_service.DTO.TeamDTO;
@FeignClient(name = "teams-service", url = "http://localhost:9090/teams")
public interface TeamsFeignClient {
	
	@GetMapping("/{teamId}")
    TeamDTO getTeam1ById(@PathVariable int teamId);

    @GetMapping("/booking/{bookingId}")
    List<TeamDTO> getTeamsByBookingId(@PathVariable int bookingId);

}
