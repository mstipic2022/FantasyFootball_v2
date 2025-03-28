package footballfantasy.demo.player.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    private String teamName;
    private Double price;
    private Integer points;

    @Version
    private Long version;

    public Player(String name, Position position, String teamName, Double price, Integer points){
        this.name = name;
        this.position = position;
        this.teamName = teamName;
        this.price = price;
        this.points = points;
    }

}

