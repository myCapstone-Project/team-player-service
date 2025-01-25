package com.demo.TeamPlayerMicroService.DTO;

import lombok.Data;

@Data
public class TeamPlayerDTO {
    private Long id;
    private Long playerId;
    private String playerName;
    private Integer playerRuns;
    private Integer playerWickets;
    private Integer playerCatches;
    private Integer ballsFacedByBatsman;
    private Integer runsConcededByBowler;
}