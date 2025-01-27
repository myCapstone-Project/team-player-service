package com.demo.TeamPlayerMicroService.DTO;

import lombok.Data;

@Data
public class TeamPlayerResponseDTO {
    private int id;
    private int teamId;
    private String teamName; // Fetched from Team Microservice
    private int playerId;
    private String playerName; // Fetched from Player Microservice
    private int playerRuns;
    private int playerWickets;
    private int playerCatches;
    private int ballsFacedByBatsman;
    private int runsConcededByBowler;
}
