package com.demo.TeamPlayerMicroService.Repository;

import com.demo.TeamPlayerMicroService.Entity.TeamPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long> {
}
