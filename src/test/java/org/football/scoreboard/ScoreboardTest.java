package org.football.scoreboard;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;

class ScoreboardTest {

    @Test
    void shouldStartGamesCorrectly() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startGame("Mexico", "Canada");
        scoreboard.startGame("Spain", "Brazil");

        List<Match> activeMatches = scoreboard.getActiveMatches();

        assertEquals(2, activeMatches.size());
        assertEquals("Mexico", activeMatches.get(0).getHomeTeam());
        assertEquals("Canada", activeMatches.get(0).getAwayTeam());
        assertEquals("Spain", activeMatches.get(1).getHomeTeam());
        assertEquals("Brazil", activeMatches.get(1).getAwayTeam());
    }
}
