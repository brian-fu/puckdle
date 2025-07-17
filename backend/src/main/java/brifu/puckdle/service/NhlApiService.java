package brifu.puckdle.service;

import brifu.puckdle.nhlapi.NhlApiClient;
import brifu.puckdle.model.Player;
import brifu.puckdle.model.Team;
import brifu.puckdle.util.PlayerMapper;
import brifu.puckdle.util.TeamMapper;

import brifu.puckdle.model.dto.TeamListDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;

@Service
public class NhlApiService {
    private final NhlApiClient nhlApiClient;

    @Autowired
    public NhlApiService(NhlApiClient nhlApiClient) {
        this.nhlApiClient = nhlApiClient;
    }

    public Player getPlayerById(int playerId) {
        try {
            return PlayerMapper.fromPlayerDTO(nhlApiClient.getPlayerById(playerId));
        } catch (Exception e) {
            System.err.println("Error fetching player with ID: " + playerId);
            return null;
        }
    }

    public HashMap<Integer, Team> getAllTeams(){
        try {
            TeamListDTO teamListDTO = nhlApiClient.getAllTeams();

            HashMap<Integer, Team> teams = new HashMap<>();

            for (var teamDTO : teamListDTO.getTeams()) {
                Team team = TeamMapper.fromTeamDTO(teamDTO);
                teams.put(team.getId(), team);
            }
            return teams;
        }
        catch (Exception e) {
            System.err.println("Error fetching teams: " + e.getMessage());
            return null;
        }
    }


}
