package brifu.puckdle.nhlapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PlayerApiDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("currentAge")
    private int age;

    @JsonProperty("currentTeam")
    private Team team;

    @JsonProperty("primaryPosition")
    private Position position;

    @JsonProperty("shootsCatches")
    private String shoots;

    @JsonProperty("birthCountry")
    private String nationality;

    @Data
    public static class Team {
        @JsonProperty("id")
        private int id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("abbreviation")
        private String abbreviation;
        // Getters and Setters
    }

    @Data
    public static class Position {
        @JsonProperty
        public String code;
    }
}
