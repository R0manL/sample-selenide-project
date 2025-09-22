package com.ccc.hv.qa.file.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by R0manL on 11/5/20.
 */

@Getter
@Builder
public class CsvFeedFile {
    @NonNull
    private final List<OnixProductFromCsv> products;
    @NonNull
    private final Path path;
}
