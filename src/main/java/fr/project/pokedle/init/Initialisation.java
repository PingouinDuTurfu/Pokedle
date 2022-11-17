package fr.project.pokedle.init;


import fr.project.pokedle.init.loader.PokemonItem;
import fr.project.pokedle.init.loader.SpecieItem;
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

    public static File getFile(String urlFileName) throws URISyntaxException, FileNotFoundException {
        URL url = Initialisation.class.getClassLoader().getResource(urlFileName);
        if(url == null)
            throw new FileNotFoundException("File " + urlFileName + " not found");
        return Paths.get(url.toURI()).toFile();
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        SFTPFileTransfer sftp = new SFTPFileTransfer(REMOTE_HOST, USERNAME, PASSWORD, REMOTE_PORT, SESSION_TIMEOUT, CHANNEL_TIMEOUT);

        sftp.getRemoteFile(REMOTE_DIRECTORY_NAME + POKEMON_FILE_NAME, POKEMON_FILE.getAbsolutePath());;
        List<PokemonItem> pokemonList = SFTPFileTransfer.mapFromJSON(POKEMON_FILE, PokemonItem.class);

        sftp.getRemoteFile(REMOTE_DIRECTORY_NAME + TYPE_FILE_NAME, TYPE_FILE.getAbsolutePath());
        List<SpecieItem> typeList = SFTPFileTransfer.mapFromJSON(TYPE_FILE, SpecieItem.class);

        sftp.getRemoteFile(REMOTE_DIRECTORY_NAME + SHAPE_FILE_NAME, SHAPE_FILE.getAbsolutePath());
        List<SpecieItem> shapeList = SFTPFileTransfer.mapFromJSON(SHAPE_FILE, SpecieItem.class);
    }
}