package fr.project.pokedle.init;


import fr.project.pokedle.init.loader.PokemonItem;
import fr.project.pokedle.init.loader.SpecieItem;
import fr.project.pokedle.persistence.data.Pokemon;
import fr.project.pokedle.persistence.data.PokemonShape;
import fr.project.pokedle.persistence.data.PokemonType;
import fr.project.pokedle.repository.PokemonRepository;
import fr.project.pokedle.repository.PokemonShapeRepository;
import fr.project.pokedle.repository.PokemonTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;


@Component
public class Initialisation implements ApplicationListener<ContextRefreshedEvent> {

    private static final String REMOTE_DIRECTORY_NAME = "/home/remy/pokedle/dataset/src/dataSet/";
    private static final String LOCAL_DIRECTORY_NAME = "data/";

    private static final String POKEMON_FILE_NAME = "pokemon.json";
    private static final String TYPE_FILE_NAME = "type.json";
    private static final String SHAPE_FILE_NAME = "shape.json";

    private static final File POKEMON_FILE;
    private static final File TYPE_FILE;
    private static final File SHAPE_FILE;

    private static final String REMOTE_HOST = "www.pingouinduturfu.fr";
    private static final String USERNAME = "remy";
    private static final String PASSWORD = "pingouin";
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 5000;

    @Autowired
    PokemonRepository pokemonRepository;

    @Autowired
    PokemonTypeRepository pokemonTypeRepository;

    @Autowired
    PokemonShapeRepository pokemonShapeRepository;

    static {
        try {
            POKEMON_FILE = getFile(LOCAL_DIRECTORY_NAME + POKEMON_FILE_NAME);
            TYPE_FILE = getFile(LOCAL_DIRECTORY_NAME + TYPE_FILE_NAME);
            SHAPE_FILE = getFile(LOCAL_DIRECTORY_NAME + SHAPE_FILE_NAME);
        } catch (URISyntaxException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getFile(String urlFileName) throws URISyntaxException, FileNotFoundException {
        URL url = Initialisation.class.getClassLoader().getResource(urlFileName);
        if(url == null)
            throw new FileNotFoundException("File " + urlFileName + " not found");
        return Paths.get(url.toURI()).toFile();
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
//        initData();
    }

    private void initData() {
        SFTPFileTransfer sftp = new SFTPFileTransfer(REMOTE_HOST, USERNAME, PASSWORD, REMOTE_PORT, SESSION_TIMEOUT, CHANNEL_TIMEOUT);

        sftp.getRemoteFile(REMOTE_DIRECTORY_NAME + TYPE_FILE_NAME, TYPE_FILE.getAbsolutePath());
        pokemonTypeRepository.saveAll(
                SFTPFileTransfer
                        .mapFromJSON(TYPE_FILE, SpecieItem.class)
                        .stream()
                        .map(pokemonType ->
                                new PokemonType.Builder()
                                        .setName(pokemonType.getName())
                                        .setLinkIcon(pokemonType.getIcon())
                                        .build()
                        ).toList()
        );
        System.out.println("Types loaded");
        sftp.getRemoteFile(REMOTE_DIRECTORY_NAME + SHAPE_FILE_NAME, SHAPE_FILE.getAbsolutePath());
        pokemonShapeRepository.saveAll(
                SFTPFileTransfer
                        .mapFromJSON(SHAPE_FILE, SpecieItem.class)
                        .stream()
                        .map(pokemonShape ->
                                new PokemonShape.Builder()
                                        .setName(pokemonShape.getName())
                                        .setLinkIcon(pokemonShape.getIcon())
                                        .build()
                        ).toList()
        );
        System.out.println("Shapes loaded");
        sftp.getRemoteFile(REMOTE_DIRECTORY_NAME + POKEMON_FILE_NAME, POKEMON_FILE.getAbsolutePath());;
        pokemonRepository.saveAll(
                SFTPFileTransfer
                        .mapFromJSON(POKEMON_FILE, PokemonItem.class)
                        .stream().map(pokemonItem ->
                                new Pokemon.Builder()
                                        .setId(Long.parseLong(pokemonItem.getId()))
                                        .setNameEn(pokemonItem.getName_en())
                                        .setNameFr(pokemonItem.getName_fr())
                                        .setHeight(pokemonItem.getHeight())
                                        .setWeight(pokemonItem.getWeight())
                                        .setLinkBigSprite(pokemonItem.getSpriteHQ())
                                        .setLinkSmallSprite(pokemonItem.getSpriteLQ())
                                        .setLinkIcon(pokemonItem.getIcon())
                                        .setColor(pokemonItem.getColor())
                                        .setGeneration(pokemonItem.getGeneration())
                                        .setShape(pokemonShapeRepository.findFirstByName(pokemonItem.getShape()))
                                        .setType1(pokemonTypeRepository.findFirstByName(pokemonItem.getType1()))
                                        .setType2(pokemonTypeRepository.findFirstByName(pokemonItem.getType2()))
                                        .build()
                        ).toList()
        );
        System.out.println("Pokemons loaded");
    }
}