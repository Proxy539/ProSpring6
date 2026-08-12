package com.apress.prospring6.three.collectioninject;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.apress.prospring6.three.nesting.Song;

@Configuration
public class CollectionConfig {

    @Bean
    public List<Song> list() {
        return List.of(
                new Song("Not the end"),
                new Song("Rise Up"));
    }

    @Bean
    public Song song1() {
        return new Song("Here's to hoping");
    }

    @Bean
    public Song song2() {
        return new Song("Wishing the best for you");
    }
}
