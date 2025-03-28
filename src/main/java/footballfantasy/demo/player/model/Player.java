package footballfantasy.demo.player.model;

import footballfantasy.demo.team.model.Team;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "players")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Position is required")
    @Enumerated(EnumType.STRING)
    private Position position;
    //TODO why this??
    private String teamName;
    private Double price;
    private Integer points;

    @Version
    private Long version;


    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(
            name = "player_teams",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new HashSet<>();

    public Player(String name, Position position, String teamName, Double price, Integer points){
        this.name = name;
        this.position = position;
        this.teamName = teamName;
        this.price = price;
        this.points = points;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
        team.getPlayers().add(this);
    }

    public void removeTeam(Team team) {
        this.teams.remove(team);
        team.getPlayers().remove(this);
    }

}

