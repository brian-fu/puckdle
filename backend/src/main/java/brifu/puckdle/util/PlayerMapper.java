package brifu.puckdle.util;

import brifu.puckdle.model.Player;
import brifu.puckdle.model.dto.PlayerApiDTO;

public class PlayerMapper {

    // Converts a PlayerApiDTO to a Player object
    public static Player fromPlayerApiDTO(PlayerApiDTO dto) {
        return new Player(
                dto.getPlayerId(),
                dto.isActive(),
                dto.getCurrentTeamId(),
                dto.getFirstName() != null ? dto.getFirstName().getFirstName() : null,
                dto.getLastName() != null ? dto.getLastName().getLastName() : null,
                dto.getSweaterNumber(),
                dto.getPosition(),
                dto.getBirthDate(),
                dto.getBirthStateProvince() != null ? dto.getBirthStateProvince().getBirthStateProvince() : null,
                dto.getBirthCountry(),
                dto.getShootsCatches()
        );
    }
}
