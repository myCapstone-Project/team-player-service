package com.demo.TeamPlayerMicroService.DTO;

import lombok.Data;

@Data
public class TeamPlayerRequestDTO {
    private Long teamId; // Refers to Team ID
    private Long playerId; // Refers to Player ID
    private Integer playerRuns;
    private Integer playerWickets;
    private Integer playerCatches;
    private Integer ballsFacedByBatsman;
    private Integer runsConcededByBowler;
}
