package fr.project.pokedle.game;

import fr.project.pokedle.game.classic_game.ClassicGameManager;
import fr.project.pokedle.game.splashart_game.SplashArtGameManager;
import fr.project.pokedle.persistence.game.classic.ClassicGame;
import fr.project.pokedle.persistence.game.classic.ClassicGamePlayer;
import fr.project.pokedle.persistence.game.splashart.SplashArtGame;
import fr.project.pokedle.persistence.game.splashart.SplashArtGamePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LeaderBoard {
    @Autowired
    private ClassicGameManager classicGameManager;
    @Autowired
    private SplashArtGameManager splashArtGameManager;

    public List<ClassicGamePlayer> getClassicMapScoreOfDay(Date date) {
        ClassicGame classicGame = classicGameManager.getClassicGameOfDay(date);
        return classicGame == null ? new ArrayList<>() : classicGame
                                                            .getGamePlayers()
                                                            .stream()
                                                            .filter(ClassicGamePlayer::isSuccess)
                                                            .sorted((o1, o2) -> (int) (o2.getScore() - o1.getScore()))
                                                            .collect(Collectors.toList());
    }

    public List<SplashArtGamePlayer> getSplashArtMapScoreOfDay(Date date) {
        SplashArtGame splashArtGame = splashArtGameManager.getSplashArtGameOfDay(date);
        return splashArtGame == null ? new ArrayList<>() : splashArtGame
                .getGamePlayers()
                .stream()
                .filter(SplashArtGamePlayer::isSuccess)
                .sorted((o1, o2) -> (int) (o2.getScore() - o1.getScore()))
                .collect(Collectors.toList());
    }
}
