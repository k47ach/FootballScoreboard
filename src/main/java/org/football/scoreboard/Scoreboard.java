package org.football.scoreboard;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private final List<Match> activeMatches = new ArrayList<>();

    public List<Match> getActiveMatches() {
        return activeMatches;
    }

    public void startGame(String homeTeam, String awayTeam) {
        Match match = new Match(homeTeam, awayTeam);

        activeMatches.add(match);
    }
}
