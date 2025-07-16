package brifu.puckdle.service;

import brifu.puckdle.nhlapi.NhlApiClient;
import brifu.puckdle.model.dto.PlayerApiDTO;
import brifu.puckdle.model.Player;
import brifu.puckdle.util.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NhlApiService {
    private final NhlApiClient nhlApiClient;

    @Autowired
    public NhlApiService(NhlApiClient nhlApiClient) {
        this.nhlApiClient = nhlApiClient;
    }

    public Player getPlayerById(int playerId) {
        try {
            return PlayerMapper.fromPlayerApiDTO(nhlApiClient.getPlayerById(playerId));
        } catch (Exception e) {
            System.err.println("Error fetching player with ID: " + playerId);
            return null;
        }
    }
}
