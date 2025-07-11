package brifu.puckdle.model;

import lombok.Data;

@Data
public class Player {

    private int id;
    private String fullName;
    private Integer age;
    private Integer jerseyNumber;
    private String teamAbbreviation;
    private String division;
    private String positionCode;
    private String shoots;
    private String nationality;

    // Constructor
    public Player(int id, String fullName, Integer age, Integer jerseyNumber, String teamAbbr,
                  String division, String positionCode, String shoots, String nationality) {
        this.id = id;
        this.fullName = fullName;
        this.age = age;
        this.jerseyNumber = jerseyNumber;
        this.teamAbbreviation = getTeamAbbreviation();
        this.division = division;
        this.positionCode = positionCode;
        this.shoots = shoots;
        this.nationality = nationality;
    }
}
