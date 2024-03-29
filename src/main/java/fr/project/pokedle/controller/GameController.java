package fr.project.pokedle.controller;

import fr.project.pokedle.game.GameManager;
import fr.project.pokedle.game.classic_game.ClassicGameManager;
import fr.project.pokedle.game.classic_game.PlayClassicGame;
import fr.project.pokedle.game.splashart_game.PlaySplashArtGame;
import fr.project.pokedle.game.splashart_game.SplashArtGameManager;
import fr.project.pokedle.repository.PokemonRepository;
import fr.project.pokedle.service.UserDetailsImpl;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller("/play")
public class GameController {
    @Autowired
    private PokemonRepository pokemonRepository;
    @Autowired
    private PlayClassicGame playClassicGame;
    @Autowired
    private PlaySplashArtGame playSplashArtGame;
    @Autowired
    private ClassicGameManager classicGameManager;
    @Autowired
    private SplashArtGameManager splashArtGameManager;
    @Autowired
    private GameManager gameManager;

    @GetMapping("/play/classic")
    public String showOfficialGame(Model model) {
        model.addAttribute("pokemonList", gameManager.getPokemonList());
        return "game/classic";
    }

    @PostMapping(value = "/play/classic/try", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> tryPokemonOfficialGame(@Param("pokemonName") String pokemonName, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        JSONObject json = playClassicGame.play(userDetails.getUser(), pokemonName);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @PostMapping(value = "/play/classic/previous", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONArray> getPreviousTriesOfficialGame(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        JSONArray json = classicGameManager.getPreviousRoundsJSON(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @GetMapping("/play/splash_art")
    public String showSplashArtGame(Model model) {
        model.addAttribute("pokemonList", gameManager.getPokemonList());
        return "game/splashArt";
    }

    @PostMapping(value = "/play/splash_art/try", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONObject> tryPokemonSplashArtGame(@Param("pokemonName") String pokemonName, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        JSONObject json = playSplashArtGame.play(userDetails.getUser(), pokemonName);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @PostMapping(value = "/play/splash_art/previous", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JSONArray> getPreviousTriesSplashArtGame(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        JSONArray json = splashArtGameManager.getPreviousRoundsJSON(userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @PostMapping(value = "play/splash_art/partial_splash_art", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> downloadSprite(Authentication authentication) throws IOException {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(splashArtGameManager.getImage(userDetails.getUser()), "png", stream);
        byte[] imageBytes = stream.toByteArray();
        byte[] imgBase64 = Base64.encodeBase64(imageBytes);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(imgBase64);
    }

}
