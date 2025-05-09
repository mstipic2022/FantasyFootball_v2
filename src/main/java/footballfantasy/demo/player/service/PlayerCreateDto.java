package footballfantasy.demo.player.service;

import footballfantasy.demo.player.model.Position;

public record PlayerCreateDto(String name, Position position, Double price, Integer points) {
}
