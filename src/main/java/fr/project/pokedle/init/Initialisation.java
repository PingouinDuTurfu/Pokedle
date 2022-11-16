package fr.project.pokedle.init;


import fr.project.pokedle.init.loader.PokemonItem;
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

    private static final String remoteDirectoryName = "/home/remy/pokedle/dataset/src/dataSet/";
    private static final String localDirectoryName = "data";

    private static final String pokemonFileName = "pokemonFormated.json";
    private static final String typeFileName = "type.json";
    private static final String shapeFileName = "shape.json";

    private static final File pokemonFile;
    private static final File typeFile;
    private static final File shapeFile;

    static {
        pokemonFile = getFile(localDirectoryName + "/" + pokemonFileName);
        typeFile = getFile(localDirectoryName + "/" + typeFileName);
        shapeFile = getFile(localDirectoryName + "/" + shapeFileName);
    }

    public static File getFile(String urlFileName) {
        try {
            URL url = Initialisation.class.getClassLoader().getResource(urlFileName);
            if(url == null)
                throw new FileNotFoundException("File " + urlFileName + " not found");
            return Paths.get(url.toURI()).toFile();
        } catch (URISyntaxException | FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        SFTPFileTransfer sftp = new SFTPFileTransfer();

        sftp.getRemoteFile(remoteDirectoryName + "/" + pokemonFileName, pokemonFile.getAbsolutePath());
        List<PokemonItem> pokemonList = sftp.mapFromJSON(pokemonFile);
//        sftp.getRemoteFile(remoteDirectoryName + "/" + typeFileName, typeFile.getAbsolutePath());
//        List<PokemonItem> pokemonList = sftp.mapFromJSON(typeFile);
//        sftp.getRemoteFile(remoteDirectoryName + "/" + shapeFileName, shapeFile.getAbsolutePath());
//        List<PokemonItem> pokemonList = sftp.mapFromJSON(shapeFile);

        System.out.println(pokemonList.get(150));
    }
}