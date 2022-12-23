package fr.project.pokedle.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Service
public class ImageReaderService {

    @Value("classpath:static/asset/**")
    private Resource[] resources;

    public InputStream getFileFromResource(String filename) {
        InputStream inputStream = null;
        try {
            inputStream = Arrays.stream(resources)
                    .filter(resource -> {
                        try {
                            return resource.getURI().toString().endsWith(filename);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .findFirst()
                    .orElseThrow(() -> new FileNotFoundException("File not found"))
                    .getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }
}
