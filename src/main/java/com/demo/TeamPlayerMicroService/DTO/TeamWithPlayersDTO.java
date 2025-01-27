package com.demo.TeamPlayerMicroService.DTO;

import lombok.Data;

import java.util.List;

@Data
public class TeamWithPlayersDTO {
    private int id;
    private String name;
    private int managerId;
    private int captainId;
    private List<TeamPlayerDTO> players;
}