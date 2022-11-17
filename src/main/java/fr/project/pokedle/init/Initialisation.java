package fr.project.pokedle.init;


import fr.project.pokedle.init.loader.PokemonItem;
import fr.project.pokedle.init.loader.SpecieItem;
import fr.project.pokedle.persistence.Pokemon;
import fr.project.pokedle.persistence.PokemonShape;
import fr.project.pokedle.persistence.PokemonType;
import fr.project.pokedle.persistence.jpa.PokemonRepository;
import fr.project.pokedle.persistence.jpa.PokemonShapeRepository;
import fr.project.pokedle.persistence.jpa.PokemonTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;


@Component
public class Initialisation implements ApplicationListener<ContextRefreshedEvent> {

    private static final String REMOTE_DIRECTORY_NAME = "/home/***REMOVED***/pokedle/dataset/src/dataSet/";
    private static final String LOCAL_DIRECTORY_NAME = "data/";

    private static final String POKEMON_FILE_NAME = "pokemon.json";
    private static final String TYPE_FILE_NAME = "type.json";
    private static final String SHAPE_FILE_NAME = "shape.json";

    private static final File POKEMON_FILE;
    private static final File TYPE_FILE;
    private static final File SHAPE_FILE;

    private static final String REMOTE_HOST = "***REMOVED***";
    private static final String USERNAME = "***REMOVED***";
    private static final String PASSWORD = "***REMOVED***";
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 5000;

    static {
        try {
            POKEMON_FILE = getFile(LOCAL_DIRECTORY_NAME + POKEMON_FILE_NAME);
            TYPE_FILE = getFile(LOCAL_DIRECTORY_NAME + TYPE_FILE_NAME);
            SHAPE_FILE = getFile(LOCAL_DIRECTORY_NAME + SHAPE_FILE_NAME);
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    PokemonTypeRepository pokemonTypeRepository;

    @Autowired
    PokemonShapeRepository pokemonShapeRepository;

    public static File getFile(String urlFileName) throws URISyntaxException, FileNotFoundException {
        URL url = Initialisation.class.getClassLoader().getResource(urlFileName);
        if(url == null)
            throw new FileNotFoundException("File " + urlFileName + " not found");
        return Paths.get(url.toURI()).toFile();
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        SFTPFileTransfer sftp = new SFTPFileTransfer(REMOTE_HOST, USERNAME, PASSWORD, REMOTE_PORT, SESSION_TIMEOUT, CHANNEL_TIMEOUT);

        sftp.getRemoteFile(REMOTE_DIRECTORY_NAME + TYPE_FILE_NAME, TYPE_FILE.getAbsolutePath());
        List<SpecieItem> typeList = SFTPFileTransfer.mapFromJSON(TYPE_FILE, SpecieItem.class);
        typeList.forEach(typeItem -> {
            PokemonType pokemonType = new PokemonType();
            pokemonType.setLinkIcon(typeItem.getIcon());
            pokemonType.setName(typeItem.getName());
            pokemonTypeRepository.save(pokemonType);
        });

        sftp.getRemoteFile(REMOTE_DIRECTORY_NAME + SHAPE_FILE_NAME, SHAPE_FILE.getAbsolutePath());
        List<SpecieItem> shapeList = SFTPFileTransfer.mapFromJSON(SHAPE_FILE, SpecieItem.class);
        shapeList.forEach(shapeItem -> {
            PokemonShape pokemonShape = new PokemonShape();
            pokemonShape.setLinkIcon(shapeItem.getIcon());
            pokemonShape.setName(shapeItem.getName());
            pokemonShapeRepository.save(pokemonShape);
        });

        sftp.getRemoteFile(REMOTE_DIRECTORY_NAME + POKEMON_FILE_NAME, POKEMON_FILE.getAbsolutePath());;
        List<PokemonItem> pokemonList = SFTPFileTransfer.mapFromJSON(POKEMON_FILE, PokemonItem.class);
        pokemonList.forEach(pokemonItem -> {
            Pokemon pokemon = new Pokemon();
            pokemon.setId(Long.parseLong(pokemonItem.getId()));
            pokemon.setNameEn(pokemonItem.getName_en());
            pokemon.setNameFr(pokemonItem.getName_fr());
            pokemon.setHeight(pokemonItem.getHeight());
            pokemon.setWeight(pokemonItem.getWeight());
            pokemon.setLinkBigSprite(pokemonItem.getSpriteHQ());
            pokemon.setLinkSmallSprite(pokemonItem.getSpriteLQ());
            pokemon.setLinkIcon(pokemonItem.getIcon());
            pokemon.setColor(pokemonItem.getColor());

            PokemonShape pokemonShape = pokemonShapeRepository.findFirstByName(pokemonItem.getShape());
            if (pokemonShape != null) {
                pokemon.setShape(pokemonShape);
            }
            PokemonType pokemonType1 = pokemonTypeRepository.findFirstByName(pokemonItem.getType1());
            if (pokemonType1 != null) {
                pokemon.setType1(pokemonType1);
            }
            PokemonType pokemonType2 = pokemonTypeRepository.findFirstByName(pokemonItem.getType2());
            if (pokemonType1 != null) {
                pokemon.setType2(pokemonType2);
            }

            pokemonRepository.save(pokemon);
            System.out.println(pokemon.getNameFr() + " donne");
        });
    }
}