package footballfantasy.demo.player.service;

import footballfantasy.demo.player.model.Position;

public record PlayerUpdateDto(String name, Position position, Double price, Integer points, Long version) {
}
