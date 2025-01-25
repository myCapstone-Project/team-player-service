package com.demo.TeamPlayerMicroService.DTO;

import lombok.Data;

import java.util.List;

@Data
public class TeamWithPlayersDTO {
    private Long id;
    private String name;
    private Long managerId;
    private Long captainId;
    private List<TeamPlayerDTO> players;
}