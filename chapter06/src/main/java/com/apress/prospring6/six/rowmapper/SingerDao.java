package com.apress.prospring6.six.rowmapper;

import com.apress.prospring6.six.records.Singer;

import java.util.Set;

public interface SingerDao {
    Set<Singer> findAllWithAlbums();
}
