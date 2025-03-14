package footballfantasy.demo.playerTests;

import footballfantasy.demo.player.PlayerMapper;
import footballfantasy.demo.player.api.rest.PlayerCreateRequest;
import footballfantasy.demo.player.api.rest.PlayerResponse;
import footballfantasy.demo.player.api.rest.PlayerUpdateRequest;
import footballfantasy.demo.player.model.Position;
import footballfantasy.demo.player.service.PlayerCreateDto;
import footballfantasy.demo.player.service.PlayerFetchDto;
import footballfantasy.demo.player.service.PlayerUpdateDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerMapperTest {

    @Test
    void testMapPlayerFetchDtoToPlayerResponse() {
        PlayerFetchDto fetchDto = new PlayerFetchDto(
                1L, "Luka Modric", Position.ST, "Real Madrid", 10000.0, 50, 1L);

        PlayerResponse response = new PlayerMapper().map(fetchDto);

        assertEquals(fetchDto.id(), response.id());
        assertEquals(fetchDto.name(), response.name());
        assertEquals(fetchDto.position(), response.position());
        assertEquals(fetchDto.teamName(), response.teamName());
        assertEquals(fetchDto.price(), response.price());
        assertEquals(fetchDto.points(), response.points());
    }

    @Test
    void testMapPlayerCreateRequestToPlayerCreateDto() {

        PlayerCreateRequest request = new PlayerCreateRequest(
                 "Luka Modric", Position.ST, "Real Madrid", 10000.0, 50);


        PlayerCreateDto createDto = PlayerMapper.map(request);


        assertEquals(request.name(), createDto.name());
        assertEquals(request.position(), createDto.position());
        assertEquals(request.teamName(), createDto.teamName());
        assertEquals(request.price(), createDto.price());
        assertEquals(request.points(), createDto.points());
    }

    @Test
    void testMapPlayerUpdateRequestToPlayerUpdateDto() {

        PlayerUpdateRequest request = new PlayerUpdateRequest(
                "Luka Modric", Position.ST, "Real Madrid", 10000.0, 50, 1L);


        PlayerUpdateDto updateDto = PlayerMapper.map(request);


        assertEquals(request.name(), updateDto.name());
        assertEquals(request.position(), updateDto.position());
        assertEquals(request.teamName(), updateDto.teamName());
        assertEquals(request.price(), updateDto.price());
        assertEquals(request.points(), updateDto.points());
    }
}

