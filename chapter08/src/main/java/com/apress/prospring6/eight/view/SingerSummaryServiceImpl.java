package com.apress.prospring6.eight.view;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service("singerSummaryService")
@Repository
@Transactional(readOnly = true)
public class SingerSummaryServiceImpl implements SingerSummaryService {

    @PersistenceContext
    private EntityManager em;

    public static final String ALL_SINGER_SUMMARY_RECORD_JPQL_QUERY = """
            select s.firstName, s.lastName, a.title from Singer s
            left join s.albums a
            where a.releaseDate=(select max(a2.releaseDate) from Album a2 where a2.singer.id = s.id)""";

    public static final String ALL_SINGER_SUMMARY_JPQL_QUERY = """
            select new com.apress.prospring6.eight.view.SingerSummary(
            s.firstName, s.lastName, a.title) from Singer s
            left join s.albums a
            where a.releaseDate=(select max(a2.releaseDate) from Album a2 where a2.singer.id = s.id)
            """;

    @Override
    public Stream<SingerSummaryRecord> findAllAsRecords() {
        return em.createQuery(ALL_SINGER_SUMMARY_RECORD_JPQL_QUERY)
                .getResultList()
                .stream()
                .map(obj -> {
                    Object[] values = (Object[]) obj;
                    return new SingerSummaryRecord((String) values[0], (String) values[1], (String) values[2]);
                });
    }

}
