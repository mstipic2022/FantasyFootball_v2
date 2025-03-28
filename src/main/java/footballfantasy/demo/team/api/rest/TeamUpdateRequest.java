package footballfantasy.demo.team.api.rest;

public record TeamUpdateRequest(String name, String abbreviation, String stadium, Long version) {
}
