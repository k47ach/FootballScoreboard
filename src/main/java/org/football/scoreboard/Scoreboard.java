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

        ScoreboardValidator.validateIfTeamAlreadyAssigned(activeMatches, match);

        activeMatches.add(match);
    }
}
