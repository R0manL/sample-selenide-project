package com.ccc.hv.qa.db.dao;

import com.ccc.hv.qa.db.pojo.AssetDistrStatusDetailsDB;
import com.ccc.hv.qa.db.pojo.AssetPrecompDetailsDB;
import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface AssetDAO {

    @SqlQuery("SELECT action FROM hrv.hv_asset_detail WHERE hv_astmas_asset_master_id  in " +
            "(SELECT hv_astmas_asset_master_id FROM hrv.hv_asset_master " +
            "WHERE hv_prdmas_product_master_id =:productId and source_name =:assetFileName) " +
            "order by version desc;")
    @SingleValue
    String getAssetActionStatus(@Bind("productId") int productId,
                                @Bind("assetFileName") String assetFileName);

    @SqlQuery("SELECT time_stamp FROM hrv.hv_asset_detail WHERE hv_astmas_asset_master_id  in " +
            "(SELECT hv_astmas_asset_master_id FROM hrv.hv_asset_master " +
            "WHERE hv_prdmas_product_master_id =:productId and source_name =:assetFileName) " +
            "order by version asc limit 1;")
    @SingleValue
    String getFirstAddedTimeStamp(@Bind("productId") int productId,
                                  @Bind("assetFileName") String assetFileName);


    @SqlQuery("SELECT time_stamp FROM hrv.hv_asset_detail WHERE hv_astmas_asset_master_id  in " +
            "(SELECT hv_astmas_asset_master_id FROM hrv.hv_asset_master " +
            "WHERE hv_prdmas_product_master_id =:productId and source_name =:assetFileName) " +
            "order by version desc limit 1;")
    @SingleValue
    String getLastRevisedTimeStamp(@Bind("productId") int productId,
                                   @Bind("assetFileName") String assetFileName);

    @SqlQuery("SELECT distributed_path, hv_asset_status_detail.hv_atsref_asset_status_ref_id, processing_errors, hv_asset_status_detail.time_stamp, hv_asset_master.source_name " +
            "            FROM hv_asset_master" +
            "            JOIN hv_asset_detail ON hv_asset_master.hv_astmas_asset_master_id = hv_asset_detail.hv_astmas_asset_master_id" +
            "            JOIN hv_asset_status_detail ON hv_asset_detail.hv_astdet_asset_detail_id = hv_asset_status_detail.hv_astdet_asset_detail_id" +
            "            JOIN hv_channel_master ON hv_asset_status_detail.hv_chamas_channel_master_id = hv_channel_master.hv_chamas_channel_master_id" +
            "            where source_name =:assetFileName and hv_channel_master.name =:channelName" +
            "            and hv_pkgmas_pkg_master_id is null and hv_dismas_distrib_master_id is not null;")
    @RegisterConstructorMapper(AssetDistrStatusDetailsDB.class)
    List<AssetDistrStatusDetailsDB> getAssetDistributionStatusDetails(@Bind("channelName") String channelName, @Bind("assetFileName") String assetFileName);

    @SqlQuery("SELECT distributed_path, hv_asset_status_detail.hv_atsref_asset_status_ref_id, processing_errors, hv_asset_status_detail.time_stamp, hv_asset_master.source_name" +
            "            FROM hv_asset_master" +
            "            JOIN hv_asset_detail ON hv_asset_master.hv_astmas_asset_master_id = hv_asset_detail.hv_astmas_asset_master_id" +
            "            JOIN hv_asset_status_detail ON hv_asset_detail.hv_astdet_asset_detail_id = hv_asset_status_detail.hv_astdet_asset_detail_id" +
            "            JOIN hv_channel_master ON hv_asset_status_detail.hv_chamas_channel_master_id = hv_channel_master.hv_chamas_channel_master_id" +
            "            where source_name =:assetFileName and hv_channel_master.name =:channelName " +
            "            and hv_asset_status_detail.distribution_type = 'EXTERNAL';")
    @RegisterConstructorMapper(AssetDistrStatusDetailsDB.class)
    List<AssetDistrStatusDetailsDB> getAssetExternalDistributionStatusDetails(@Bind("channelName") String channelName, @Bind("assetFileName") String assetFileName);

    @SqlQuery("SELECT distributed_path, hv_asset_status_detail.hv_atsref_asset_status_ref_id, processing_errors, hv_asset_status_detail.time_stamp, hv_asset_master.source_name " +
            "            FROM hv_asset_master" +
            "            JOIN hv_asset_detail ON hv_asset_master.hv_astmas_asset_master_id = hv_asset_detail.hv_astmas_asset_master_id" +
            "            JOIN hv_asset_status_detail ON hv_asset_detail.hv_astdet_asset_detail_id = hv_asset_status_detail.hv_astdet_asset_detail_id" +
            "            JOIN hv_channel_master ON hv_asset_status_detail.hv_chamas_channel_master_id = hv_channel_master.hv_chamas_channel_master_id" +
            "            where source_name =:assetFileName and hv_channel_master.name =:channelName and version =:version" +
            "            and hv_pkgmas_pkg_master_id is null and hv_dismas_distrib_master_id is not null;")
    @RegisterConstructorMapper(AssetDistrStatusDetailsDB.class)
    AssetDistrStatusDetailsDB getAssetDistributionStatusDetails(@Bind("channelName") String channelName,
                                                                      @Bind("assetFileName") String assetFileName,
                                                                      @Bind("version") int version);

    @SqlQuery("SELECT distributed_path, hv_asset_status_detail.hv_atsref_asset_status_ref_id, processing_errors, hv_asset_status_detail.time_stamp, hv_asset_master.source_name " +
            "            FROM hv_asset_master" +
            "            JOIN hv_asset_detail ON hv_asset_master.hv_astmas_asset_master_id = hv_asset_detail.hv_astmas_asset_master_id" +
            "            JOIN hv_asset_status_detail ON hv_asset_detail.hv_astdet_asset_detail_id = hv_asset_status_detail.hv_astdet_asset_detail_id" +
            "            JOIN hv_channel_master ON hv_asset_status_detail.hv_chamas_channel_master_id = hv_channel_master.hv_chamas_channel_master_id" +
            "            where source_name =:assetFileName and hv_channel_master.name =:channelName" +
            "            and hv_pkgmas_pkg_master_id is not null and hv_dismas_distrib_master_id is null;")
    @RegisterConstructorMapper(AssetDistrStatusDetailsDB.class)
    List<AssetDistrStatusDetailsDB> getAssetDistributionStatusDetailsForPacking(@Bind("channelName") String channelName, @Bind("assetFileName") String assetFileName);


    @SqlQuery("SELECT hv_astmas_asset_master_id FROM hrv.hv_asset_master " +
            "WHERE source_name =:assetFileName")
    @SingleValue
    Integer getAssetIdBy(@Bind("assetFileName") String assetFileName);

    @SqlQuery("SELECT hv_astdet_asset_detail_id FROM hv_precompdd_master " +
            "WHERE hv_astmas_asset_master_id =:assetId and hv_csadet_cha_server_assoc_id =:channelServerAssocId")
    @SingleValue
    int getAssetDetailId(@Bind("channelServerAssocId") int channelServerAssocId,
                         @Bind("assetId") int assetId);

    @SqlQuery("SELECT hv_csadet_cha_server_assoc_id FROM hv_precompdd_master WHERE hv_astmas_asset_master_id =:assetId")
    List<Integer> getChannelServerAssocIDsBy(@Bind("assetId") int assetId);

    @SqlQuery("SELECT CASE WHEN EXISTS (SELECT ppjmas_id FROM hrv.pre_package_job_master " +
            "WHERE ppjmas_status =:expectedStatus and hv_worgrp_work_group_master_id in (SELECT worker_grouping_id FROM hv_precompdd_master " +
            "WHERE hv_astmas_asset_master_id =:assetId and hv_csadet_cha_server_assoc_id =:channelServerAssocId ))" +
            "THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    @SingleValue
    boolean hasAssetPrePackageStatus(@Bind("channelServerAssocId") int channelServerAssocId,
                                     @Bind("assetId") int assetId,
                                     @Bind("expectedStatus") String expectedStatus);

    @SqlQuery("SELECT CASE WHEN EXISTS (SELECT wajhis_id FROM hrv.watermark_job_history " +
            "WHERE hv_astdet_asset_detail_id =:assetDetailId and wajmas_status =:status and wajmas_phase_complete=:phase ) " +
            "THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    @SingleValue
    boolean isRecordWithStatusAndPhaseExistInWatermarkJobHistoryTable(@Bind("assetDetailId") int assetDetailId,
                                                                      @Bind("status") String status,
                                                                      @Bind("phase") String phase);

    @SqlQuery("SELECT hv_asstyp_asset_type_id FROM hv_asset_type_ref where description =:assetType")
    @SingleValue
    int getAssetTypeIdBy(@Bind("assetType") String assetType);

    @SqlQuery("SELECT hv_precdd_precompdd_id, hv_precompdd_master.hv_astmas_asset_master_id,\n" +
            "       hv_precompdd_master.hv_tenmas_tenant_master_id, hv_precompdd_master.hv_asstyp_asset_type_id,\n" +
            "       release_date_utc, delivery_offset, pre_computed_date_utc, in_flight_time_utc,\n" +
            "       priority, hv_precompdd_master.hv_csadet_cha_server_assoc_id, hv_usrmas_user_master_id,\n" +
            "       dist_failure_count, workflow_status, release_date_type,\n" +
            "       distribution_type, hv_prdtyp_product_type_id, worker_grouping_id\n" +
            "   FROM hv_precompdd_master\n" +
            "    JOIN hv_asset_master ON hv_precompdd_master.hv_astmas_asset_master_id = hv_asset_master.hv_astmas_asset_master_id\n" +
            "    JOIN hv_cha_server_assoc_detail hcsad on hv_precompdd_master.hv_csadet_cha_server_assoc_id = hcsad.hv_csadet_cha_server_assoc_id\n" +
            "    JOIN hv_channel_server_detail hcsd on hcsad.hv_chsdet_channel_server_id = hcsd.hv_chsdet_channel_server_id\n" +
            "    JOIN hv_channel_master hcm on hcsd.hv_chamas_channel_master_id = hcm.hv_chamas_channel_master_id\n" +
            "    WHERE hv_asset_master.source_name =:assetFileName and hcm.name =:channelName")
    @SingleValue
    @RegisterConstructorMapper(AssetPrecompDetailsDB.class)
    AssetPrecompDetailsDB getAssetPrecompDetailsFor(@Bind("channelName") @NotNull String channelName, @Bind("assetFileName") @NotNull String assetFileName);
}
