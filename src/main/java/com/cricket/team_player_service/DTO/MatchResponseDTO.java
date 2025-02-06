package com.cricket.team_player_service.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MatchResponseDTO {
	
	private String myTeamName;
    private String opponentTeamName;
    private LocalDate matchDate;

}
