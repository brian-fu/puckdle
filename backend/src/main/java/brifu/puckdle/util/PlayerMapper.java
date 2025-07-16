package brifu.puckdle.util;

import brifu.puckdle.model.Player;
import brifu.puckdle.model.dto.PlayerApiDTO;

// This class maps player-related data from one DTO to Player Class
public class PlayerMapper {

    // Converts a PlayerApiDTO to a Player object
    // This method is used to transform data received from the API into the internal Player model
    public static Player fromPlayerApiDTO(PlayerApiDTO dto) {
        return new Player(
            dto.getId(),
            dto.isActive(),
            dto.getCurrentTeamId(),
            dto.getFirstName(),
            dto.getLastName(),
            dto.getSweaterNumber(),
            dto.getPosition(),
            dto.getBirthDate(),
            dto.getBirthCity(),
            dto.getBirthStateProvince(),
            dto.getBirthCountry(),
            dto.getShootsCatches()
        );
    }
}
