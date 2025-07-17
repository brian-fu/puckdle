package brifu.puckdle.model;

import lombok.Data;

@Data
public class Team {
    private int id;
    private Integer franchiseId; // nullable
    private String fullName;
    private int leagueId;
    private String triCode;

    public Team(int id, Integer franchiseId, String fullName, int leagueId, String triCode) {
        this.id = id;
        this.franchiseId = franchiseId;
        this.fullName = fullName;
        this.leagueId = leagueId;
        this.triCode = triCode;
    }
}
