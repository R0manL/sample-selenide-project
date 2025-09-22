package com.ccc.hv.qa.ui.pojos;

import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

@Builder
@Getter
public class CreateTagEntity {
    @NotNull String tagName;
    @NotNull Path csvFilePath;
}
