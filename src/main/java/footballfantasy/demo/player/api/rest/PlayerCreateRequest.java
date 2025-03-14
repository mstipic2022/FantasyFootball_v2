package footballfantasy.demo.player.api.rest;

import footballfantasy.demo.player.model.Position;

public record PlayerCreateRequest(String name, Position position, String teamName, Double price, Integer points) {
}
