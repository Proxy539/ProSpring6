package com.apress.prospring6.three.collectioninject;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apress.prospring6.three.nesting.Song;

@Component
public class CollectingBean {
    @Autowired
    List<Song> songList;

    public void printCollections() {
        songList.forEach(s -> System.out.println(s.getTitle()));
    }
}
