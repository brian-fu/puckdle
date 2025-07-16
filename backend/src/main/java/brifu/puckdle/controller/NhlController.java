package brifu.puckdle.controller;


import brifu.puckdle.model.Player;
import brifu.puckdle.service.NhlApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nhl")
public class NhlController {

    private final NhlApiService nhlApiService;

    public NhlController(NhlApiService nhlApiService) {
        this.nhlApiService = nhlApiService;
    }

    // Player Related Endpoints
    @GetMapping("/player/{playerId}")
    public ResponseEntity<Player> getPlayerById(@PathVariable int playerId) {
        Player player = nhlApiService.getPlayerById(playerId);
        if (player != null) {
            return ResponseEntity.ok(player);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}