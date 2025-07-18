package brifu.puckdle.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDTO {

    @JsonProperty("id")
    private int id;

    @JsonProperty("franchiseId")
    private Integer franchiseId; // nullable

    @JsonProperty("fullName")
    private String fullName;

    @JsonProperty("leagueId")
    private int leagueId;

    @JsonProperty("triCode")
    private String triCode;
}