package com.demo.TeamPlayerMicroService.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team_players")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "team_id", nullable = false)
    private Long teamId;  // Refers to Team ID

    @Column(name = "player_id", nullable = false)
    private Long playerId;  // Refers to Player ID

    @Column(name = "player_runs")
    private Integer playerRuns;

    @Column(name = "player_wickets")
    private Integer playerWickets;

    @Column(name = "player_catches")
    private Integer playerCatches;

    @Column(name = "player_balls_faced")
    private Integer playerBallsFaced;

    @Column(name = "player_balls_delivered")
    private Integer playerBallsDelivered;

    @Column(name = "catches")
    private Integer catches;
}
