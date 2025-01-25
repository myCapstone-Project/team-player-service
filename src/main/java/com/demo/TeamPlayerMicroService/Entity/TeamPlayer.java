package com.demo.TeamPlayerMicroService.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "team_player_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_player_id")  // Changed from "id" to match database
    private Long id;

    @Column(name = "team_id", nullable = false)
    private Long teamId;

    @Column(name = "player_id", nullable = false)
    private Long playerId;

    @Column(name = "player_runs")
    private Integer playerRuns;

    @Column(name = "player_wickets")
    private Integer playerWickets;

    @Column(name = "player_catches")
    private Integer playerCatches;

    @Column(name = "balls_faced_by_batsman")
    private Integer ballsFacedByBatsman;

    @Column(name = "runs_conceded_by_bowler")
    private Integer runsConcededByBowler;
}