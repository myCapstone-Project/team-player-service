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
//
//@Service
//public class TeamPlayerService {
//
//    @Autowired
//    private TeamPlayerRepository teamPlayerRepository;
//
//    @Autowired
//    private WebClient.Builder webClientBuilder;
//
//    @Value("${team.service.url}")
//    private String teamServiceUrl;
//
//    @Value("${player.service.url}")
//    private String playerServiceUrl;
//
//    // Helper classes for response parsing
//    @Data
//    private static class TeamResponse {
//        private Long id;
//        private String name;
//    }
//
//    @Data
//    private static class PlayerResponse {
//        private Long id;
//        private String name;
//        private Integer age;
//        private String role;
//        private String country;
//    }
//
//    public List<TeamPlayerResponseDTO> getAllTeamPlayers() {
//        List<TeamPlayer> teamPlayers = teamPlayerRepository.findAll();
//        return teamPlayers.stream().map(this::convertToResponseDTO).collect(Collectors.toList());
//    }
//
//    public TeamPlayerResponseDTO getTeamPlayerById(Long id) {
//        TeamPlayer teamPlayer = teamPlayerRepository.findById(id).orElse(null);
//        return teamPlayer != null ? convertToResponseDTO(teamPlayer) : null;
//    }
//
//    public TeamPlayerResponseDTO createTeamPlayer(TeamPlayerRequestDTO requestDTO) {
//        TeamPlayer teamPlayer = new TeamPlayer();
//        teamPlayer.setTeamId(requestDTO.getTeamId());
//        teamPlayer.setPlayerId(requestDTO.getPlayerId());
//        teamPlayer.setPlayerRuns(requestDTO.getPlayerRuns());
//        teamPlayer.setPlayerWickets(requestDTO.getPlayerWickets());
//        teamPlayer.setPlayerCatches(requestDTO.getPlayerCatches());
//        teamPlayer.setBallsFacedByBatsman(requestDTO.getBallsFacedByBatsman());
//        teamPlayer.setRunsConcededByBowler(requestDTO.getRunsConcededByBowler());
//        TeamPlayer savedTeamPlayer = teamPlayerRepository.save(teamPlayer);
//        return convertToResponseDTO(savedTeamPlayer);
//    }
//
//    public TeamPlayerResponseDTO updateTeamPlayer(Long id, TeamPlayerRequestDTO requestDTO) {
//        TeamPlayer teamPlayer = teamPlayerRepository.findById(id).orElse(null);
//        if (teamPlayer != null) {
//            teamPlayer.setTeamId(requestDTO.getTeamId());
//            teamPlayer.setPlayerId(requestDTO.getPlayerId());
//            teamPlayer.setPlayerRuns(requestDTO.getPlayerRuns());
//            teamPlayer.setPlayerWickets(requestDTO.getPlayerWickets());
//            teamPlayer.setPlayerCatches(requestDTO.getPlayerCatches());
//            teamPlayer.setBallsFacedByBatsman(requestDTO.getBallsFacedByBatsman());
//            teamPlayer.setRunsConcededByBowler(requestDTO.getRunsConcededByBowler());
//            TeamPlayer updatedTeamPlayer = teamPlayerRepository.save(teamPlayer);
//            return convertToResponseDTO(updatedTeamPlayer);
//        }
//        return null;
//    }
//
//    public void deleteTeamPlayer(Long id) {
//        teamPlayerRepository.deleteById(id);
//    }
//
//    public TeamPlayer findByPlayerId(Long playerId) {
//        return teamPlayerRepository.findByPlayerId(playerId);
//    }
//
//    private String getTeamName(Long teamId) {
//        try {
//            TeamResponse teamResponse = webClientBuilder.build()
//                    .get()
//                    .uri(teamServiceUrl + "/" + teamId)
//                    .retrieve()
//                    .bodyToMono(TeamResponse.class)
//                    .block();
//            return teamResponse != null ? teamResponse.getName() : "Team Not Found";
//        } catch (Exception e) {
//            System.err.println("Error fetching team: " + e.getMessage());
//            return "Team Not Found";
//        }
//    }
//
//    private String getPlayerName(Long playerId) {
//        try {
//            PlayerResponse playerResponse = webClientBuilder.build()
//                    .get()
//                    .uri(playerServiceUrl + "/" + playerId)
//                    .retrieve()
//                    .bodyToMono(PlayerResponse.class)
//                    .block();
//            return playerResponse != null ? playerResponse.getName() : "Player Not Found";
//        } catch (Exception e) {
//            System.err.println("Error fetching player: " + e.getMessage());
//            return "Player Not Found";
//        }
//    }
//
//    private TeamPlayerResponseDTO convertToResponseDTO(TeamPlayer teamPlayer) {
//        TeamPlayerResponseDTO responseDTO = new TeamPlayerResponseDTO();
//        responseDTO.setId(teamPlayer.getId());
//        responseDTO.setTeamId(teamPlayer.getTeamId());
//        responseDTO.setTeamName(getTeamName(teamPlayer.getTeamId()));
//        responseDTO.setPlayerId(teamPlayer.getPlayerId());
//        responseDTO.setPlayerName(getPlayerName(teamPlayer.getPlayerId()));
//        responseDTO.setPlayerRuns(teamPlayer.getPlayerRuns());
//        responseDTO.setPlayerWickets(teamPlayer.getPlayerWickets());
//        responseDTO.setPlayerCatches(teamPlayer.getPlayerCatches());
//        responseDTO.setBallsFacedByBatsman(teamPlayer.getBallsFacedByBatsman());
//        responseDTO.setRunsConcededByBowler(teamPlayer.getRunsConcededByBowler());
//        return responseDTO;
//    }
//}

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

    public String getPlayerName(Long playerId) {
        try {
            log.info("Fetching player details for playerId: {}", playerId);

            // Use the correct URL format with direct URL
            String fullUrl = String.format("http://localhost:1818/api/players/%d", playerId);
            log.info("Making request to player service: {}", fullUrl);

            ResponseEntity<PlayerResponseDTO> response = webClientBuilder.build()
                    .get()
                    .uri(fullUrl)
                    .retrieve()
                    .toEntity(PlayerResponseDTO.class)
                    .block();

            if (response != null && response.getBody() != null && response.getBody().getName() != null) {
                String playerName = response.getBody().getName();
                log.info("Successfully found player: {}", playerName);
                return playerName;
            }

            log.warn("No player found for id: {}", playerId);
            return "Player Not Found";

        } catch (WebClientResponseException.NotFound e) {
            log.warn("Player not found with id: {}", playerId);
            return "Player Not Found";
        } catch (Exception e) {
            log.error("Error fetching player name for playerId {}: {}", playerId, e.getMessage(), e);
            return "Player Not Found";
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
}

    // Make sure PlayerResponse matches exactly with Player service response


