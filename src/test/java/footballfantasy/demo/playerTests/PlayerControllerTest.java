package footballfantasy.demo.playerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import footballfantasy.demo.player.PlayerMapper;
import footballfantasy.demo.player.api.rest.PlayerController;
import footballfantasy.demo.player.api.rest.PlayerCreateRequest;
import footballfantasy.demo.player.api.rest.PlayerResponse;
import footballfantasy.demo.player.api.rest.PlayerUpdateRequest;
import footballfantasy.demo.player.model.Position;
import footballfantasy.demo.player.service.PlayerCreateDto;
import footballfantasy.demo.player.service.PlayerFetchDto;
import footballfantasy.demo.player.service.PlayerService;
import footballfantasy.demo.player.service.PlayerUpdateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlayerService playerService;

    @MockitoBean
    private PlayerMapper playerMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testFetchPlayers() throws Exception {

        List<PlayerFetchDto> fetchDtos = Stream.of(
                new PlayerFetchDto(1L, "Cristiano Ronaldo", Position.ST, "Real Madrid",
                        50000.00, 45, 1L),
                new PlayerFetchDto(2L, "Luka Modric", Position.ST, "Manchester United",
                        3200.50, 70, 1L)
        ).collect(Collectors.toList());

        List<PlayerResponse> responses = fetchDtos.stream()
                .map(dto -> new PlayerResponse(dto.id(), dto.name(), dto.position(), dto.teamName(),
                        dto.price(), dto.points(), dto.version())).toList();

        when(playerService.fetchPlayers()).thenReturn(fetchDtos);
        when(playerMapper.map(any(PlayerFetchDto.class))).thenAnswer(invocation -> {
            PlayerFetchDto dto = invocation.getArgument(0);
            return new PlayerResponse(dto.id(), dto.name(), dto.position(), dto.teamName(),
                    dto.price(), dto.points(), dto.version());
        });

        mockMvc.perform(MockMvcRequestBuilders.get("/api/player/v1/players")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreatePlayerRequest() throws Exception {
        PlayerCreateRequest request = new PlayerCreateRequest("Ivan Perisic", Position.RW,
                "Bayern FC", 543.23, 60);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/player/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        ArgumentCaptor<PlayerCreateDto> captor = ArgumentCaptor.forClass(PlayerCreateDto.class);
        Mockito.verify(playerService).createPlayer(captor.capture());
    }

    @Test
    void testUpdatePlayer() throws Exception {

        PlayerUpdateRequest request = new PlayerUpdateRequest("Lionel Messi", Position.ST, "Bayern FC",
                543.23, 60, 1L);


        mockMvc.perform(MockMvcRequestBuilders.put("/api/player/v1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<PlayerUpdateDto> dtoCaptor = ArgumentCaptor.forClass(PlayerUpdateDto.class);
        Mockito.verify(playerService).updatePlayer(idCaptor.capture(), dtoCaptor.capture());

    }

    @Test
    void testDeletePlayer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/player/v1/1"))
                .andExpect(status().isOk());

        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(playerService).deletePlayer(idCaptor.capture());
    }
}
