package footballfantasy.demo.team.controller;

import footballfantasy.demo.team.TeamMapper;
import footballfantasy.demo.team.api.rest.TeamCreateRequest;
import footballfantasy.demo.team.api.rest.TeamResponse;
import footballfantasy.demo.team.api.rest.TeamUpdateRequest;
import footballfantasy.demo.team.service.TeamCreateDto;
import footballfantasy.demo.team.service.TeamFetchDto;
import footballfantasy.demo.team.service.TeamService;
import footballfantasy.demo.team.service.TeamUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team/v1")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final TeamMapper teamMapper;

    @GetMapping
    public ResponseEntity<List<TeamResponse>> getAllTeams() {
        List<TeamFetchDto> dtos = teamService.getAllTeams();
        return ResponseEntity.ok(dtos.stream()
                .map(teamMapper::toResponse)
                .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeamById(@PathVariable Long id) {
        TeamFetchDto dto = teamService.getTeamById(id);
        return ResponseEntity.ok(teamMapper.toResponse(dto));
    }

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(@RequestBody TeamCreateRequest request) {
        TeamCreateDto createDto = teamMapper.toCreateDto(request);
        TeamFetchDto result = teamService.createTeam(createDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(teamMapper.toResponse(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponse> updateTeam(@PathVariable Long id,
                                                   @RequestBody TeamUpdateRequest request) {
        TeamUpdateDto updateDto = teamMapper.toUpdateDto(request);
        TeamFetchDto result = teamService.updateTeam(id, updateDto);
        return ResponseEntity.ok(teamMapper.toResponse(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<TeamResponse> addPlayerToTeam(@PathVariable Long teamId,
                                                        @PathVariable Long playerId) {
        TeamFetchDto result = teamService.addPlayerToTeam(teamId, playerId);
        return ResponseEntity.ok(teamMapper.toResponse(result));
    }

    @DeleteMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<TeamResponse> removePlayerFromTeam(@PathVariable Long teamId,
                                                             @PathVariable Long playerId) {
        TeamFetchDto result = teamService.removePlayerFromTeam(teamId, playerId);
        return ResponseEntity.ok(teamMapper.toResponse(result));
    }
}
