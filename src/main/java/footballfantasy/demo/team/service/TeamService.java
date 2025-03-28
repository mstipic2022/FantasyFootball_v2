package footballfantasy.demo.team.service;

import footballfantasy.demo.team.model.Team;
import footballfantasy.demo.team.repository.TeamRepository;
import footballfantasy.demo.player.service.PlayerService;
import footballfantasy.demo.team.TeamMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerService playerService;
    private final TeamMapper teamMapper;

    public List<TeamFetchDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(teamMapper::toFetchDto)
                .toList();
    }

    public TeamFetchDto getTeamById(Long id) {
        return teamRepository.findById(id)
                .map(teamMapper::toFetchDto)
                .orElseThrow(() -> new RuntimeException("Team not found"));
    }

    @Transactional
    public TeamFetchDto createTeam(TeamCreateDto createDto) {
        Team team = teamMapper.toEntity(createDto);
        Team savedTeam = teamRepository.save(team);
        return teamMapper.toFetchDto(savedTeam);
    }

    @Transactional
    public TeamFetchDto updateTeam(Long id, TeamUpdateDto updateDto) {
        Team existing = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        teamMapper.updateEntity(existing, updateDto);
        Team updated = teamRepository.save(existing);
        return teamMapper.toFetchDto(updated);
    }

    @Transactional
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }

    @Transactional
    public TeamFetchDto addPlayerToTeam(Long teamId, Long playerId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        team.addPlayer(playerService.getPlayerById(playerId));
        Team updated = teamRepository.save(team);
        return teamMapper.toFetchDto(updated);
    }

    @Transactional
    public TeamFetchDto removePlayerFromTeam(Long teamId, Long playerId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        team.removePlayer(playerService.getPlayerById(playerId));
        Team updated = teamRepository.save(team);
        return teamMapper.toFetchDto(updated);
    }
}
