package com.demo.TeamPlayerMicroService.DTO;

import lombok.Data;

@Data
public class TeamPlayerResponseDTO {
    private Long id;
    private Long teamId;
    private String teamName; // Fetched from Team Microservice
    private Long playerId;
    private String playerName; // Fetched from Player Microservice
    private Integer playerRuns;
    private Integer playerWickets;
    private Integer playerCatches;
    private Integer ballsFacedByBatsman;
    private Integer runsConcededByBowler;
}
