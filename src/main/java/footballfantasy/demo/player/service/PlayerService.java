package footballfantasy.demo.player.service;

import footballfantasy.demo.exception.CustomOptimisticLockException;
import footballfantasy.demo.player.model.Player;
import footballfantasy.demo.player.repository.PlayerRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class    PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<PlayerFetchDto> fetchPlayers() {
        return playerRepository.findAll().stream()
                .map(player -> new PlayerFetchDto(player.getId(), player.getName(), player.getPosition(),
                        player.getTeamName(), player.getPrice(), player.getPoints(), player.getVersion()))
                .toList();
    }

    public void createPlayer(PlayerCreateDto playerCreateDto) {
        Player player = new Player(playerCreateDto.name(), playerCreateDto.position(), playerCreateDto.teamName(),
                playerCreateDto.price(), playerCreateDto.points());
        playerRepository.save(player);
    }

    @Transactional
    public void updatePlayer(Long id, PlayerUpdateDto playerUpdateDto) {
        try {
            Player player = playerRepository.findById(id).orElseThrow();
            player.setName(playerUpdateDto.name());
            player.setPosition(playerUpdateDto.position());
            player.setTeamName(playerUpdateDto.teamName());
            player.setPrice(playerUpdateDto.price());
            player.setPoints(playerUpdateDto.points());
            playerRepository.save(player);
        } catch (OptimisticLockException e) {
            log.error("Optimistic lock exception occurred while updating player", e);
            throw new CustomOptimisticLockException("Another user has updated the player. Please try again.");
        }
    }


    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    public Player getPlayerById(Long playerId) {
        return playerRepository.findById(playerId).orElseThrow();
    }
}
