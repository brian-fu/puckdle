package brifu.puckdle.util;

import brifu.puckdle.model.Team;
import brifu.puckdle.model.dto.PlayerDTO;
import brifu.puckdle.model.dto.TeamDTO;
import brifu.puckdle.model.dto.TeamRosterDTO;
import brifu.puckdle.model.Player;

import java.util.List;
import java.util.HashMap;

public class TeamMapper {
    // Converts a TeamDTO to a Team object
    public static Team fromTeamDTO(TeamDTO teamDTO, TeamRosterDTO teamRosterDTO) {

        HashMap<Integer, Player> forwards = new HashMap<>();
        HashMap<Integer, Player> defensemen = new HashMap<>();
        HashMap<Integer, Player> goalies = new HashMap<>();

        for (PlayerDTO player : teamRosterDTO.getForwards()) {
            Player forward = PlayerMapper.fromPlayerDTO(player);
            forward.setCurrentTeamId(teamDTO.getId());
            forwards.put(forward.getPlayerId(), forward);
        }

        for (PlayerDTO player : teamRosterDTO.getDefensemen()) {
            Player defenseman = PlayerMapper.fromPlayerDTO(player);
            defenseman.setCurrentTeamId(teamDTO.getId());
            defensemen.put(defenseman.getPlayerId(), defenseman);
        }

        for (PlayerDTO player : teamRosterDTO.getGoalies()) {
            Player goalie = PlayerMapper.fromPlayerDTO(player);
            goalie.setCurrentTeamId(teamDTO.getId());
            goalies.put(goalie.getPlayerId(), goalie);
        }

        return new Team(
                teamDTO.getId(),
                teamDTO.getFranchiseId(),
                teamDTO.getFullName(),
                teamDTO.getLeagueId(),
                teamDTO.getTriCode(),
                forwards,
                defensemen,
                goalies
        );
    }
}
