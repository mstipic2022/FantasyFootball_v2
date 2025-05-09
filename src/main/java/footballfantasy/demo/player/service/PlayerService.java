package footballfantasy.demo.player.service;

import footballfantasy.demo.exception.CustomOptimisticLockException;
import footballfantasy.demo.player.model.Player;
import footballfantasy.demo.player.repository.PlayerRepository;
import footballfantasy.demo.team.repository.TeamRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public List<PlayerFetchDto> fetchPlayers() {
        return playerRepository.findAll().stream()
                .map(player -> new PlayerFetchDto(player.getId(), player.getName(), player.getPosition(),
                        player.getPrice(), player.getPoints(), player.getVersion()))
                .toList();
    }

    @Transactional
    public void createPlayer(PlayerCreateDto playerCreateDto) {
        log.info("############## Creating player: {}", playerCreateDto.position());
        Player player = new Player(playerCreateDto.name(), playerCreateDto.position(),
                playerCreateDto.price(), playerCreateDto.points());
        playerRepository.save(player);
    }

    @Transactional
    public void updatePlayer(Long id, PlayerUpdateDto playerUpdateDto) {
        try {
            Player player = playerRepository.findById(id).orElseThrow();
            player.setName(playerUpdateDto.name());
            player.setPosition(playerUpdateDto.position());
            player.setPrice(playerUpdateDto.price());
            player.setPoints(playerUpdateDto.points());
            //playerRepository.save(player); implicitno se poziva od strane spring context
        } catch (OptimisticLockException e) {
            log.error("Optimistic lock exception occurred while updating player", e);
            throw new CustomOptimisticLockException("Another user has updated the player. Please try again.", e);
        }
    }


    public void deletePlayer(Long id) {

        playerRepository.deleteById(id);
    }

    public Player getPlayerById(Long playerId) {
        return playerRepository.findById(playerId).orElseThrow();
    }
}
