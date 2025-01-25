package com.demo.TeamPlayerMicroService.DTO;

import lombok.Data;

@Data
public class PlayerStatsDTO {
    private Long playerId;
    private Integer runs;
    private Integer wickets;
    private Integer catches;
    private Integer ballsFacedByBatsman;
    private Integer runsConcededByBowler;
}