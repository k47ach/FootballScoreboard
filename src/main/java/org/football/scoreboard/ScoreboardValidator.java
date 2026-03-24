package org.football.scoreboard;

import java.util.List;
import java.util.Optional;

import static org.football.scoreboard.ErrorMessageConstants.TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE;

public class ScoreboardValidator {

    public static void validateIfTeamAlreadyAssigned(List<Match> activeMatches, Match match) {
        String homeTeam = match.getHomeTeam();
        String awayTeam = match.getAwayTeam();

        Optional<Match> alreadyActiveMatch = activeMatches.stream()
                .filter(el -> el.getHomeTeam().equals(homeTeam)
                        || el.getHomeTeam().equals(awayTeam)
                        || el.getAwayTeam().equals(homeTeam)
                        || el.getAwayTeam().equals(awayTeam))
                .findAny();

        if (alreadyActiveMatch.isPresent()) {
            throw new IllegalArgumentException(TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE);
        }
    }
}
