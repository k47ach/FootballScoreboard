package org.football.scoreboard;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Optional;

import static org.football.scoreboard.ErrorMessageConstants.*;

public class ScoreboardValidator {

    public static void validateIfTeamAlreadyAssigned(Map<String, Match> activeMatches, String homeTeam, String awayTeam) {
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

    public static void validateIfTeamNamesNotEmpty(String homeTeam, String awayTeam) {
        if (StringUtils.isEmpty(homeTeam) || StringUtils.isEmpty(awayTeam)) {
            throw new IllegalArgumentException(EMPTY_TEAM_NAME_ERROR_MESSAGE);
        }
    }

    public static void validateIfMatchExists(Map<String, Match> activeMatches, String homeTeam, String activeTeam) {
        if (!activeMatches.containsKey(homeTeam + " - " + activeTeam)) {
            throw new IllegalArgumentException(MATCH_NOT_FOUND_ERROR_MESSAGE);
        }
    }

    private ScoreboardValidator() {
        // Object instantiation not needed for this validator class
    }
}
