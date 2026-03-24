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
    private static final String GERMANY = "Germany";
    private static final String FRANCE = "France";
    private static final String URUGUAY = "Uruguay";
    private static final String ITALY = "Italy";
    private static final String ARGENTINA = "Argentina";
    private static final String AUSTRALIA = "Australia";


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

            Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.startGame(SPAIN, MEXICO)
            );

            Exception exception4 = assertThrows(IllegalArgumentException.class, () -> {
                scoreboard.startGame(CANADA, BRAZIL);
            });

            List<Match> activeMatches = scoreboard.getActiveMatches();
            assertEquals(1, activeMatches.size());
            assertEquals(TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE, exception1.getMessage());
            assertEquals(TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE, exception2.getMessage());
            assertEquals(TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE, exception3.getMessage());
            assertEquals(TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE, exception4.getMessage());
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

        @Test
        void shouldNotStartGameIfTeamNameContainsNonLetterCharacters() {
            Scoreboard scoreboard = new Scoreboard();

            Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.startGame("@^$#123@($%(aa", CANADA)
            );

            Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.startGame(MEXICO, "@^$#@131($%(aa")
            );

            List<Match> activeMatches = scoreboard.getActiveMatches();
            assertTrue(activeMatches.isEmpty());
            assertEquals(ILLEGAL_CHARACTER_ERROR_MESSAGE, exception1.getMessage());
            assertEquals(ILLEGAL_CHARACTER_ERROR_MESSAGE, exception2.getMessage());
        }

        @Test
        void shouldNotStartGameWithEqualTeamNames() {
            Scoreboard scoreboard = new Scoreboard();

            Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.startGame(MEXICO, MEXICO)
            );

            Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.startGame(MEXICO, "mexico")
            );

            List<Match> activeMatches = scoreboard.getActiveMatches();
            assertTrue(activeMatches.isEmpty());
            assertEquals(SAME_NAME_ERROR_MESSAGE, exception1.getMessage());
            assertEquals(SAME_NAME_ERROR_MESSAGE, exception2.getMessage());
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
        void shouldFinishGamesCorrectlyDespiteInconsistentTrimmingAndLetterCase() {
            Scoreboard scoreboard = new Scoreboard();
            scoreboard.startGame("mExIcO ", "  canada");
            scoreboard.startGame(SPAIN, BRAZIL);

            scoreboard.finishGame(MEXICO, CANADA);
            scoreboard.finishGame(" sPAIn ", " braZIl  ");

            List<Match> activeMatches = scoreboard.getActiveMatches();
            assertTrue(activeMatches.isEmpty());
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

    @Nested
    class UpdateScoreTests {
        @Test
        void shouldUpdateGameScoresCorrectly() {
            Scoreboard scoreboard = new Scoreboard();
            scoreboard.startGame(MEXICO, CANADA);
            scoreboard.startGame(SPAIN, BRAZIL);

            scoreboard.updateScore(MEXICO, 1, CANADA, 0);
            scoreboard.updateScore(SPAIN, 3, BRAZIL, 4);

            List<Match> activeMatches = scoreboard.getActiveMatches();
            assertEquals(2, activeMatches.size());
            assertEquals(1, activeMatches.get(0).getHomeTeamScore());
            assertEquals(0, activeMatches.get(0).getAwayTeamScore());
            assertEquals(3, activeMatches.get(1).getHomeTeamScore());
            assertEquals(4, activeMatches.get(1).getAwayTeamScore());
        }

        @Test
        void shouldThrowExceptionWhenUpdatingGameScoreWithEmptyNames() {
            Scoreboard scoreboard = new Scoreboard();
            scoreboard.startGame(MEXICO, CANADA);

            Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.updateScore(MEXICO, 0, "", 0)
            );
            Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.updateScore(MEXICO, 0, null, 1)
            );
            Exception exception3 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.updateScore("", 1, CANADA, 0)
            );
            Exception exception4 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.updateScore("", 1, CANADA, 1)
            );

            List<Match> activeMatches = scoreboard.getActiveMatches();
            assertEquals(1, activeMatches.size());
            assertEquals(0, activeMatches.get(0).getHomeTeamScore());
            assertEquals(0, activeMatches.get(0).getAwayTeamScore());
            assertEquals(EMPTY_TEAM_NAME_ERROR_MESSAGE, exception1.getMessage());
            assertEquals(EMPTY_TEAM_NAME_ERROR_MESSAGE, exception2.getMessage());
            assertEquals(EMPTY_TEAM_NAME_ERROR_MESSAGE, exception3.getMessage());
            assertEquals(EMPTY_TEAM_NAME_ERROR_MESSAGE, exception4.getMessage());
        }

        @Test
        void shouldThrowExceptionWhenUpdatingNotExistingMatch() {
            Scoreboard scoreboard = new Scoreboard();
            scoreboard.startGame(MEXICO, CANADA);

            Exception exception = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.updateScore(CANADA, 1, MEXICO, 1)
            );

            List<Match> activeMatches = scoreboard.getActiveMatches();
            assertEquals(1, activeMatches.size());
            assertEquals(0, activeMatches.get(0).getHomeTeamScore());
            assertEquals(0, activeMatches.get(0).getAwayTeamScore());
            assertEquals(MATCH_NOT_FOUND_ERROR_MESSAGE, exception.getMessage());
        }

        @Test
        void shouldThrowExceptionWhenUpdatingScoresWithNegativeValues() {
            Scoreboard scoreboard = new Scoreboard();
            scoreboard.startGame(MEXICO, CANADA);

            Exception exception1 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.updateScore(MEXICO, -5, CANADA, 0)
            );

            Exception exception2 = assertThrows(IllegalArgumentException.class, () ->
                    scoreboard.updateScore(MEXICO, 0, CANADA, -5)
            );

            List<Match> activeMatches = scoreboard.getActiveMatches();
            assertEquals(1, activeMatches.size());
            assertEquals(0, activeMatches.get(0).getHomeTeamScore());
            assertEquals(0, activeMatches.get(0).getAwayTeamScore());
            assertEquals(NEGATIVE_SCORE_NOT_ALLOWED_ERROR_MESSAGE, exception1.getMessage());
            assertEquals(NEGATIVE_SCORE_NOT_ALLOWED_ERROR_MESSAGE, exception2.getMessage());
        }
    }

    @Nested
    class getSummaryTests {

        @Test
        void shouldShowSummaryCorrectlyForGamesWithDefaultScores() {
            Scoreboard scoreboard = new Scoreboard();
            scoreboard.startGame(MEXICO, CANADA);
            scoreboard.startGame(SPAIN, BRAZIL);

            List<String> summary = scoreboard.getSummary();

            assertEquals(2, summary.size());
            assertEquals("Spain 0 - Brazil 0", summary.get(0));
            assertEquals("Mexico 0 - Canada 0", summary.get(1));
        }

        @Test
        void shouldShowSummaryCorrectlyBasedOnTeamScoresAndRecency() {
            Scoreboard scoreboard = new Scoreboard();
            scoreboard.startGame(MEXICO, CANADA);
            scoreboard.startGame(SPAIN, BRAZIL);
            scoreboard.startGame(GERMANY, FRANCE);
            scoreboard.startGame(URUGUAY, ITALY);
            scoreboard.startGame(ARGENTINA, AUSTRALIA);

            scoreboard.updateScore(MEXICO, 0, CANADA, 5);
            scoreboard.updateScore(SPAIN, 10, BRAZIL, 2);
            scoreboard.updateScore(GERMANY, 2, FRANCE, 2);
            scoreboard.updateScore(URUGUAY, 6, ITALY, 6);
            scoreboard.updateScore(ARGENTINA, 3, AUSTRALIA, 1);

            List<String> summary = scoreboard.getSummary();

            assertEquals(5, summary.size());
            assertEquals("Uruguay 6 - Italy 6", summary.get(0));
            assertEquals("Spain 10 - Brazil 2", summary.get(1));
            assertEquals("Mexico 0 - Canada 5", summary.get(2));
            assertEquals("Argentina 3 - Australia 1", summary.get(3));
            assertEquals("Germany 2 - France 2", summary.get(4));
        }
    }
}
