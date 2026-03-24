package org.football.scoreboard;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.football.scoreboard.ErrorMessageConstants.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoreboardTest {

    private static final String MEXICO = "Mexico";
    private static final String CANADA = "Canada";
    private static final String SPAIN = "Spain";
    private static final String BRAZIL = "Brazil";

    @Nested
    class StartGameTests {
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

        @Test
        void shouldNotStartGameIfTeamAlreadyActiveInAnotherGame() {
            Scoreboard scoreboard = new Scoreboard();
            scoreboard.startGame(MEXICO, CANADA);

            Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.startGame(MEXICO, BRAZIL)
            );

            Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
                scoreboard.startGame(SPAIN, CANADA);
            });

            List<Match> activeMatches = scoreboard.getActiveMatches();
            assertEquals(1, activeMatches.size());
            assertEquals(TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE, exception1.getMessage());
            assertEquals(TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE, exception2.getMessage());
        }

        @Test
        void shouldNotStartGameIfTeamAlreadyActiveInAnotherGameIgnoreCase() {
            Scoreboard scoreboard = new Scoreboard();
            scoreboard.startGame(MEXICO, CANADA);

            Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.startGame("mexico", BRAZIL)
            );

            Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
                scoreboard.startGame(SPAIN, "canada");
            });

            List<Match> activeMatches = scoreboard.getActiveMatches();
            assertEquals(1, activeMatches.size());
            assertEquals(TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE, exception1.getMessage());
            assertEquals(TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE, exception2.getMessage());
        }
    }

    @Nested
    class FinishGameTests {
        @Test
        void shouldFinishGamesCorrectly() {
            Scoreboard scoreboard = new Scoreboard();
            scoreboard.startGame(MEXICO, CANADA);
            scoreboard.startGame(SPAIN, BRAZIL);

            scoreboard.finishGame(MEXICO, CANADA);

            List<Match> activeMatches = scoreboard.getActiveMatches();
            assertEquals(1, activeMatches.size());
            assertEquals(SPAIN, activeMatches.get(0).getHomeTeam());
            assertEquals(BRAZIL, activeMatches.get(0).getAwayTeam());
        }

        @Test
        void shouldThrowExceptionWhenFinishingGameWithEmptyName() {
            Scoreboard scoreboard = new Scoreboard();
            scoreboard.startGame(MEXICO, CANADA);

            Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.finishGame(MEXICO, "")
            );
            Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.finishGame(MEXICO, null)
            );
            Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.finishGame("", CANADA)
            );
            Exception exception4 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.finishGame("", CANADA)
            );

            List<Match> activeMatches = scoreboard.getActiveMatches();
            assertEquals(1, activeMatches.size());
            assertEquals(EMPTY_TEAM_NAME_ERROR_MESSAGE, exception1.getMessage());
            assertEquals(EMPTY_TEAM_NAME_ERROR_MESSAGE, exception2.getMessage());
            assertEquals(EMPTY_TEAM_NAME_ERROR_MESSAGE, exception3.getMessage());
            assertEquals(EMPTY_TEAM_NAME_ERROR_MESSAGE, exception4.getMessage());
        }

        @Test
        void shouldThrowExceptionWhenFinishingNotExistingGame() {
            Scoreboard scoreboard = new Scoreboard();
            scoreboard.startGame(MEXICO, CANADA);

            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.finishGame(CANADA, MEXICO)
            );

            List<Match> activeMatches = scoreboard.getActiveMatches();
            assertEquals(1, activeMatches.size());
            assertEquals(MATCH_NOT_FOUND_ERROR_MESSAGE, exception.getMessage());
        }
    }
}
