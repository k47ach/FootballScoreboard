package org.football.scoreboard;

import java.util.ArrayList;
import java.util.Collections;
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
        String matchKey = MatchKeyProvider.provide(homeTeam, awayTeam);
        activeMatches.put(matchKey, match);
    }

    public void finishGame(String homeTeam, String awayTeam) {
        ScoreboardValidator.validateIfTeamNamesNotEmpty(homeTeam, awayTeam);
        ScoreboardValidator.validateIfMatchExists(activeMatches, homeTeam, awayTeam);

        String matchKey = MatchKeyProvider.provide(homeTeam, awayTeam);
        activeMatches.remove(matchKey);
    }

    public void updateScore(String homeTeam, int homeTeamScore, String awayTeam, int awayTeamScore) {
        ScoreboardValidator.validateIfTeamNamesNotEmpty(homeTeam, awayTeam);
        ScoreboardValidator.validateIfMatchExists(activeMatches, homeTeam, awayTeam);
        ScoreboardValidator.validateIfMatchScoreNotNegative(homeTeamScore, awayTeamScore);

        String matchKey = MatchKeyProvider.provide(homeTeam, awayTeam);
        Match match = activeMatches.get(matchKey);
        match.setHomeTeamScore(homeTeamScore);
        match.setAwayTeamScore(awayTeamScore);
    }

    public List<String> getSummary() {
        List<Match> activeMatchesList = new ArrayList<>(activeMatches.values());
        Collections.reverse(activeMatchesList);

        return activeMatchesList.stream()
                .map(Match::toString)
                .toList();
    }
}
