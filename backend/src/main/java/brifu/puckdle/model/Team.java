package brifu.puckdle.model;

import lombok.Data;
import java.util.HashMap;

@Data
public class Team {
    private int id;
    private Integer franchiseId; // nullable
    private String fullName;
    private int leagueId;
    private String triCode;
    private HashMap<Integer, Player> forwards; // Key: Player ID, Value: Player object
    private HashMap<Integer, Player> defensemen; // Key: Player ID, Value: Player object
    private HashMap<Integer, Player> goalies; // Key: Player ID, Value: Player object

    public Team(int id, Integer franchiseId, String fullName, int leagueId, String triCode, HashMap<Integer, Player> forwards, HashMap<Integer, Player> defensemen, HashMap<Integer, Player> goalies) {
        this.id = id;
        this.franchiseId = franchiseId;
        this.fullName = fullName;
        this.leagueId = leagueId;
        this.triCode = triCode;
        this.forwards = forwards;
        this.defensemen = defensemen;
        this.goalies = goalies;
    }
}
