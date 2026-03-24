package org.football.scoreboard;

public class MatchKeyProvider {

    public static String provide(String homeTeam, String awayTeam) {
        return homeTeam.trim().toLowerCase() + " - " + awayTeam.trim().toLowerCase();
    }

    private MatchKeyProvider() {
        // Unused private constructor
    }
}
