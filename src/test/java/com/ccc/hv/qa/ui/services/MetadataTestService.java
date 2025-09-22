package com.ccc.hv.qa.ui.services;

import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

import static com.ccc.hv.qa.file.services.OnixFileService.ONIX_FILE_EXTENSION;
import static com.ccc.hv.qa.utils.FileOpsUtils.cloneFile;

/**
 * Created by R0manL on 5/18/21.
 */

public class MetadataTestService extends AssetTestService {
    private Path filePath;


    public MetadataTestService(@NotNull String relFilePath) {
        super(relFilePath);
    }

    @Override
    public AssetTestService cloneFileWith(@NotNull String newRecordReference) {
        this.filePath = cloneFile(newRecordReference + ONIX_FILE_EXTENSION, this.filePath.toString());

        return this;
    }
}
