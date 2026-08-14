package com.apress.prospring6.four;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

public class ResourceDemo {

    private static Logger logger = LoggerFactory.getLogger(ResourceDemo.class);

    public static void main(String[] args) throws Exception {
        var ctx = new AnnotationConfigApplicationContext();

        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        Path filePath = Files.createFile(Path.of(baseDir.getAbsolutePath(), "test.txt"));
        Files.writeString(filePath, "Hello World!");
        filePath.toFile().deleteOnExit();

        Resource res1 = ctx.getResource("file://" + filePath);
        displayInfo(res1);

        Resource res2 = ctx.getResource("classpath:test.txt");
        displayInfo(res2);

        Resource res3 = ctx.getResource("http://iuliana-cosmina.com");
        displayInfo(res3);
    }

    private static void displayInfo(Resource res) throws Exception {
        logger.info("Resource class: {}", res.getClass());
        logger.info("Resource URL content: {}",
                new BufferedReader(new InputStreamReader((InputStream) res.getURL().getContent())).lines().parallel()
                        .collect(Collectors.joining("\n")));
        logger.info("----------");
    }

}
