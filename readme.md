# Football Worldcup Scoreboard Library
This basic library provides an in-memory scoreboard with addition, modification, removal and display actions.

---

## Description
This library has been implemented with TDD approach in mind. It's not thread-safe. It uses an in-memory data structure to store games.
<br />It permits following operations:
* Starting new game - adds new match with home team and away team to the scoreboard and sets the score to 0 - 0.
* Finishing existing game - removes an existing match from the scoreboard.
* Updating score - sets the home team and away team score to new values.
* Getting a summary of games by total score - gets a list of Strings containing team names and scores sorted by total score and recency.

--- 

## Technologies
* Java 25
* JUnit
* Maven

---

## Install
Clone the repository ```git clone https://github.com/k47ach/FootballScoreboard.git```

## Assumptions
1. If a team is already assigned to an active match on the scoreboard, it cannot be assigned to another match.
2. A team can't play against itself.
3. A team must be assigned an opponent.
4. Score can be updated to lower their values, but never below 0.

## Technical details
### Storage
During the implementation, I decided to use ```LinkedHashMap``` instead of ```ArrayList``` to store Match objects.
* ```LinkedHashMap``` enables O(1) access to the data.
* ```LinkedHashMap``` preserves the insertion order of the data.
* Keys for match object contain homeTeam and awayTeam, ensuring their uniqueness.

### Sorting
Sorting games as per the acceptance criteria 4 has been accomplished in two steps: 
1. The order of elements is first reversed, so that matches are ordered by their addition recency. This is performed in O(n). 
2. The order of elements is then sorted by their score sum, reversed. Given that Stream.sorted is stable, we can safely assume that tied elements will remain unchanged. This is performed in O(n log n).

### Validation
Input (starting, finishing and updating) operations are provided with basic validation to handle invalid data by throwing an exception with reason message. Validation has been extracted to another class to separate concerns and improve reusability and maintainability.