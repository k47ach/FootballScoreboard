package org.football.scoreboard;

public class MatchKeyProvider {

    public static String provide(String homeTeam, String awayTeam) {
        return homeTeam + " - " + awayTeam;
    }

    private MatchKeyProvider() {
        // Unused private constructor
    }
}
