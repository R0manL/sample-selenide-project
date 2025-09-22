package com.ccc.hv.qa.configs;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;

/**
 * Created by R0manL on 03/08/20.
 *
 * Description. Read environment configuration from *.properties file for defined environment.
 *
 * Environment (qa, ephemeral) can be defined from commandline (via sys props). Example: 'mvn -Denv=qa test'
 * If env has not defined, means that we run tests from IDE, default.properties will be used.
 *
 * Note. Please, note that load strategy = MERGE (more info http://owner.aeonbits.org/docs/loading-strategies/)
 *       In this case, for every property all the specified URLs will be queries, and the first resource defining
 *       the property will prevail. More in detail, this is what will happen:
 *       - First, it will try to load the given property from system properteis (maven parameters);
 *         if the given property is found the associated value will be returned.
 *       - Then it will try to load the given property from ~/resources/${env}-env.properties;
 *         if the property is found the value associated will be returned.
 *       - Finally, it will try to load the given property from the ~/resources/default.properties;
 *
 * Note. If any property has missed in all *.property files, NullPointerException will be shown when you try to read it
 *       for the first time.
 */

@LoadPolicy(LoadType.MERGE)
@Sources({
        "system:properties",
        "classpath:${env}-env.properties",
        "classpath:default.properties"})
public interface EnvConfig extends Config {

   EnvConfig ENV_CONFIG = ConfigFactory.create(EnvConfig.class);

   /* Test constants */
   @Key("test.suite.name")
   String testSuiteName();

   @Key("account.name")
   String accountName();

   @Key("account.name2")
   String accountName2();

   @Key("test.bu.name")
   String testBusinessUnitName();

   @Key("alpha.code.prefix")
   String alphaCodePrefix();

   @Key("record.source.name")
   String recordSourceName();

   @Key("test.bu.name2")
   String testBusinessUnitName2();

   @Key("alpha.code.prefix2")
   String alphaCodePrefix2();

   @Key("record.source.name2")
   String recordSourceName2();


   @Key("test.notification.email")
   String notificationEmail();

   @Key("association.srv.ftp.username")
   String associationServerFTPUsername();

   @Key("association.srv.ftp.password")
   String associationServerFTPPassword();

   @Key("association.srv.sftp.username")
   String associationServerSFTPUsername();

   @Key("association.srv.sftp.password")
   String associationServerSFTPPassword();

   @Key("association.srv.ftps.username")
   String associationServerFTPSUsername();

   @Key("association.srv.ftps.password")
   String associationServerFTPSPassword();

   @Key("association.srv.itms.username")
   String associationServerITMSUsername();

   @Key("association.srv.itms.password")
   String associationServerITMSPassword();

   @Key("association.srv.itms.provider")
   String associationServerITMSProvider();

   @Key("bu.publisher.id")
   String buPublisherId();

   @Key("bu.publisher.pin")
   String buPublisherPin();

   @Key("bu.user.id")
   String buUserId();

   @Key("bu.user.pass")
   String buUserPass();


   /* Third party services */
   @Key("test.gmail.username")
   String testGmailAddress();

   @Key("test.gmail.password")
   String testGmailPassword();


   /* Users */
   @Key("super.admin.email")
   String superAdminEmail();

   @Key("super.admin.password")
   String superAdminPassword();


   /* UI/API URLs */
   @Key("ui.url")
   String uiUrl();

   @Key("hrv.api.url")
   String hrvApiUrl();

   @Key("hrv.reaper.host.url")
   String hrvReaperHostUrl();

   @Key("hrv.ingest.persistence.api.url")
   String hrvIngestPersistenceApiUrl();

   @Key("hrv.time.bandit.api.url")
   String hrvTimeBanditApiUrl();

   @Key("hrv.time.bandit.srv.url.01")
   String hrvTimeBanditSrvUrl01();

   @Key("hrv.time.bandit.srv.url.02")
   String hrvTimeBanditSrvUrl02();

   @Key("rebuild.search.index.srv.url.01")
   String rebuildSearchIndexSrvUrl01();

   @Key("rebuild.search.index.srv.url.02")
   String rebuildSearchIndexSrvUrl02();

   @Key("current.event.srv.url.01")
   String currentEventSrvUrl01();

   @Key("current.event.srv.url.02")
   String currentEventSrvUrl02();

   @Key("hrv.cropduster.api.url")
   String hrvCropdusterApiUrl();


   /* Mailstrom box URL */
   @Key("mailstrom.box.url")
   String mailsrtomBoxUrl();


   /* DB properties */
   @Key("hrv.db.url")
   String hrvDBUrl();

   @Key("hrv.db.username")
   String hrvDBUsername();

   @Key("hrv.db.password")
   String hrvDBPassword();

   @Key("zipline.db.url")
   String ziplineDBUrl();

   @Key("zipline.db.username")
   String ziplineDBUsername();

   @Key("zipline.db.password")
   String ziplineDBPassword();

   /* FTP properties */
   @Key("crushftp.hostname")
   String crushFtpHostName();

   @Key("crushftp.ftp.port")
   int crushFtpFTPPort();

   @Key("crushftp.sftp.port")
   int crushFtpSFTPPort();

   @Key("crushftp.ftps.port")
   int crushFtpFTPSPort();

   @Key("crushftp.upload.username")
   String crushFtpUploadUsername();

   @Key("crushftp.upload.password")
   String crushFtpUploadPassword();

   @Key("crushftp.upload.username2")
   String crushFtpUploadUsername2();

   @Key("crushftp.upload.password2")
   String crushFtpUploadPassword2();


   /* Keycloak */
   @Key("keycloak.reaper.client.id")
   String keycloakReaperClientId();

   @Key("keycloak.reaper.client_secret")
   String keycloakReaperClientSecret();

   @Key("keycloak.reaper.username")
   String keycloakReaperUsername();

   @Key("keycloak.reaper.password")
   String keycloakReaperPassword();


   @Key("keycloak.admin.client.id")
   String keycloakAdminClientId();

   @Key("keycloak.admin.username")
   String keycloakAdminUsername();

   @Key("keycloak.admin.password")
   String keycloakAdminPassword();

   @Key("zipline.srv.url")
   String ziplineServerUrl();

   @Key("keycloak.portal.srv.url")
   String keycloakPortalUrl();

   @Key("keycloak.auth.srv.url")
   String keycloakAuthUrl();


   /* Timeouts */
   @Key("web.element.refresh.timeout")
   long webElmRefreshTimeout();

   @Key("web.element.load.timeout")
   long webElmLoadTimeout();

   @Key("page.load.timeout")
   long pageLoadTimeout();

   @Key("awaitility.timeout")
   long awaitilityTimeout();

   @Key("awaitility.poll.interval")
   long awaitilityPollInterval();

   @Key("max.distribution.timeout")
   long maxDistributionTimeout();

   @Key("restassured.http.connection.timeout")
   int restassuredHttpConnectionTimeout();

   @Key("restassured.http.socket.timeout")
   int restassuredHttpSocketTimeout();

   @Key("restassured.http.connection-manager.timeout")
   int restassuredHttpConnectionManagerTimeout();


   /* Constants */
   @Key("server.timezone")
   String serverTimeZone();


   /* Maven parameters */
   @Key("selenoid.hostname")
   String selenoidHostName();

   @Key("threadCount")
   @DefaultValue("1")
   int threadCount();

   @Key("record.remote.video")
   @DefaultValue("true")
   boolean recordRemoteVideo();
}