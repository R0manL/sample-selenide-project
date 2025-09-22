package com.ccc.hv.qa.api;

import com.ccc.hv.qa.api.pojo.WatermarkTransactionResponseAPI;
import com.ccc.hv.qa.db.services.ChannelDBService;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

import static com.ccc.hv.qa.api.services.CropdusterAPIService.getCropdusterAPIService;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static com.ccc.hv.qa.db.services.ChannelAssociationDBService.getCAIDBy;
import static com.ccc.hv.qa.db.services.ChannelAssociationDBService.isCAExists;
import static com.ccc.hv.qa.db.services.ChannelDBService.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by R0manL on 12/1/22.
 */

@Test(groups = {"api", "regression", "watermarking"})
public class WatermarkTransactionTests {

    @Test
    public void verifyWatermarkTransactionWhenCAExist() {
        final String expectedBuName = ENV_CONFIG.testBusinessUnitName();
        List<Integer> channelIDs = getAllActiveUnlockedChannelsFor(expectedBuName);

        int caID = getFirstExistingCAFrom(channelIDs);
        int chaID = ChannelDBService.getChannelIDBy(caID);
        String expectedChannelName = getChannelNameBy(chaID);

        WatermarkTransactionResponseAPI response = getCropdusterAPIService().getWatermarkTransaction(caID);

        assertThat(response.getBuName())
                .as("Invalid BU name in watermark transaction response.")
                .isEqualTo(expectedBuName);

        assertThat(response.getChannelName())
                .as("Invalid channel name in watermark transaction response.")
                .isEqualTo(expectedChannelName);
    }

    @Test
    public void verifyWatermarkTransactionWhenCANotExist() {
        int nonExistedCaID = getFirstNonExistedCA();
        WatermarkTransactionResponseAPI response = getCropdusterAPIService().getWatermarkTransaction(nonExistedCaID);

        assertThat(response.getBuName())
                .as("Invalid BU name in watermark transaction response.")
                .isEqualTo("No business unit");

        assertThat(response.getChannelName())
                .as("Invalid channel name in watermark transaction response.")
                .isEqualTo("No channel association");
    }

    private int getFirstExistingCAFrom(List<Integer> channelIds) {
        for(int chaId : channelIds) {
            Integer caId = getCAIDBy(chaId);
            if(caId != null) {
                return caId;
            }
        }

        throw new IllegalStateException("No test CA exist. Create at least one.");
    }

    private int getFirstNonExistedCA() {
        Random random = new Random();
        int generatedCAIDsRange = 10000;
        while(true) {
            int randomCaID = random.nextInt(generatedCAIDsRange);
            if(!isCAExists(randomCaID)) {
                return randomCaID;
            }
        }
    }
}
