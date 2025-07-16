package brifu.puckdle.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class PlayerApiDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("isActive")
    private boolean isActive;

    @JsonProperty("currentTeamId")
    private int currentTeamId;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("sweaterNumber")
    private int sweaterNumber;

    @JsonProperty("position")
    private String position;

    @JsonProperty("birthDate")
    private String birthDate;

    @JsonProperty("birthCity")
    private String birthCity;

    @JsonProperty("birthStateProvince")
    private String birthStateProvince;

    @JsonProperty("birthCountry")
    private String birthCountry;

    @JsonProperty("shootsCatches")
    private String shootsCatches;
}
