package brifu.puckdle.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

// DTO For getAllTeams API Call

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamListDTO {

    @JsonProperty("data")
    private List<TeamDTO> teams;

    @JsonProperty("total")
    private int total;
}