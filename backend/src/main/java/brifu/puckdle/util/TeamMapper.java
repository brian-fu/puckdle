package brifu.puckdle.util;

import brifu.puckdle.model.Team;
import brifu.puckdle.model.dto.TeamDTO;

public class TeamMapper {

    // Converts a TeamDTO to a Team object
    public static Team fromTeamDTO(TeamDTO dto) {
        return new Team(
                dto.getId(),
                dto.getFranchiseId(),
                dto.getFullName(),
                dto.getLeagueId(),
                dto.getTriCode()
        );
    }
}
