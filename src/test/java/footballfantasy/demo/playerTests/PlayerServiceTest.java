package footballfantasy.demo.playerTests;

import footballfantasy.demo.exception.CustomOptimisticLockException;
import footballfantasy.demo.player.model.Player;
import footballfantasy.demo.player.model.Position;
import footballfantasy.demo.player.service.PlayerService;
import footballfantasy.demo.player.repository.PlayerRepository;
import footballfantasy.demo.player.service.PlayerCreateDto;
import footballfantasy.demo.player.service.PlayerFetchDto;
import footballfantasy.demo.player.service.PlayerUpdateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void testFetchPlayers() {

        Player player1 = new Player("Luka Modric", Position.ST, "Real Madrid", 10000.0, 50);
        Player player2 = new Player("Lionel Messi", Position.CM, "FC Barcelona", 50000.50, 75);

        when(playerRepository.findAll()).thenReturn(List.of(player1, player2));


        List<PlayerFetchDto> fetchDtos = playerService.fetchPlayers();


        assertEquals(2, fetchDtos.size());
        assertEquals(player1.getId(), fetchDtos.get(0).id());
        assertEquals(player1.getName(), fetchDtos.get(0).name());
        assertEquals(player2.getId(), fetchDtos.get(1).id());
        assertEquals(player2.getName(), fetchDtos.get(1).name());
    }

    @Test
    void testCreatePlayer() {

        PlayerCreateDto createDto = new PlayerCreateDto("Luka Modric", Position.ST, "Real Madrid",
                10000.0, 50);


        playerService.createPlayer(createDto);


        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void testUpdatePlayer() {

        PlayerUpdateDto updateDto = new PlayerUpdateDto( "Luka Modric", Position.ST, "Real Madrid",
                10000.0, 50, 1L);
        Player player = new Player("Luka Modric", Position.ST, "Real Madrid", 10000.0, 50);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));


        playerService.updatePlayer(1L, updateDto);


        verify(playerRepository, times(1)).save(any(Player.class));
        assertEquals(updateDto.name(), player.getName());
    }

    @Test
    void testUpdatePlayerOptimisticLockException() {

        PlayerUpdateDto updateDto = new PlayerUpdateDto("Luka Modric", Position.ST, "Real Madrid",
                10000.0, 50, 1L);
        Player player = new Player("Luka Modric", Position.ST, "Real Madrid", 10000.0, 50);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        doThrow(new jakarta.persistence.OptimisticLockException()).when(playerRepository).save(any(Player.class));


        assertThrows(CustomOptimisticLockException.class, () -> playerService.updatePlayer(1L, updateDto));
    }

    @Test
    void testDeletePlayer() {

        playerService.deletePlayer(1L);

        verify(playerRepository, times(1)).deleteById(1L);
    }
}

