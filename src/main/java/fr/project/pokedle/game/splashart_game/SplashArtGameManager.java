package fr.project.pokedle.game.splashart_game;

import fr.project.pokedle.game.GameManager;
import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.persistence.game.splashart.SplashArtGame;
import fr.project.pokedle.persistence.game.splashart.SplashArtGamePlayer;
import fr.project.pokedle.persistence.game.splashart.SplashArtRound;
import fr.project.pokedle.persistence.registration.User;
import fr.project.pokedle.repository.SplashArtGamePlayerRepository;
import fr.project.pokedle.repository.SplashArtGameRepository;
import fr.project.pokedle.repository.SplashArtRoundRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class SplashArtGameManager {
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
        splashArtGame.setCenter_y(0.3 + 0.4 * Math.random());
        splashArtGameRepository.save(splashArtGame);
        return splashArtGame;
    }

    public SplashArtGame getSplashArtGameOfDay(Date date) {
        /* verfify if there is a pokemon to find */
        return splashArtGameRepository.findByDateBetween(
                gameManager.startOfDay(date),
                gameManager.endOfDay(date)
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

    public SplashArtGamePlayer getSplashArtGamePlayer(User user, SplashArtGame splashArtGame) {
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
        } else
            splashArtRound.setRound(0);
        splashArtRoundRepository.save(splashArtRound);
        return splashArtRound;
    }

    public List<SplashArtRound> getPreviousRounds(User user) {
        SplashArtGamePlayer splashArtGamePlayer = getSplashArtGamePlayer(
                user,
                getSplashArtGameOfDay(new Date())
        );
        List<SplashArtRound> rounds = splashArtGamePlayer.getRounds();
        if (rounds != null && rounds.size() > 0) {
            Collections.sort(rounds, (o1, o2) -> (int) (o1.getRound() - o2.getRound()));
            return rounds;
        } else {
            return new ArrayList<>();
        }
    }


    public JSONArray getPreviousRoundsJSON(User user) {
        List<SplashArtRound> rounds = getPreviousRounds(user);
        JSONArray json = new JSONArray();

        if (rounds.size() != 0) {
            Pokemon pokemonToFind = rounds.get(0).getGamePlayer().getGame().getPokemon();
            rounds.forEach(round -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("is_same", round.getPokemon().equals(pokemonToFind));
                jsonObject.put("pokemon", round.getPokemon().toJSON());
                json.add(jsonObject);
            });
        }
        return json;
    }

    public BufferedImage getImage(User user) throws IOException {
        SplashArtGame splashArtGame = getSplashArtGameOfDay(new Date());
        SplashArtGamePlayer splashArtGamePlayer = getSplashArtGamePlayer(
                user,
                splashArtGame
        );

        String pathImg = getSplashArtGameOfDay(new Date()).getPokemon().getLinkBigSprite();
        BufferedImage img = ImageIO.read(new URL(pathImg));

        if (splashArtGamePlayer.isSuccess())
            return img;

        int numberRounds = splashArtGamePlayer.getRounds().size();
        int imageHeight = img.getHeight();
        int imageWidth = img.getWidth();

        double size = 0.02 * (2 + numberRounds);

        return img.getSubimage(
                (int) (imageWidth * Math.max(0, splashArtGame.getCenter_x() - size)),
                (int) (imageHeight * Math.max(0, splashArtGame.getCenter_y() - size)),
                (int) (imageWidth * 2 * size),
                (int) (imageHeight * 2 * size)
        );
    }
}
