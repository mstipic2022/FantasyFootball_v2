package footballfantasy.demo.player;

import footballfantasy.demo.player.api.rest.PlayerCreateRequest;
import footballfantasy.demo.player.api.rest.PlayerResponse;
import footballfantasy.demo.player.api.rest.PlayerUpdateRequest;
import footballfantasy.demo.player.service.PlayerCreateDto;
import footballfantasy.demo.player.service.PlayerFetchDto;
import footballfantasy.demo.player.service.PlayerUpdateDto;
import org.springframework.stereotype.Component;

@Component
public final class PlayerMapper {
    public PlayerResponse map(PlayerFetchDto playerFetchDto) {
        return new PlayerResponse(
                playerFetchDto.id(),
                playerFetchDto.name(),
                playerFetchDto.position(),
                playerFetchDto.teamName(),
                playerFetchDto.price(),
                playerFetchDto.points(),
                playerFetchDto.version());
    }

    public static PlayerCreateDto map(PlayerCreateRequest playerCreateRequest) {
        return new PlayerCreateDto(
                playerCreateRequest.name(),
                playerCreateRequest.position(),
                playerCreateRequest.teamName(),
                playerCreateRequest.price(),
                playerCreateRequest.points());
    }

    public static PlayerUpdateDto map(PlayerUpdateRequest playerUpdateRequest) {
        return new PlayerUpdateDto(
                playerUpdateRequest.name(),
                playerUpdateRequest.position(),
                playerUpdateRequest.teamName(),
                playerUpdateRequest.price(),
                playerUpdateRequest.points(),
                playerUpdateRequest.version());
    }

}
