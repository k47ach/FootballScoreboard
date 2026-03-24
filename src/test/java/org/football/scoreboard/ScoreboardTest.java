package org.football.scoreboard;

import org.junit.jupiter.api.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.football.scoreboard.ErrorMessageConstants.EMPTY_TEAM_NAME_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoreboardTest {

    private static final String MEXICO = "Mexico";
    private static final String CANADA = "Canada";
    private static final String SPAIN = "Spain";
    private static final String BRAZIL = "Brazil";

    @Test
    void shouldStartGamesCorrectly() {
        Scoreboard scoreboard = new Scoreboard();

        scoreboard.startGame(MEXICO, CANADA);
        scoreboard.startGame(SPAIN, BRAZIL);

        List<Match> activeMatches = scoreboard.getActiveMatches();

        assertEquals(2, activeMatches.size());
        assertEquals(MEXICO, activeMatches.get(0).getHomeTeam());
        assertEquals(CANADA, activeMatches.get(0).getAwayTeam());
        assertEquals(SPAIN, activeMatches.get(1).getHomeTeam());
        assertEquals(BRAZIL, activeMatches.get(1).getAwayTeam());
    }

    @Test
    void shouldStartGameWithDefaultScore() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startGame(MEXICO, CANADA);

        List<Match> activeMatches = scoreboard.getActiveMatches();

        assertEquals(1, activeMatches.size());
        assertEquals(0, activeMatches.get(0).getHomeTeamScore());
        assertEquals(0, activeMatches.get(0).getAwayTeamScore());
    }

    @Test
    void shouldNotStartGameIfTeamNameIsEmpty() {
        Scoreboard scoreboard = new Scoreboard();

        Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                scoreboard.startGame(MEXICO, "")
        );
        Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                scoreboard.startGame(MEXICO, null)
        );
        Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                scoreboard.startGame("", CANADA)
        );
        Exception exception4 = assertThrows(IllegalArgumentException.class, () ->
                scoreboard.startGame(null, CANADA)
        );

        List<Match> activeMatches = scoreboard.getActiveMatches();
        assertTrue(activeMatches.isEmpty());
        assertEquals(EMPTY_TEAM_NAME_ERROR_MESSAGE, exception1.getMessage());
        assertEquals(EMPTY_TEAM_NAME_ERROR_MESSAGE, exception2.getMessage());
        assertEquals(EMPTY_TEAM_NAME_ERROR_MESSAGE, exception3.getMessage());
        assertEquals(EMPTY_TEAM_NAME_ERROR_MESSAGE, exception4.getMessage());
    }
}
