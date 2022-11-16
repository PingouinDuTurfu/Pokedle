package fr.project.pokedle.init;


import fr.project.pokedle.init.loader.PokemonItem;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;


@Component
public class Initialisation implements ApplicationListener<ContextRefreshedEvent> {

    private static final String remoteFileName = "/home/***REMOVED***/pokedle/dataset/src/dataSet/pokemonFormated.json";
    private static final String localFileName = "pokemons.json";

    private static File localFile;

    static {
        try {
            URL url = Initialisation.class.getClassLoader().getResource(localFileName);
            if(url == null) {
                throw new FileNotFoundException("File " + localFileName + " not found");
            }
            localFile = Paths.get(url.toURI()).toFile();
        } catch (URISyntaxException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        SFTPFileTransfer sftp = new SFTPFileTransfer();

        sftp.getRemoteFile(remoteFileName, localFile.getAbsolutePath());
        List<PokemonItem> pokemonList = sftp.mapFromJSON(localFile);

        System.out.println(pokemonList.get(150));
    }
}