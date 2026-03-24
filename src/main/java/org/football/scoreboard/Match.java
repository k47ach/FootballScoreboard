package org.football.scoreboard;

import org.apache.commons.lang3.StringUtils;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;

    public Match(String homeTeam, String awayTeam) {
        if (StringUtils.isEmpty(homeTeam) || StringUtils.isEmpty(awayTeam)) {
            throw new IllegalArgumentException("Team name can not be empty");
        }

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }
}
