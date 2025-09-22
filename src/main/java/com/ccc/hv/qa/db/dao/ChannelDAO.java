package com.ccc.hv.qa.db.dao;

import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

/**
 * Created by R0manL on 02/09/20.
 */

public interface ChannelDAO {

    @SqlQuery("select hv_chamas_channel_master_id from hv_channel_master where name =:name")
    @SingleValue
    String getChannelIDBy(@Bind("name") String channelName);

    @SqlQuery("select name from hv_channel_master where hv_chamas_channel_master_id =:id")
    @SingleValue
    String getChannelNameBy(@Bind("id") int id);

    @SqlQuery("select retry_interval_hours from hv_channel_master where name=:name")
    @SingleValue
    Integer getRetryIntervalHours(@Bind("name") String channelName);

    @SqlQuery("select hv_chamas_channel_master_id from hv_channel_master where name like :prefix  || '%' order by hv_chamas_channel_master_id asc")
    List<Integer> getChannelsIdWith(@Bind("prefix") String channelNamePrefix);

    @SqlQuery("select hv_channel_master.hv_chamas_channel_master_id from hv_channel_master join hv_cha_association_detail " +
            "on hv_channel_master.hv_chamas_channel_master_id = hv_cha_association_detail.hv_chamas_channel_master_id " +
            "where hv_tenmas_tenant_master_id =:buId and hv_cha_association_detail.active_yn = true and " +
            "hv_channel_master.active_yn = true  and user_lock_utc IS NULL;")
    List<Integer> getAllActiveUnlockedChannelsFor(@Bind("buId") int buId);

    @SqlQuery("SELECT CASE WHEN EXISTS (select hv_chamas_channel_master_id from hv_channel_master where hv_chamas_channel_master_id=:id ) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    @SingleValue
    boolean hasChannelExistWith(@Bind("id") int channelId);

    @SqlQuery("SELECT CASE WHEN EXISTS (select hv_chamas_channel_master_id from hv_channel_master where name=:name ) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END")
    @SingleValue
    boolean hasChannelExistWith(@Bind("name") String name);

    @SqlQuery("select hv_chamas_channel_master_id from hv_cha_association_detail where hv_caadet_cha_association_id =:caId;")
    @SingleValue
    int getChannelIDBy(@Bind("caId") int caId);
}
