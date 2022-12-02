package fr.project.pokedle.game;

import org.springframework.stereotype.Component;

@Component
public class Score {
    public double computeScore(int numberRounds) {
        return Math.floor(
          100 / Math.log(2 + 0.2 * (numberRounds - 1))
        );
    }
}
