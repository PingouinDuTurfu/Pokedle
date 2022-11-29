package fr.project.pokedle.game.splashart_game;

import fr.project.pokedle.game.GameManager;
import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.persistence.game.splashart.SplashArtGame;
import fr.project.pokedle.persistence.game.splashart.SplashArtGamePlayer;
import fr.project.pokedle.persistence.game.splashart.SplashArtRound;
import fr.project.pokedle.persistence.registration.User;
import fr.project.pokedle.repository.PokemonRepository;
import fr.project.pokedle.repository.SplashArtGamePlayerRepository;
import fr.project.pokedle.repository.SplashArtGameRepository;
import fr.project.pokedle.repository.SplashArtRoundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class SplashArtGameManager {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private SplashArtGameRepository splashArtGameRepository;

    @Autowired
    private SplashArtGamePlayerRepository splashArtGamePlayerRepository;

    @Autowired
    private SplashArtRoundRepository splashArtRoundRepository;

    @Autowired
    private GameManager gameManager;

    public SplashArtGame createSplashArtGame() {
        Pokemon pokemon = gameManager.getRandomPokemon();

        // create game for the day
        SplashArtGame splashArtGame = new SplashArtGame();
        splashArtGame.setPokemon(pokemon);
        splashArtGame.setDate(new Date());
        splashArtGame.setCenter_x(0.3 + 0.4 * Math.random());
        splashArtGame.setCenter_x(0.3 + 0.4 * Math.random());

        splashArtGameRepository.save(splashArtGame);

        return splashArtGame;
    }

    public SplashArtGame getSplashArtGameOfToday() {
        /* verfify if there is a pokemon to find */
        LocalDateTime now = LocalDateTime.now(); //current date and time
        LocalDateTime start = now.toLocalDate().atStartOfDay();
        LocalDateTime end = now.toLocalDate().atStartOfDay().plusDays(1);

        return splashArtGameRepository.findByDateBetween(
                Date.from(start.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(end.atZone(ZoneId.systemDefault()).toInstant())
        ).orElseGet(this::createSplashArtGame);
    }

    public SplashArtGamePlayer createSplashArtGamePlayer(User user, SplashArtGame splashArtGame) {
        SplashArtGamePlayer splashArtGamePlayer = new SplashArtGamePlayer();
        splashArtGamePlayer.setUser(user);
        splashArtGamePlayer.setGame(splashArtGame);
        splashArtGamePlayer.setSuccess(false);
        splashArtGamePlayer.setCreationDate(new Date());

        splashArtGamePlayerRepository.save(splashArtGamePlayer);

        return splashArtGamePlayer;
    }

    public SplashArtGamePlayer getSplashArtGamePlayerOfToday(User user, SplashArtGame splashArtGame) {
        return splashArtGamePlayerRepository.findByUserAndGame(
                user,
                splashArtGame
        ).orElseGet(() -> createSplashArtGamePlayer(user, splashArtGame));
    }

    public SplashArtRound createSplashArtRound(SplashArtGamePlayer splashArtGamePlayer, Pokemon pokemon) {
        SplashArtRound splashArtRound = new SplashArtRound();
        splashArtRound.setGamePlayer(splashArtGamePlayer);
        splashArtRound.setPokemon(pokemon);

        if (splashArtGamePlayer.getRounds().size() > 0) {
            List<Long> indexRounds = new ArrayList<>(splashArtGamePlayer.getRounds().stream().map(SplashArtRound::getRound).toList());
            Collections.sort(indexRounds);
            splashArtRound.setRound(1 + indexRounds.get(indexRounds.size() - 1));
        } else {
            splashArtRound.setRound(0);
        }
        splashArtRoundRepository.save(splashArtRound);
        return splashArtRound;
    }

    public List<SplashArtRound> getPreviousRounds(User user) {
        SplashArtGamePlayer splashArtGamePlayer = getSplashArtGamePlayerOfToday(
                user,
                getSplashArtGameOfToday()
        );
        List<SplashArtRound> rounds = splashArtGamePlayer.getRounds();
        Collections.sort(rounds, (o1, o2) -> (int) (o1.getRound() - o2.getRound()));

        return rounds;
    }



}
