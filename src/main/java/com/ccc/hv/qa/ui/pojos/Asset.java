package com.ccc.hv.qa.ui.pojos;


import com.ccc.hv.qa.ui.enums.AssetType;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Asset {
    @NonNull
    private final AssetType type;
    private final String fileName;
    private LocalDateTime firstAdded;
    private LocalDateTime lastRevisioned;
    private final String fileSize;

    @Override
    public String toString() {
        return "Asset{" +
                "type=" + type +
                ", fileName='" + fileName + '\'' +
                ", firstAdded=" + firstAdded +
                ", lastRevisioned=" + lastRevisioned +
                ", fileSize='" + fileSize + '\'' +
                '}';
    }
}
