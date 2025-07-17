package brifu.puckdle.util;

import brifu.puckdle.model.Player;
import brifu.puckdle.model.dto.PlayerDTO;

public class PlayerMapper {

    // Converts a PlayerApiDTO to a Player object
    public static Player fromPlayerDTO(PlayerDTO dto) {
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
