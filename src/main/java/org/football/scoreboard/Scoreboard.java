package org.football.scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.football.scoreboard.ErrorMessageConstants.TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE;

public class Scoreboard {
    private final List<Match> activeMatches = new ArrayList<>();

    public List<Match> getActiveMatches() {
        return activeMatches;
    }

    public void startGame(String homeTeam, String awayTeam) {
        Match match = new Match(homeTeam, awayTeam);

        Optional<Match> alreadyActiveMatch = activeMatches.stream()
                .filter(el -> el.getHomeTeam().equals(homeTeam)
                        || el.getHomeTeam().equals(awayTeam)
                        || el.getAwayTeam().equals(homeTeam)
                        || el.getAwayTeam().equals(awayTeam))
                        .findAny();

        if (alreadyActiveMatch.isPresent()) {
            throw new IllegalArgumentException(TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE);
        }

        activeMatches.add(match);
    }
}
