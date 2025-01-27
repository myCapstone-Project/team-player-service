package com.demo.TeamPlayerMicroService.Service;

import com.demo.TeamPlayerMicroService.DTO.TeamPlayerDTO;
import com.demo.TeamPlayerMicroService.DTO.TeamWithPlayersDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.demo.TeamPlayerMicroService.DTO.TeamPlayerRequestDTO;
import com.demo.TeamPlayerMicroService.DTO.TeamPlayerResponseDTO;
import com.demo.TeamPlayerMicroService.Entity.TeamPlayer;
import com.demo.TeamPlayerMicroService.Repository.TeamPlayerRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
public class TeamPlayerService {

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${team.service.url}")
    private String teamServiceUrl;

    @Value("${player.service.url}")
    private String playerServiceUrl;

    // Helper classes for response parsing
    @Data
    private static class TeamResponse {
        private Long id;
        private String name;
        private Long managerId;
        private Long captainId;
    }

    @Data
    private static class PlayerResponse {
        private Long id;
        private String name;
        private Integer age;
        private String role;
        private String country;
    }

    public List<TeamPlayerResponseDTO> getAllTeamPlayers() {
        try {
            List<TeamPlayer> teamPlayers = teamPlayerRepository.findAll();
            return teamPlayers.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching all team players: {}", e.getMessage());
            throw new RuntimeException("Error fetching all team players", e);
        }
    }

    public TeamPlayerResponseDTO getTeamPlayerById(Long id) {
        try {
            TeamPlayer teamPlayer = teamPlayerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("TeamPlayer not found with id: " + id));
            return convertToResponseDTO(teamPlayer);
        } catch (Exception e) {
            log.error("Error fetching team player with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Error fetching team player", e);
        }
    }

    public List<TeamPlayerResponseDTO> getPlayersByTeamId(Long teamId) {
        try {
            List<TeamPlayer> teamPlayers = teamPlayerRepository.findByTeamId(teamId);
            return teamPlayers.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching players for team {}: {}", teamId, e.getMessage());
            throw new RuntimeException("Error fetching players for team", e);
        }
    }

    public TeamPlayerResponseDTO createTeamPlayer(TeamPlayerRequestDTO requestDTO) {
        try {
            // Verify team exists
            String teamName = getTeamName(requestDTO.getTeamId());
            if ("Team Not Found".equals(teamName)) {
                throw new RuntimeException("Team not found with id: " + requestDTO.getTeamId());
            }

            TeamPlayer teamPlayer = new TeamPlayer();
            teamPlayer.setTeamId(requestDTO.getTeamId());
            teamPlayer.setPlayerId(requestDTO.getPlayerId());
            teamPlayer.setPlayerRuns(requestDTO.getPlayerRuns());
            teamPlayer.setPlayerWickets(requestDTO.getPlayerWickets());
            teamPlayer.setPlayerCatches(requestDTO.getPlayerCatches());
            teamPlayer.setBallsFacedByBatsman(requestDTO.getBallsFacedByBatsman());
            teamPlayer.setRunsConcededByBowler(requestDTO.getRunsConcededByBowler());

            TeamPlayer savedTeamPlayer = teamPlayerRepository.save(teamPlayer);
            return convertToResponseDTO(savedTeamPlayer);
        } catch (Exception e) {
            log.error("Error creating team player: {}", e.getMessage());
            throw new RuntimeException("Error creating team player", e);
        }
    }

    public TeamPlayerResponseDTO updateTeamPlayer(Long id, TeamPlayerRequestDTO requestDTO) {
        try {
            TeamPlayer teamPlayer = teamPlayerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("TeamPlayer not found with id: " + id));

            // Verify team exists
            String teamName = getTeamName(requestDTO.getTeamId());
            if ("Team Not Found".equals(teamName)) {
                throw new RuntimeException("Team not found with id: " + requestDTO.getTeamId());
            }

            teamPlayer.setTeamId(requestDTO.getTeamId());
            teamPlayer.setPlayerId(requestDTO.getPlayerId());
            teamPlayer.setPlayerRuns(requestDTO.getPlayerRuns());
            teamPlayer.setPlayerWickets(requestDTO.getPlayerWickets());
            teamPlayer.setPlayerCatches(requestDTO.getPlayerCatches());
            teamPlayer.setBallsFacedByBatsman(requestDTO.getBallsFacedByBatsman());
            teamPlayer.setRunsConcededByBowler(requestDTO.getRunsConcededByBowler());

            TeamPlayer updatedTeamPlayer = teamPlayerRepository.save(teamPlayer);
            return convertToResponseDTO(updatedTeamPlayer);
        } catch (Exception e) {
            log.error("Error updating team player with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Error updating team player", e);
        }
    }

    public void deleteTeamPlayer(Long id) {
        try {
            teamPlayerRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error deleting team player with id {}: {}", id, e.getMessage());
            throw new RuntimeException("Error deleting team player", e);
        }
    }

    public TeamPlayer findByPlayerId(Long playerId) {
        try {
            return teamPlayerRepository.findByPlayerId(playerId);
        } catch (Exception e) {
            log.error("Error finding team player by playerId {}: {}", playerId, e.getMessage());
            throw new RuntimeException("Error finding team player by playerId", e);
        }
    }





    private TeamPlayerResponseDTO convertToResponseDTO(TeamPlayer teamPlayer) {
        TeamPlayerResponseDTO responseDTO = new TeamPlayerResponseDTO();
        responseDTO.setId(teamPlayer.getId());
        responseDTO.setTeamId(teamPlayer.getTeamId());
        responseDTO.setTeamName(getTeamName(teamPlayer.getTeamId()));
        responseDTO.setPlayerId(teamPlayer.getPlayerId());
        responseDTO.setPlayerName(getPlayerName(teamPlayer.getPlayerId()));
        responseDTO.setPlayerRuns(teamPlayer.getPlayerRuns());
        responseDTO.setPlayerWickets(teamPlayer.getPlayerWickets());
        responseDTO.setPlayerCatches(teamPlayer.getPlayerCatches());
        responseDTO.setBallsFacedByBatsman(teamPlayer.getBallsFacedByBatsman());
        responseDTO.setRunsConcededByBowler(teamPlayer.getRunsConcededByBowler());
        return responseDTO;
    }

    public List<TeamPlayerResponseDTO> getPlayersByTeamName(String teamName) {
        try {
            log.info("Fetching players for team name: {}", teamName);

            // First, get the team ID from team service
            Long teamId = getTeamIdByName(teamName);

            if (teamId == null) {
                log.warn("No team found with name: {}", teamName);
                return Collections.emptyList();
            }

            // Then get all players for that team
            List<TeamPlayer> teamPlayers = teamPlayerRepository.findByTeamId(teamId);

            return teamPlayers.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error fetching players for team name {}: {}", teamName, e.getMessage());
            throw new RuntimeException("Error fetching players by team name", e);
        }
    }

    private Long getTeamIdByName(String teamName) {
        try {
            TeamResponse teamResponse = webClientBuilder.build()
                    .get()
                    .uri(teamServiceUrl + "/search?name=" + teamName)
                    .retrieve()
                    .bodyToMono(TeamResponse.class)
                    .block();

            return teamResponse != null ? teamResponse.getId() : null;
        } catch (Exception e) {
            log.error("Error fetching team ID for name {}: {}", teamName, e.getMessage());
            return null;
        }
    }


    // Update PlayerResponseDTO to match exactly with Player service response
    @Data
    private static class PlayerResponseDTO {
        private Long id;
        private String name;
        private Integer age;
        private String role;
        private String country;
    }

    private void validateTeamPlayerRequest(TeamPlayerRequestDTO requestDTO) {
        if (requestDTO == null) {
            throw new RuntimeException("Request cannot be null");
        }
        if (requestDTO.getTeamId() == null) {
            throw new RuntimeException("Team ID is required");
        }
        if (requestDTO.getPlayerId() == null) {
            throw new RuntimeException("Player ID is required");
        }

        // Verify team exists
        String teamName = getTeamName(requestDTO.getTeamId());
        if ("Team Not Found".equals(teamName)) {
            throw new RuntimeException("Team not found with ID: " + requestDTO.getTeamId());
        }

        // Verify player exists
        String playerName = getPlayerName(requestDTO.getPlayerId());
        if ("Player Not Found".equals(playerName)) {
            throw new RuntimeException("Player not found with ID: " + requestDTO.getPlayerId());
        }
    }
    private String getTeamName(Long teamId) {
        try {
            log.info("Fetching team details for teamId: {}", teamId);

            // Fix the URL construction
            String url = teamServiceUrl + "/" + teamId;
            log.debug("Making request to: {}", url);

            return webClientBuilder.build()
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(TeamResponse.class)
                    .map(team -> {
                        if (team != null && team.getName() != null) {
                            log.info("Found team name: {}", team.getName());
                            return team.getName();
                        }
                        log.warn("Team response was null or had no name");
                        return "Team Not Found";
                    })
                    .onErrorResume(WebClientResponseException.class, e -> {
                        log.error("Error fetching team: {} - {}", e.getStatusCode(), e.getMessage());
                        return Mono.just("Team Not Found");
                    })
                    .block();

        } catch (Exception e) {
            log.error("Error fetching team name for teamId {}: {}", teamId, e.getMessage());
            return "Team Not Found";
        }
    }

    // Update TeamResponse class to match Team service response
    @Data
    private static class TeamResponseDTO {
        private Long id;
        private String name;
        private Long managerId;
        private Long captainId;
    }
    public String getPlayerName(Long playerId) {
        try {
            log.info("Fetching player name for ID: {}", playerId);
            String url = playerServiceUrl + "/" + playerId;
            log.debug("Making request to: {}", url);

            PlayerResponseDTO response = webClientBuilder.build()
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(PlayerResponseDTO.class)
                    .block();

            if (response != null && response.getName() != null) {
                log.info("Found player name: {} for ID: {}", response.getName(), playerId);
                return response.getName();
            } else {
                log.warn("No player name found for ID: {}", playerId);
                return "Unknown Player";
            }
        } catch (WebClientResponseException.NotFound e) {
            log.warn("Player not found with ID: {}", playerId);
            return "Unknown Player";
        } catch (Exception e) {
            log.error("Error fetching player name for ID {}: {}", playerId, e.getMessage());
            return "Unknown Player";
        }
    }
}

    // Make sure PlayerResponse matches exactly with Player service response


