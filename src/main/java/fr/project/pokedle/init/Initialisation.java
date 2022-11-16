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

    private static final String remoteDirectoryName = "/home/***REMOVED***/pokedle/dataset/src/dataSet/";
    private static final String localDirectoryName = "data/";

    private static final String pokemonFileName = "pokemon.json";
    private static final String typeFileName = "type.json";
    private static final String shapeFileName = "shape.json";

    private static final File pokemonFile;
    private static final File typeFile;
    private static final File shapeFile;

    private static final String REMOTE_HOST = "***REMOVED***";
    private static final String USERNAME = "***REMOVED***";
    private static final String PASSWORD = "***REMOVED***";
    private static final int REMOTE_PORT = 22;
    private static final int SESSION_TIMEOUT = 10000;
    private static final int CHANNEL_TIMEOUT = 5000;

    static {
        try {
            pokemonFile = getFile(localDirectoryName + pokemonFileName);
            typeFile = getFile(localDirectoryName + typeFileName);
            shapeFile = getFile(localDirectoryName + shapeFileName);
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

        sftp.getRemoteFile(remoteDirectoryName + pokemonFileName, pokemonFile.getAbsolutePath());;
        List<PokemonItem> pokemonList = SFTPFileTransfer.mapFromJSON(pokemonFile, PokemonItem.class);

        sftp.getRemoteFile(remoteDirectoryName + typeFileName, typeFile.getAbsolutePath());
        List<SpecieItem> typeList = SFTPFileTransfer.mapFromJSON(typeFile, SpecieItem.class);

        sftp.getRemoteFile(remoteDirectoryName + shapeFileName, shapeFile.getAbsolutePath());
        List<SpecieItem> shapeList = SFTPFileTransfer.mapFromJSON(shapeFile, SpecieItem.class);
    }
}