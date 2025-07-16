package brifu.puckdle.model;

import lombok.Data;

// Used to calculate age
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

@Data
public class Player {

    private int playerId;
    private boolean isActive;
    private int currentTeamId;
    private String firstName;
    private String lastName;

    private int jerseyNumber;
    private int age;
    private String position;

    private String birthStateProvince;
    private String birthCountry;
    private String shootsCatches;

    // Constructor
    public Player(int playerId, boolean isActive, int currentTeamId, String firstName, String lastName,
                  int jerseyNumber, String position, String birthDate,
                  String birthStateProvince, String birthCountry, String shootsCatches) {
        this.playerId = playerId;
        this.currentTeamId = currentTeamId;
        this.jerseyNumber = jerseyNumber;
        this.age = calculateAge(birthDate);
        this.isActive = isActive;

        this.firstName = firstName;
        this.lastName = lastName;

        this.position = position;
        this.birthStateProvince = birthStateProvince;
        this.birthCountry = birthCountry;
        this.shootsCatches = shootsCatches;
    }

    private int calculateAge(String birthDateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(birthDateString, formatter);
        LocalDate today = LocalDate.now();
        Period age = Period.between(birthDate, today);
        return age.getYears();
    }
}
