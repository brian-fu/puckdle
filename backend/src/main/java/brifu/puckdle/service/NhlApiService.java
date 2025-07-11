package brifu.puckdle.service;

import brifu.puckdle.nhlapi.NhlApiClient;
import brifu.puckdle.dto.PlayerApiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NhlApiService {
    private final NhlApiClient nhlApiClient;

    @Autowired
    public NhlApiService(NhlApiClient nhlApiClient) {
        this.nhlApiClient = nhlApiClient;
    }

    public PlayerApiDTO getPlayerById(int playerId) {
        return nhlApiClient.getPlayerById(playerId);
    }
}
