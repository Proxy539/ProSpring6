package com.apress.prospring6.four.destroymethod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class FileManager {
    private static Logger logger = LoggerFactory.getLogger(FileManager.class);
    private Path file;

    public FileManager() {
        logger.info("Creating bean of type {}", FileManager.class);

        try {
            file = Files.createFile(Path.of("sample"));
        } catch (IOException e) {
            logger.error("Could not create file");
        }
    }

    private void destroyMethod() throws IOException {
        logger.info("Destryoing bean of type {}", FileManager.class);
        if (file != null) {
            Files.deleteIfExists(file);
        }
    }
}

@Configuration
class DemoConfig {
    @Bean(destroyMethod = "destroyMethod")
    public FileManager fileManager() {
        return new FileManager();
    }
}

public class DestroyMethodDemo {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(DemoConfig.class);
        ctx.close(); // needed to close the context
    }
}
