package com.ccc.hv.qa.db.dao;

import com.ccc.hv.qa.db.pojo.ChannelAssociationDetails;
import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Created by R0manL on 21/08/20.
 */

/**
 * Describes methods to work with HV_TENANT_MASTER table.
 */
public interface ChannelAssociationDAO {

    @SqlQuery("SELECT CASE WHEN EXISTS (select hv_caadet_cha_association_id from hv_cha_association_detail where hv_tenmas_tenant_master_id =:tenant_id and hv_chamas_channel_master_id =:channel_id) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    @SingleValue
    boolean hasChannelAssociationExistFor(@Bind("channel_id") int channelId, @Bind("tenant_id") int tenantId);

    @SqlQuery("SELECT hv_csadet_cha_server_assoc_id FROM hrv.hv_cha_server_assoc_detail " +
            "WHERE hv_chsdet_channel_server_id in " +
            "(SELECT hv_chsdet_channel_server_id FROM hrv.hv_channel_server_detail " +
            "WHERE hv_chamas_channel_master_id in " +
            "(SELECT hv_chamas_channel_master_id FROM hrv.hv_channel_master " +
            "WHERE name =:channelName))")
    @SingleValue
    int getChannelServerAssocIDBy(@Bind("channelName") String channelName);

    @SqlQuery("SELECT hv_caadet_cha_association_id FROM hrv.hv_cha_server_assoc_detail " +
            "WHERE hv_csadet_cha_server_assoc_id =:chnlServerAssociationId")
    @SingleValue
    int getChannelAssociationIDByServerId(@Bind("chnlServerAssociationId") int chnlServerAssociationId);

    @SqlQuery("select hv_caadet_cha_association_id from hv_cha_association_detail left join hv_channel_master on hv_channel_master.hv_chamas_channel_master_id = hv_cha_association_detail.hv_chamas_channel_master_id where hv_channel_master.name=:name")
    @SingleValue
    int getChannelAssociationIDBy(@Bind("name") String channelName);

    @SqlQuery("select count(1) from hv_cha_association_detail where hv_caadet_cha_association_id =:id;")
    @SingleValue
    int isCAExists(@Bind("id") int caID);

    @Nullable
    @SqlQuery("select hv_caadet_cha_association_id from hv_cha_association_detail where hv_chamas_channel_master_id =:channel_id")
    @SingleValue
    Integer getChannelAssociationIDBy(@Bind("channel_id") int channelId);

    @SqlQuery("select * from hv_cha_association_detail where hv_chamas_channel_master_id in (select hv_chamas_channel_master_id from hv_channel_master where hv_channel_master.name=:channelName)")
    @SingleValue
    @RegisterConstructorMapper(ChannelAssociationDetails.class)
    ChannelAssociationDetails getChannelAssociationDetailsFor(@Bind("channelName") @NotNull String channelName);
}
