package brifu.puckdle.service;

import brifu.puckdle.nhlapi.NhlApiClient;
import brifu.puckdle.model.Player;
import brifu.puckdle.model.Team;
import brifu.puckdle.util.*;

import brifu.puckdle.model.dto.*;

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

    // Player Related Methods
    /**
     * Fetches a Player by their ID and returns a Player object.
     *
     * @param playerId The ID of the player to fetch.
     * @return Player object containing player details, or null if not found.
     */
    public Player getPlayerById(int playerId) {
        try {
            return PlayerMapper.fromPlayerDTO(nhlApiClient.getPlayerById(playerId));
        } catch (Exception e) {
            System.err.println("Error fetching player with ID: " + playerId);
            return null;
        }
    }

    // Team Related Methods
    /**
     * Fetches a team's Roster by its tricode and returns a TeamRosterDTO object.
     *
     * @return TeamRosterDTO object containing team details, or null if not found.
     */
    public TeamRosterDTO getTeamRosterByTricode(String triCode) {
        try {
            TeamRosterDTO teamRosterDTO = nhlApiClient.getTeamRosterByTricode(triCode);
            if (teamRosterDTO == null) {
                System.err.println("No team found with tricode: " + triCode);
                return null;
            }
            return teamRosterDTO;
        } catch (Exception e) {
            System.err.println("Error fetching team with tricode: " + triCode + " - " + e.getMessage());
            return null;
        }
    }

    /**
     * Fetches all NHL teams and maps them to a HashMap with team ID as the key.
     *
     * @return A HashMap where the key is the team ID and the value is the Team object.
     */
    public HashMap<Integer, Team> getTeamList(){
        try {
            TeamListDTO teamListDTO = nhlApiClient.getTeamList();

            HashMap<Integer, Team> teams = new HashMap<>();

            for (TeamDTO teamDTO : teamListDTO.getTeams()) {

                try {
                    TeamRosterDTO teamRosterDTO = nhlApiClient.getTeamRosterByTricode(teamDTO.getTriCode());
                    Team team = TeamMapper.fromTeamDTO(teamDTO, teamRosterDTO);
                    teams.put(team.getId(), team);
                } catch (Exception e) {
                    System.err.println("Error fetching roster for team: " + teamDTO.getFullName() + " - " + e.getMessage());
                    continue; // Skip this team if there's an error fetching the roster
                }
            }
            return teams;
        }
        catch (Exception e) {
            System.err.println("Error fetching teams: " + e.getMessage());
            return null;
        }
    }
}
