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

        List<PlayerFetchDto> fetchDtos = playerService.fetchPlayers();
        return fetchDtos.stream()
                .map(playerMapper::map)
                .toList();
    }

    @PostMapping
    public void createPlayerRequest(@RequestBody PlayerCreateRequest playerCreateRequest) {

        var playerCreateDto = PlayerMapper.map(playerCreateRequest);
        playerService.createPlayer(playerCreateDto);
    }

    @PutMapping(path = "/{id}")
    public void updatePlayer(@PathVariable Long id, @RequestBody PlayerUpdateRequest playerUpdateRequest) {

        var playerUpdateDto = PlayerMapper.map(playerUpdateRequest);
        playerService.updatePlayer(id, playerUpdateDto);
    }

    @DeleteMapping(path = "/{id}")
    public void deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
    }
}

