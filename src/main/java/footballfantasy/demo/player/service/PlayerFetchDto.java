package footballfantasy.demo.player.service;

import footballfantasy.demo.player.model.Position;

public record PlayerFetchDto(Long id, String name, Position position, Double price,
                             Integer points, Long version) {
}
