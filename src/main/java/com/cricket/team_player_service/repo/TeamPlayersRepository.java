package com.cricket.team_player_service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cricket.team_player_service.entity.TeamPlayer;

@Repository
public interface TeamPlayersRepository extends JpaRepository<TeamPlayer, Integer> {
	
	
	

	List<TeamPlayer> findByPlayerId(int playerId);
}