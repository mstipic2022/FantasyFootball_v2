CREATE TABLE IF NOT EXISTS players (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         position VARCHAR(50) NOT NULL,
                         price DECIMAL(10, 2),
                         points INTEGER,
                         version BIGINT
);

CREATE TABLE IF NOT EXISTS teams (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       abbreviation VARCHAR(4) NOT NULL,
                       stadium VARCHAR(255),
                       version BIGINT
);

CREATE TABLE IF NOT EXISTS player_teams  (
                              player_id BIGSERIAL,
                              team_id BIGSERIAL,
                              PRIMARY KEY (player_id, team_id),
                              FOREIGN KEY (player_id) REFERENCES players(id),
                              FOREIGN KEY (team_id) REFERENCES teams(id)
);
