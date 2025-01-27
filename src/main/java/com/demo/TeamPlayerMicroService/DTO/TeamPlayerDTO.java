package com.demo.TeamPlayerMicroService.DTO;

import lombok.Data;

@Data
public class TeamPlayerDTO {
    private int id;
    private int playerId;
    private String playerName;
    private int playerRuns;
    private int playerWickets;
    private int playerCatches;
    private int ballsFacedByBatsman;
    private int runsConcededByBowler;
}