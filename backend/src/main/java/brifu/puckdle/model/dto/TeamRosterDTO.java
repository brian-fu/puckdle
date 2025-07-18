package brifu.puckdle.model.dto;

import brifu.puckdle.model.dto.PlayerDTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamRosterDTO {
    @JsonProperty("forwards")
    private List<PlayerDTO> forwards;
    @JsonProperty("defensemen")
    private List<PlayerDTO> defensemen;
    @JsonProperty("goalies")
    private List<PlayerDTO> goalies;
}
