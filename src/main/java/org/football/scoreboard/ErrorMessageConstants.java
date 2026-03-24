package org.football.scoreboard;

public final class ErrorMessageConstants {
    public static final String EMPTY_TEAM_NAME_ERROR_MESSAGE = "Team name can not be empty";
    public static final String TEAM_ALREADY_ASSIGNED_ERROR_MESSAGE = "Team already assigned to another match";
    public static final String MATCH_NOT_FOUND_ERROR_MESSAGE = "Match can not be found";
    public static final String NEGATIVE_SCORE_NOT_ALLOWED_ERROR_MESSAGE = "Team score can not be negative";

    private ErrorMessageConstants() {
        // Object instantiation not needed for constants class
    }
}
