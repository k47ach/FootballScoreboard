package org.football.scoreboard;

import java.util.LinkedHashMap;
import java.util.List;

public class Scoreboard {
    private final LinkedHashMap<String, Match> activeMatches = new LinkedHashMap<>();

    public List<Match> getActiveMatches() {
        return activeMatches.values()
                .stream()
                .toList();
    }

    public void startGame(String homeTeam, String awayTeam) {
        ScoreboardValidator.validateIfTeamNamesNotEmpty(homeTeam, awayTeam);
        ScoreboardValidator.validateIfTeamAlreadyAssigned(activeMatches, homeTeam, awayTeam);

        Match match = new Match(homeTeam, awayTeam);


        activeMatches.put(homeTeam + " - " + awayTeam, match);
    }

    public void finishGame(String homeTeam, String awayTeam) {
        ScoreboardValidator.validateIfTeamNamesNotEmpty(homeTeam, awayTeam);
        ScoreboardValidator.validateIfMatchExists(activeMatches, homeTeam, awayTeam);

        activeMatches.remove(homeTeam + " - " + awayTeam);
    }
}
