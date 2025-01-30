package com.cricket.team_player_service.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cricket.team_player_service.DTO.BookingDTO;
import com.cricket.team_player_service.DTO.MatchResponseDTO;
import com.cricket.team_player_service.DTO.TeamDTO;
import com.cricket.team_player_service.entity.TeamPlayer;
import com.cricket.team_player_service.repo.TeamPlayersRepository;

@Service
public class TeamPlayersService {
	
	@Autowired
    private TeamPlayersRepository teamPlayersRepository;
	
	
	@Autowired
    private TeamsFeignClient teamsFeignClient;
	
	@Autowired
	private BookingsFeignClient bookingsFeignClient;
	
//	public List<MatchResponseDTO> getPlayerMatches(int playerId) {
//		List<TeamPlayer> teamPlayers = teamPlayersRepository.findByPlayerId(playerId);
//        List<MatchResponseDTO> matches = new ArrayList<>();
//
//        for (TeamPlayer tp : teamPlayers) {
//            TeamDTO myTeam = teamsFeignClient.getTeam1ById(tp.getTeamId());
//            int bookingId = myTeam.getBookingId();
//
//            // Fetch opponent team
//            List<TeamDTO> teamsInBooking = teamsFeignClient.getTeamsByBookingId(bookingId);
//            TeamDTO opponentTeam = teamsInBooking.stream()
//                    .filter(t -> t.getId() != myTeam.getId())
//                    .findFirst()
//                    .orElse(null);
//
//            if (opponentTeam != null) {
//                // Fetch match date using BookingsFeignClient instead of TeamsFeignClient
//                BookingDTO booking = bookingsFeignClient.getBookingById(bookingId);
//
//                matches.add(new MatchResponseDTO(
//                    myTeam.getName(),
//                    opponentTeam.getName(),
//                    booking.getBookingDate()
//                ));
//            }
//        }
//        return matches;
//    }
	public List<MatchResponseDTO> getPlayerMatches(int playerId) {
	    List<TeamPlayer> teamPlayers = teamPlayersRepository.findByPlayerId(playerId);
	    Set<MatchResponseDTO> matches = new HashSet<>(); // Use Set to remove duplicates

	    for (TeamPlayer tp : teamPlayers) {
	        TeamDTO myTeam = teamsFeignClient.getTeam1ById(tp.getTeamId());
	        int bookingId = myTeam.getBookingId();

	        // Fetch opponent team
	        List<TeamDTO> teamsInBooking = teamsFeignClient.getTeamsByBookingId(bookingId);
	        TeamDTO opponentTeam = teamsInBooking.stream()
	                .filter(t -> t.getId() != myTeam.getId())
	                .findFirst()
	                .orElse(null);

	        if (opponentTeam != null) {
	            // Fetch match date using BookingsFeignClient
	            BookingDTO booking = bookingsFeignClient.getBookingById(bookingId);

	            // Create match entry
	            MatchResponseDTO match = new MatchResponseDTO(
	                myTeam.getName(),
	                opponentTeam.getName(),
	                booking.getBookingDate()
	            );

	            matches.add(match); // Set ensures only unique matches are added
	        }
	    }
	    return new ArrayList<>(matches); // Convert Set back to List
	}

	public TeamPlayer saveTeamPlayer(TeamPlayer teamPlayer) {
        return teamPlayersRepository.save(teamPlayer);
    }
	public List<TeamPlayer> getAllTeamPlayers() {
        return teamPlayersRepository.findAll();
    }
	public Optional<TeamPlayer> getTeamPlayerById(int id) {
        return teamPlayersRepository.findById(id);
    }

    public void deleteTeamPlayerById(int id) {
        teamPlayersRepository.deleteById(id);
    }
}

