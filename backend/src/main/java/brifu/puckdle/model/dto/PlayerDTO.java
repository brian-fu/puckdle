package brifu.puckdle.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PlayerDTO {

    @JsonAlias({"id", "playerId"})
    private int playerId;

    @JsonProperty("currentTeamId")
    private int currentTeamId;

    @JsonProperty("firstName")
    private FirstName firstName;

    @JsonProperty("lastName")
    private LastName lastName;

    @JsonProperty("sweaterNumber")
    private int sweaterNumber;

    @JsonAlias({"positionCode", "position"})
    private String position;

    @JsonProperty("birthDate")
    private String birthDate;

    @JsonProperty("birthStateProvince")
    private BirthStateProvince birthStateProvince;

    @JsonProperty("birthCountry")
    private String birthCountry;

    @JsonProperty("shootsCatches")
    private String shootsCatches;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FirstName {
        @JsonProperty("default")
        private String firstName;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LastName {
        @JsonProperty("default")
        private String lastName;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BirthStateProvince {
        @JsonProperty("default")
        private String BirthStateProvince;
    }
}
