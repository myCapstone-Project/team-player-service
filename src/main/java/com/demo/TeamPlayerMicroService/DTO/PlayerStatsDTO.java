package com.demo.TeamPlayerMicroService.DTO;

import lombok.Data;

@Data
public class PlayerStatsDTO {
    private int playerId;
    private int runs;
    private int wickets;
    private int catches;
    private int ballsFacedByBatsman;
    private int runsConcededByBowler;
}