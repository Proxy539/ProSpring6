package com.apress.prospring6.nine.services;

import com.apress.prospring6.nine.entities.Album;
import com.apress.prospring6.nine.entities.Singer;
import com.apress.prospring6.nine.ex.TitleTooLongException;
import com.apress.prospring6.nine.repos.AlbumRepo;
import com.apress.prospring6.nine.repos.SingerRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class AllServiceImpl implements AllService {

    private final SingerRepo singerRepo;
    private final AlbumRepo albumRepo;

    public AllServiceImpl(SingerRepo singerRepo, AlbumRepo albumRepo) {
        this.singerRepo = singerRepo;
        this.albumRepo = albumRepo;
    }

    @Transactional(readOnly = true)
    @Override
    public Stream<Singer> findAllWithAlbums() {
        var singers = singerRepo.findAll();
        return singers.peek(s -> s.setAlbums(albumRepo.findBySinger(s).collect(Collectors.toSet())));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Singer> findByIdWithAlbums(Long id) {
        var singerOpt = singerRepo.findById(id);
        singerOpt.ifPresent(s -> s.setAlbums(albumRepo.findBySinger(s).collect(Collectors.toSet())));

        return singerOpt;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void update(Singer singer) {
        singerRepo.save(singer);
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Long countSingers() {
        return singerRepo.countAllSingers();
    }

    @Transactional(rollbackFor = TitleTooLongException.class)
    @Override
    public void saveSingerWithAlbums(Singer s, Set<Album> albums) throws TitleTooLongException {
        var singer = singerRepo.save(s);
        if (singer != null) {
            albums.forEach(a -> a.setSinger(singer));
            albumRepo.save(albums);
        }
    }
}
