package org.football.scoreboard;

import org.apache.commons.lang3.StringUtils;

import static org.football.scoreboard.ErrorMessageConstants.EMPTY_TEAM_NAME_ERROR_MESSAGE;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;

    public Match(String homeTeam, String awayTeam) {
        if (StringUtils.isEmpty(homeTeam) || StringUtils.isEmpty(awayTeam)) {
            throw new IllegalArgumentException(EMPTY_TEAM_NAME_ERROR_MESSAGE);
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
