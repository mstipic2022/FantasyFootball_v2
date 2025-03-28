package footballfantasy.demo.team.model;

import footballfantasy.demo.player.model.Player;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teams")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Team name is required")
    private String name;

    @NotBlank(message = "Abbreviation is required")
    private String abbreviation;

    private String stadium;

    @Version
    private Long version;

    @ManyToMany (mappedBy = "teams")
    private Set<Player> players = new HashSet<>();

    public Team(String name, String abbreviation, String stadium) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.stadium = stadium;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        player.getTeams().add(this);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
        player.getTeams().remove(this);
    }
}

