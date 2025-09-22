package com.ccc.hv.qa.db.dao;

import com.ccc.hv.qa.db.pojo.PhaseTrackMasterDB;
import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;


public interface BatchDAO {

    @SqlQuery("SELECT CASE WHEN EXISTS (SELECT hv_batmas_batch_master_id FROM hv_batch_master WHERE original_file_name =:origFileName) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    @SingleValue
    boolean isAnyBatchRecordExistFor(@Bind("origFileName") String originalFileName);

    @SqlQuery("SELECT CASE WHEN EXISTS (SELECT hv_batmas_batch_master_id FROM hv_batch_master WHERE original_file_name =:origFileName AND batch_data IS NOT NULL) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    @SingleValue
    boolean isBatchMasterIdExistsFor(@Bind("origFileName") String originalFileName);

    @SqlQuery("SELECT hv_batmas_batch_master_id FROM hv_batch_master WHERE original_file_name =:origFileName AND batch_data IS NOT NULL")
    @SingleValue
    int getBatchMasterIdBy(@Bind("origFileName") String originalFileName);

    @SqlQuery("SELECT hv_batmas_batch_master_id FROM hv_batch_master WHERE original_file_name =:origFileName AND batch_data IS NOT NULL")
    List<Integer> getBatchMasterIdListBy(@Bind("origFileName") String originalFileName);

    @SqlQuery("SELECT hv_bstref_batch_status_ref_id FROM hv_batch_master WHERE original_file_name =:origFileName AND batch_data IS NOT NULL")
    @SingleValue
    int getBatchStatusIdBy(@Bind("origFileName") String originalFileName);

    @SqlQuery("SELECT hv_validation_errors FROM hv_batch_detail WHERE hv_batmas_batch_master_id =:batchID")
    @SingleValue
    String getValidationErrorBy(@Bind("batchID") int batchId);

    @SqlQuery("SELECT hv_normalize_batch_id FROM hv_batch_track_master WHERE hv_batch_id =:batchID")
    @SingleValue
    int getNormalizeBatchIdBy(@Bind("batchID") int batchId);

    @SqlQuery("SELECT validation_message FROM hv_batch_odd_detail WHERE hv_csadet_cha_server_assoc_id =:chaSrvAssocId")
    @SingleValue
    String getBatchOddValidationMsgBy(@Bind("chaSrvAssocId") int chaServerAssocId);

    @SqlQuery("SELECT hv_batch_track_master_id FROM hv_batch_track_master WHERE hv_batch_id =:batchID")
    @SingleValue
    int getBatchTrackMasterIdBy(@Bind("batchID") int batchId);

    @SqlQuery("SELECT * FROM hv_phase_track_master WHERE hv_batch_track_master_id =:batchTrackMasterId AND phase =:phase")
    @RegisterConstructorMapper(PhaseTrackMasterDB.class)
    PhaseTrackMasterDB getPhaseTrackMasterIdBy(@Bind("batchTrackMasterId") int batchTrackMasterId,
                                @Bind("phase") String phase);

    @SqlQuery("select hv_batmas_batch_master_id from hv_batch_master where hv_tenmas_tenant_master_id=:tenantID and original_file_name=:origFileName")
    @SingleValue
    int getBatchMasterIDBy(@Bind("tenantID") int tenantID, @Bind("origFileName") String originalFileName);
}
