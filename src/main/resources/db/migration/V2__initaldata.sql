BEGIN TRANSACTION;
INSERT INTO players (name, position, price, points, version) VALUES
        ('Lionel Messi', 'FW', 120.00, 50, 0),
        ('Cristiano Ronaldo', 'FW', 110.00, 48, 0),
        ('Kevin De Bruyne', 'CM', 100.00, 45, 0),
        ('Virgil van Dijk', 'CB', 90.00, 40, 0),
        ('Alisson Becker', 'GK', 85.00, 38, 0)
;

INSERT INTO teams (name, abbreviation, stadium, version) VALUES
        ('FC Barcelona', 'BAR', 'Camp Nou', 0),
        ('Manchester United', 'MANU', 'Old Trafford', 0),
        ('Liverpool FC', 'LIV', 'Anfield', 0),
        ('Real Madrid', 'RM', 'Santiago Bernabeu', 0),
        ('Paris Saint-Germain', 'PSG', 'Parc des Princes', 0);

INSERT INTO player_teams (player_id, team_id) VALUES
                                                  (1, 1),
                                                  (2, 4),
                                                  (3, 2),
                                                  (4, 3),
                                                  (5, 3)
    ON CONFLICT DO NOTHING
;
END TRANSACTION;