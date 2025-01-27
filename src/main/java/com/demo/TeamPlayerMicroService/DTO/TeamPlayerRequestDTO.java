package com.demo.TeamPlayerMicroService.DTO;

import lombok.Data;

@Data
public class TeamPlayerRequestDTO {
    private int teamId; // Refers to Team ID
    private int playerId; // Refers to Player ID
    private int playerRuns;
    private int playerWickets;
    private int playerCatches;
    private int ballsFacedByBatsman;
    private int runsConcededByBowler;
}
