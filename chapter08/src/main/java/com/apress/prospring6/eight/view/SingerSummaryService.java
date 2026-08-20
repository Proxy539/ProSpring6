package com.apress.prospring6.eight.view;

import java.util.stream.Stream;

public interface SingerSummaryService {
    Stream<SingerSummaryRecord> findAllAsRecords();
}
