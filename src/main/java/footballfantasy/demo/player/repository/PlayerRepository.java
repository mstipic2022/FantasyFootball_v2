package footballfantasy.demo.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import footballfantasy.demo.player.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}

