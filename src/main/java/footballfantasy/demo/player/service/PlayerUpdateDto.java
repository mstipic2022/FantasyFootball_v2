package footballfantasy.demo.player.service;

import footballfantasy.demo.player.model.Position;

public record PlayerUpdateDto(String name, Position position, String teamName, Double price, Integer points, Long version) {
}
