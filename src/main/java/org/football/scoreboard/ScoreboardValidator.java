package org.football.scoreboard;

import java.util.Map;
import java.util.Optional;

import static org.football.scoreboard.ErrorMessageConstants.TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE;

public class ScoreboardValidator {

    public static void validateIfTeamAlreadyAssigned(Map<String, Match> activeMatches, Match match) {
        String homeTeam = match.getHomeTeam();
        String awayTeam = match.getAwayTeam();

        Optional<Match> alreadyActiveMatch = activeMatches.values()
                .stream()
                .filter(el -> el.getHomeTeam().equalsIgnoreCase(homeTeam)
                        || el.getHomeTeam().equalsIgnoreCase(awayTeam)
                        || el.getAwayTeam().equalsIgnoreCase(homeTeam)
                        || el.getAwayTeam().equalsIgnoreCase(awayTeam))
                .findAny();

        if (alreadyActiveMatch.isPresent()) {
            throw new IllegalArgumentException(TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE);
        }
    }
}
