package fr.project.pokedle.game.splashart_game;

import fr.project.pokedle.game.GameManager;
import fr.project.pokedle.game.ScoreManager;
import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.persistence.game.splashart.SplashArtGame;
import fr.project.pokedle.persistence.game.splashart.SplashArtGamePlayer;
import fr.project.pokedle.persistence.game.splashart.SplashArtRound;
import fr.project.pokedle.persistence.registration.User;
import fr.project.pokedle.repository.SplashArtGamePlayerRepository;
import fr.project.pokedle.repository.SplashArtGameRepository;
import fr.project.pokedle.repository.SplashArtRoundRepository;
import fr.project.pokedle.service.ImageReaderService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class SplashArtGameManager {

    private final double ZOOM_SPEED = 0.02;
    private final double ZOOM_SIZE = 2;
    private final double CENTER_MIN = 0.3;
    private final double CENTER_MAX = 0.4;

    private final String LOCAL_DIRECTORY_NAME = "static";

    @Autowired
    private SplashArtGameRepository splashArtGameRepository;
    @Autowired
    private SplashArtGamePlayerRepository splashArtGamePlayerRepository;
    @Autowired
    private SplashArtRoundRepository splashArtRoundRepository;
    @Autowired
    private GameManager gameManager;
    @Autowired
    private ScoreManager scoreManager;
    @Autowired
    private ImageReaderService imageReaderService;

    public SplashArtGame createSplashArtGame() {
        Pokemon pokemon = gameManager.getRandomPokemon();

        SplashArtGame splashArtGame = new SplashArtGame();
        splashArtGame.setPokemon(pokemon);
        splashArtGame.setDate(new Date());
        splashArtGame.setCenter_x(CENTER_MIN + (CENTER_MAX - CENTER_MIN) * Math.random());
        splashArtGame.setCenter_y(CENTER_MIN + (CENTER_MAX - CENTER_MIN) * Math.random());
        splashArtGameRepository.save(splashArtGame);
        return splashArtGame;
    }

    public SplashArtGame getSplashArtGameOfDayOrCreate(Date date) {
        return splashArtGameRepository.findByDateBetween(
                gameManager.startOfDay(date),
                gameManager.endOfDay(date)
        ).orElseGet(this::createSplashArtGame);
    }

    public SplashArtGame getSplashArtGameOfDay(Date date) {
        return splashArtGameRepository.findByDateBetween(
                gameManager.startOfDay(date),
                gameManager.endOfDay(date)
        ).orElse(null);
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
                getSplashArtGameOfDayOrCreate(new Date())
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

        if (rounds != null && rounds.size() > 0) {
            Pokemon pokemonToFind = rounds.get(0).getGamePlayer().getGame().getPokemon();
            rounds.forEach(round -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("is_same", round.getPokemon().equals(pokemonToFind));
                jsonObject.put("pokemon", round.getPokemon().toJSON());
                if (round.getPokemon().equals(pokemonToFind))
                    jsonObject.put("score", round.getGamePlayer().getScore());
                json.add(jsonObject);
            });
        }
        return json;
    }

    public BufferedImage getImage(User user) throws IOException {
        SplashArtGame splashArtGame = getSplashArtGameOfDayOrCreate(new Date());
        SplashArtGamePlayer splashArtGamePlayer = getSplashArtGamePlayer(
                user,
                splashArtGame
        );

        String pathImg = getSplashArtGameOfDayOrCreate(new Date()).getPokemon().getLinkBigSprite();
        BufferedImage img = ImageIO.read(imageReaderService.getFileFromResource(LOCAL_DIRECTORY_NAME + pathImg));

        if (splashArtGamePlayer.isSuccess())
            return img;

        int numberRounds = splashArtGamePlayer.getRounds().size();
        int imageHeight = img.getHeight();
        int imageWidth = img.getWidth();

        double size = ZOOM_SPEED * (ZOOM_SIZE + numberRounds);
        double xMin = Math.max(0, splashArtGame.getCenter_x() - size);
        double yMin = Math.max(0, splashArtGame.getCenter_y() - size);
        double xMax = Math.min(1, splashArtGame.getCenter_x() + size);
        double yMax = Math.min(1, splashArtGame.getCenter_y() + size);

        return img.getSubimage(
                (int) (xMin * imageWidth),
                (int) (yMin * imageHeight),
                (int) ((xMax - xMin) * imageWidth),
                (int) ((yMax - yMin) * imageHeight)
        );
    }

    public double saveGamePlayerOnCompletion(SplashArtGamePlayer splashArtGamePlayer) {
        splashArtGamePlayer.setSuccess(true);
        splashArtGamePlayer.setSuccessDate(new Date());
        splashArtGamePlayer.setScore(scoreManager.computeScore(splashArtGamePlayer.getRounds().size()));
        splashArtGamePlayerRepository.save(splashArtGamePlayer);
        return splashArtGamePlayer.getScore();
    }
}
