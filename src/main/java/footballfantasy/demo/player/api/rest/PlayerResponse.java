package footballfantasy.demo.player.api.rest;

import footballfantasy.demo.player.model.Position;

public record PlayerResponse(Long id, String name, Position position, String teamName, Double price,
                             Integer points, Long version) {
}
