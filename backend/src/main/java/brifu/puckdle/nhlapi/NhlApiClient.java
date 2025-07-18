package brifu.puckdle.nhlapi;

import brifu.puckdle.model.dto.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class NhlApiClient {

    private static final String BASE_URL_WEB = "https://api-web.nhle.com";
    private static final String BASE_URL_REST = "https://api.nhle.com/stats/rest";

    private final RestTemplate restTemplate;

    @Autowired
    public NhlApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Player Related API calls
    public PlayerDTO getPlayerById(int playerId) {
        String url =  BASE_URL_WEB + "/v1/player/" + playerId + "/landing";
        return restTemplate.getForObject(url, PlayerDTO.class);
    }

    // Team Related API calls
    public TeamListDTO getAllTeams() {
        String url = BASE_URL_REST + "/en/team";
        return restTemplate.getForObject(url, TeamListDTO.class);
    }

    public TeamDTO getTeamByTricode(String teamCode){
        String url = BASE_URL_WEB + "/v1/roster/" + teamCode + "/current";
        return restTemplate.getForObject(url, TeamDTO.class);
    }
}

