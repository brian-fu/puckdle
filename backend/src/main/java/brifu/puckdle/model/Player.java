package brifu.puckdle.model;

import lombok.Data;

// Used to calculate age
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Period;

@Data
public class Player {

    private int id;
    private int currentTeamId;
    private int jerseyNumber;
    private int age;
    private boolean isActive;

    private String firstName;
    private String lastName;
    private String position;

    private String birthStateProvince;
    private String birthCountry;
    private String shootsCatches;

    // Constructor
    public Player(int id, boolean isActive, int currentTeamId, String firstName, String lastName,
                  int sweaterNumber, String position, String birthDate, String birthCity,
                  String birthStateProvince, String birthCountry, String shootsCatches) {
        this.id = id;
        this.currentTeamId = currentTeamId;
        this.jerseyNumber = sweaterNumber;
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
