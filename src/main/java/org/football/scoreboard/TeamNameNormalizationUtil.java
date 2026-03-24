package org.football.scoreboard;

import org.apache.commons.lang3.StringUtils;

public class TeamNameNormalizationUtil {

    public static String normalize(String teamName) {
        return StringUtils.capitalize(teamName.toLowerCase().trim());
    }

    private TeamNameNormalizationUtil() {
        // Constructor not needed
    }
}
