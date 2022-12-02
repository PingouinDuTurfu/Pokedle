package fr.project.pokedle.game;

import org.springframework.stereotype.Component;

@Component
public class ScoreManager {
    public double computeScore(int numberRounds) {
        return Math.floor(
          100.0 / Math.log10(10.0 + 10.0 * (numberRounds))
        );
    }
}
