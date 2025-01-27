package com.demo.TeamPlayerMicroService.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int id; // Primary Key

    @Column(name = "team_id", nullable = false)
    private int teamId; // Foreign Key referencing Teams.id

    @Column(name = "player_id", nullable = false)
    private int playerId; // Foreign Key referencing Players.id

    @Column(name = "player_runs")
    private int playerRuns;

    @Column(name = "player_wickets")
    private int playerWickets;

    @Column(name = "player_catches")
    private int playerCatches;

    @Column(name = "player_balls_faced")
    private int playerBallsFaced;

    @Column(name = "player_balls_delivered")
    private int playerBallsDelivered;

    @Column(name = "catches")
    private int catches;


	}
