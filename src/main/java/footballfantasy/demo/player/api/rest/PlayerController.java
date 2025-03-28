package footballfantasy.demo.player.api.rest;

import footballfantasy.demo.player.PlayerMapper;
import footballfantasy.demo.player.service.PlayerFetchDto;
import footballfantasy.demo.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping(path = "/api/player/v1")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    @GetMapping("/players")
    public @ResponseBody List<PlayerResponse> fetchPlayers() {
        log.info("fetchPlayers request received");
        String trackingId = MDC.get("trackingId");
        //TODO why thiss???
        log.info("fetchPlayers trackingId={}", trackingId);

        List<PlayerFetchDto> fetchDtos = playerService.fetchPlayers();
        return fetchDtos.stream()
                .map(playerMapper::map)
                .toList();
    }

    @PostMapping
    public void createPlayerRequest(@RequestBody PlayerCreateRequest playerCreateRequest) {
        String trackingId = MDC.get("trackingId");
        log.info("createPlayerRequest received request={}, trackingId={} ", playerCreateRequest, trackingId);

        var playerCreateDto = PlayerMapper.map(playerCreateRequest);
        playerService.createPlayer(playerCreateDto);
    }

    @PutMapping(path = "/{id}")
    public void updatePlayer(@PathVariable Long id, @RequestBody PlayerUpdateRequest playerUpdateRequest) {
        String trackingId = MDC.get("trackingId");
        log.info("updatePlayer received id={}, request={}, trackingId={} ", id, playerUpdateRequest, trackingId);

        var playerUpdateDto = PlayerMapper.map(playerUpdateRequest);
        playerService.updatePlayer(id, playerUpdateDto);
    }

    @DeleteMapping(path = "/{id}")
    public void deletePlayer(@PathVariable Long id) {
        String trackingId = MDC.get("trackingId");
        log.info("deletePlayer received id={}, trackingId={} ", id, trackingId);
        playerService.deletePlayer(id);
    }
}

