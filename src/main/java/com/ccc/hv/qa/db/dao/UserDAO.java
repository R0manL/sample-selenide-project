package com.ccc.hv.qa.db.dao;

import com.ccc.hv.qa.db.pojo.UserDB;
import org.jdbi.v3.sqlobject.SingleValue;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * Created by R0manL on 25/09/20.
 */

public interface UserDAO {

    @SqlQuery("select * from hv_user_master where username =:name;")
    @SingleValue
    @RegisterConstructorMapper(UserDB.class)
    UserDB getUserBy(@Bind("name") String userName);

    @SqlQuery("SELECT CASE WHEN EXISTS (select * from hv_user_master where username=:name) THEN CAST(1 AS BIT) ELSE CAST(0 AS BIT) END ")
    @SingleValue
    boolean isUserExist(@Bind("name") String userName);

    @SqlUpdate("INSERT INTO HV_USER_MASTER (HV_USRMAS_USER_MASTER_ID,PASSWORD_HASH, USERNAME, FIRST_NAME, LAST_NAME, PHONE_NUMBER," +
            "ACCOUNT_EXPIRED_YN,ACCOUNT_LOCKED_YN,ACTIVE_YN,PASSWORD_EXPIRED_YN,HV_ROLMAS_ROLE_MASTER_ID) " +
            "VALUES (nextval('user_master_usrmas_user_master_id_seq'::regclass), :password_hash, :username, :first_name, :last_name, " +
            ":phone_number, :account_expired_yn, :account_locked_yn, :active_yn, :password_expired_yn, :hv_rolmas_role_master_id)")
    @RegisterBeanMapper(UserDB.class)
    void createUser(@BindBean UserDB user);
}
