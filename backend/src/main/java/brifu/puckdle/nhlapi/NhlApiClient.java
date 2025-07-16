package brifu.puckdle.nhlapi;

import brifu.puckdle.model.dto.PlayerApiDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NhlApiClient {

    private static final String BASE_URL = "https://api-web.nhle.com";

    private final RestTemplate restTemplate;

    @Autowired
    public NhlApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PlayerApiDTO getPlayerById(int playerId) {
        String url =  BASE_URL + "/v1/player/" + playerId + "/landing";
        return restTemplate.getForObject(url, PlayerApiDTO.class);

    }
}

