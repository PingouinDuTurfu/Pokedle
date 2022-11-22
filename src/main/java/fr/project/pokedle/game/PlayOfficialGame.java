package fr.project.pokedle.game;

import fr.project.pokedle.persistence.Pokemon;
import fr.project.pokedle.persistence.User;
import fr.project.pokedle.persistence.classic.ClassicGame;
import fr.project.pokedle.persistence.classic.ClassicGamePlayer;
import fr.project.pokedle.persistence.classic.ClassicRound;
import fr.project.pokedle.persistence.repository.ClassicGamePlayerRepository;
import fr.project.pokedle.persistence.repository.ClassicGameRepository;
import fr.project.pokedle.persistence.repository.ClassicRoundRepository;
import fr.project.pokedle.persistence.repository.PokemonRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class PlayOfficialGame {
    private final PokemonRepository pokemonRepository;
    private final ClassicGameRepository classicGameRepository;
    private final ClassicGamePlayerRepository classicGamePlayerRepository;
    private final ClassicRoundRepository classicRoundRepository;

    private String result = "";

    public PlayOfficialGame(PokemonRepository pokemonRepository, ClassicGameRepository classicGameRepository, ClassicGamePlayerRepository classicGamePlayerRepository, ClassicRoundRepository classicRoundRepository) {
        this.pokemonRepository = pokemonRepository;
        this.classicGameRepository = classicGameRepository;
        this.classicGamePlayerRepository = classicGamePlayerRepository;
        this.classicRoundRepository = classicRoundRepository;
    }

    public void play(User user, String pokemonNameToTry) {
        /* verfify if there is a pokemon to find */
        LocalDateTime now = LocalDateTime.now(); //current date and time
        LocalDateTime start = now.toLocalDate().atStartOfDay();
        LocalDateTime end = now.toLocalDate().atStartOfDay().plusDays(1);

        GameOfficialManager gameOfficialManager = new GameOfficialManager(pokemonRepository, classicGameRepository, classicGamePlayerRepository, classicRoundRepository);

        ClassicGame classicGame = classicGameRepository.findByDateBetween(
                Date.from(start.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(end.atZone(ZoneId.systemDefault()).toInstant())
        ).orElseGet(gameOfficialManager::createGame);

        ClassicGamePlayer classicGamePlayer = classicGamePlayerRepository.findByUserAndCreationDateBetween(
                user,
                Date.from(start.atZone(ZoneId.systemDefault()).toInstant()),
                Date.from(end.atZone(ZoneId.systemDefault()).toInstant())
        ).orElseGet(() -> gameOfficialManager.createGamePlayer(user, classicGame));

        if (classicGamePlayer.isSuccess()) {
            System.out.println("already finished");
            return;
        }

        /* verfify if the pokemon enter is correct */
        Pokemon pokemonToTry = pokemonRepository.findPokemonsByNameFr(pokemonNameToTry).orElseThrow(RuntimeException::new);

        Pokemon pokemonToFind = classicGame.getPokemon();
        System.out.println(pokemonToFind.getNameFr());

        // comparaison des pokemon
        GameOfficialTry gameOfficialTry = new GameOfficialTry(pokemonToTry, pokemonToFind);

        ClassicRound classicRound = gameOfficialManager.createGameRound(classicGamePlayer, pokemonToTry);

        if (gameOfficialTry.isSame()) {
            classicGamePlayer.setSuccess(true);
            classicGamePlayer.setSuccessDate(new Date());
            classicGamePlayerRepository.save(classicGamePlayer);
            System.out.println("You found it !");
        } else {
            // do sthg
        }

        result =    "{\n" +
                    "   \"pokemonInfo\": {\n" +
                    "       \"id: "+ pokemonToTry.getId() +",\n" +
                    "       \"nameFr\": \""+ pokemonToTry.getNameFr() +"\",\n" +
                    "       \"nameEn\": \""+ pokemonToTry.getNameEn() +"\",\n" +
                    "       \"shape\": \""+ pokemonToTry.getShape().getName() +"\",\n" +
                    "       \"type1\": \""+ pokemonToTry.getType1().getName() +"\",\n" +
                    "       \"type2\": \""+ (pokemonToTry.getType2() == null ? pokemonToTry.getType2().getName() : null) +"\",\n" +
                    "       \"color\": \""+ pokemonToTry.getColor() +"\",\n" +
                    "       \"height\": \""+ pokemonToTry.getHeight() +"\",\n" +
                    "       \"weight\": \""+ pokemonToTry.getWeight() +"\",\n" +
                    "       \"linkIcon\": \""+ pokemonToTry.getLinkIcon() +"\",\n" +
                    "       \"linkSmallSprite\": \""+ pokemonToTry.getLinkSmallSprite() +"\",\n" +
                    "       \"linkBigSprite\": \""+ pokemonToTry.getLinkBigSprite() +"\"\n" +
                    "   }\n" +
                    "}";
    }

    public String getResult() {
        return result;
    }
}
