package com.demo.TeamPlayerMicroService.Repository;

import com.demo.TeamPlayerMicroService.Entity.TeamPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamPlayersRepository extends JpaRepository<TeamPlayer, Integer> {
}