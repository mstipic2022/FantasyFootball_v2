package footballfantasy.demo.team;

import footballfantasy.demo.team.api.rest.TeamCreateRequest;
import footballfantasy.demo.team.api.rest.TeamResponse;
import footballfantasy.demo.team.api.rest.TeamUpdateRequest;
import footballfantasy.demo.team.model.Team;
import footballfantasy.demo.team.service.TeamCreateDto;
import footballfantasy.demo.team.service.TeamFetchDto;
import footballfantasy.demo.team.service.TeamUpdateDto;
import org.springframework.stereotype.Component;

@Component
public final class TeamMapper {

    public TeamFetchDto toFetchDto(Team team) {
        return new TeamFetchDto(
                team.getId(),
                team.getName(),
                team.getAbbreviation(),
                team.getStadium(),
                team.getVersion()
        );
    }

    public TeamResponse toResponse(TeamFetchDto dto) {
        return new TeamResponse(
                dto.id(),
                dto.name(),
                dto.abbreviation(),
                dto.stadium(),
                dto.version()
        );
    }

    public TeamCreateDto toCreateDto(TeamCreateRequest request) {
        return new TeamCreateDto(
                request.name(),
                request.abbreviation(),
                request.stadium()
        );
    }

    public TeamUpdateDto toUpdateDto(TeamUpdateRequest request) {
        return new TeamUpdateDto(
                request.name(),
                request.abbreviation(),
                request.stadium(),
                request.version()
        );
    }

    public Team toEntity(TeamCreateDto dto) {
        return new Team(
                dto.name(),
                dto.abbreviation(),
                dto.stadium());
    }

    public void updateEntity(Team existing, TeamUpdateDto dto) {
        existing.setName(dto.name());
        existing.setAbbreviation(dto.abbreviation());
        existing.setStadium(dto.stadium());
        existing.setVersion(dto.version());
    }
}

