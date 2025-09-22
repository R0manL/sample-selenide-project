# Sample Selenide automation testing project.

This document describes architecture, how to setup, execute and analyze results of automated tests execution for Hrv project.


Env. requirements:
- java 8+
- browser: chrome (latest version)
- screen resolution (min): 1366 x 768
- maven 3.6+.

## Quick Start
1. Create a new `Project from Version Control`.
2. Copy /src/main/.../sources/settings.xml to ${user.home}/.m2/ folder. [How to](https://maven.apache.org/settings.html#Quick_Overview).
3. run mvn install.

Note. To run tests from IDE, '-parameters' compiler flag must be used. To set this parameter in IntelliJ Idea, go to: 
Preferences > Build, Execution, Deployment > Compiler > Java Compiler, and set: Additional command line parameters = -parameters


## Framework's architecture
```
├── allure-results - stores all allure results. Used for generating html report.
├── pom.xml
├── readme.md - current readme file
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── ccc
│   │   │           └── hv
│   │   │               └── qa
│   │   │                   ├── api - all related to API
│   │   │                   │   ├── data - test data that used for api
│   │   │                   │   └── services - methods that implements business logic 
│   │   │                   ├── configs
│   │   │                   │   └── EnvConfig.java - interface that read and parse *.properties
│   │   │                   ├── db - all related to DB (postgress)
│   │   │                   │   ├── dao - contains queries that map DB tables/columns with java methods
│   │   │                   │   │   └── TenantDAO.java - interface for tenant table mapping
│   │   │                   │   ├── data - contains predefined data
│   │   │                   │   └── services - methods that used in tests
│   │   │                   │       ├── DBConnection.java
│   │   │                   │       └── TenantDBService.java
│   │   │                   ├── ui - all related to UI
│   │   │                   │   ├── data - predefined data
│   │   │                   │   │   ├── PredefinedAccounts.java
│   │   │                   │   │   ├── PredefinedAddresses.java
│   │   │                   │   │   ├── PredefinedBusinessUnits.java
│   │   │                   │   │   ├── PredefinedUsers.java
│   │   │                   │   │   ├── enums
│   │   │                   │   │   │   ├── IngestMode.java
│   │   │                   │   │   │   └── UserRole.java
│   │   │                   │   │   └── pojos - describes test entities
│   │   │                   │   │       ├── Account.java
│   │   │                   │   │       ├── Address.java
│   │   │                   │   │       ├── BusinessUnit.java
│   │   │                   │   │       ├── Email.java
│   │   │                   │   │       └── User.java
│   │   │                   │   └── pages - page objects that covers business logic
│   │   │                   │       ├── AddUserPage.java
│   │   │                   │       ├── BusinessUnitAddPage.java
│   │   │                   │       ├── HomePage.java
│   │   │                   │       ├── LoginPage.java
│   │   │                   │       ├── PageBase.java
│   │   │                   │       ├── TopMenu.java
│   │   │                   │       └── UserNewPasswordPage.java
│   │   │                   └── utils - utility methods
│   │   │                       ├── EmailUtils.java
│   │   │                       └── StringUtils.java
│   │   └── resources
│   │       ├── allure.properties - allure reporting properties
│   │       ├── default.properties - common properties (for all envs), props that used for local (from IDE) execution
│   │       ├── ephemeral-env.properties - ephemeral env specific properties
│   │       ├── qa-env.properties - qa env specific props
│   │       └── simplelogger.properties - slf4 logger config props
│   └── test - all tests and related data are here
│       ├── java
│       │   └── com
│       │       └── ccc
│       │           └── hv
│       │               └── qa
│       │                   ├── api - contains API tests
│       │                   └── ui - contains UI tests
│       │                       ├── LoginPageTests.java
│       │                       ├── SmokeTests.java - contains smoke tests
│       │                       ├── RegressionTests - contains regression tests
│       │                       ├── NewEnvironmentSetupScript - contains script for setup a new (clear) tests environment
│       │                       ├── CleanupScripts - contains cleanup script for removing test: products, channel, CAs, etc.
│       │                       ├── UITestBase.java
│       │                       ├── businessUnit
│       │                       │   └── addBusinessUnitTests.java
│       │                       ├── data - contains test entities (users, BUs, etc.) that used during test execution
│       │                       │   ├── Addresses.java
│       │                       │   ├── BusinessUnits.java
│       │                       │   └── Users.java
│       └── resources - test related resources (test assets, content, collateral, onix) files.
└── svnignore - list files / folders that sould be ignored
```


## 3d party libs

- owner - used to simplify work with *.properties, more [info](http://owner.aeonbits.org/docs/welcome/)
- lombok - used to simplify builder classes generation for Users, BusinessUnits, etc., more [info](https://projectlombok.org/features/Builder)
- jdbi 3 - used for simple connection to DB, [more info](https://jdbi.org/#_maven_setup)


## Setup
All properties are stored in */properties files located in /src/main/resources folder.
default.properties - contains all general props that used for local execution (from IDE).
qa-env.properties, ephemeral-env.properties - contains ONLY (in additiona to general props) environment specific properties;

### IDE
 - lombok. Install the plugin;
 

## Execution 

To run all test with chrome from commnadline:
```
mvn test
```

### Parameters
For test execution we can use mix of: [testng](https://testng.org/doc/documentation-main.html#running-testng), [selenide](https://selenide.org/javadoc/current/com/codeborne/selenide/Configuration.html) or parameters defined in /resource/*.properties files.

Parameter | Values | Description |
--- | --- | --- |
-Dtest | [test class names separated by ','] | execute tests by classname |
-Dgroups | [group's name separated by ','] | execute tests (e.g UI, API) marked with specific group name, example: @Test (groups = { "ui" }) |
-DthreadCount | [int value] | define number of thread for parallel execution (e.g. -DthreadCount=5), default = 1 |
-Denv | qa | defines which environment specific properties (urls, credentials) will be used |
-Dselenoid.hostname | [url] | Selenoid hostname, used for remote execution (e.g. http://amlnx-ldq-sln01.digital.prscoad.com) |

####Examples
- execute tests from one class:
```
mvn test -Dtest=LoginPageTests
```

### Test groups
- ui - set of GUI tests
- api - set of API tests
- smoke - set of smoke tests
- regression - set of regression tests
- negative - set of tests with negative scenarios
- singleODD, batchODD, autoDistribution - sets by type of distribution
- watermarking - set of tests where watermarking is verified
- distributionPathOptions, feedDistributionOptions - sets by distribution path options
- caServerSettings - set of tests where CA server settings are verified
- onSaleDate, onsetOffset - set of tests with on sale and on set off set date
- publicationDateFilter - set of tests with publication date filter on CA 


Defining groups in *-groups*, *-excludedGroups*, *-includedgroups* we can flexibly define which tests we want to execute.
```
mvn -Dgroups=ui -DexcludedGroups=login -Dincludedgroups=watermark test
```
 


## Reporting

### Allure report
Allure 2 has used for automated tests results generation. By default, only *.json files, screenshots and pagesource files are creating.
For generating *.html report, navigate to project's root dir and run command:

```
allure serve ./allure-results
```
Report will be generated and opened in the browser.

### slf4j
slf4j + [simpleLogger](http://www.slf4j.org/api/org/slf4j/impl/SimpleLogger.html) has used for the console debug logging. 
Logging level can be set in /test/java/resource/simplelogger.properties file.


### WebDriver log
WebDriver log for debugging purpose located: */allure-results/webdriver.{id}.log*.

### Parallel execution on remote server
[Selenoid](https://aerokube.com/) has been setup for parallel execution.
For remote tests execution (in 14 threads) we should run command:
```
mvn clean test -Dselenoid.hostname=http://amlnx-ldq-sln01.digital.prscoad.com -DthreadCount=14
```
Execution visualization can be reached on: http://amlnx-ldq-sln01.digital.prscoad.com:8080/#/
Server monitoring can be done via zabbix: http://zabbix.digital.prscoad.com/zabbix/screens.php?elementid=34


## HowTo
### Cleanup channels and channel associations
Before running channel/CA cleanup script you should:
1. Update scripts with the latest version: 
   - Navigate to: https://bamboo.digital.com/browse/HRV-DB
   - Select latest build and go to Artifacts
   - Download: 'Ansible deployment bundle'
2. Put cleanup scripts on {SSH_HOST} machine to 'cleanup_scripts' folder (under your user folder rrXXXXXX).
3. Comment 'prompt' (e.g. '\prompt 'To kill a  channel association, please enter a CA ID: ' channelAssocId') lines in both './cleanup_scripts/dbscripts/pg/utility/killAChannel.sql' and './cleanup_scripts/dbscripts/pg/utility/killAChannelAssociation.sql' files.
4. Save modifications.
5. Remove {SSH_USERNAME} = '[your username]', {SSH_PASSWORD} = '[your password]' with your personal credentials.

### Normalization fail emails forwarding
Since normalization fail emails can be send only to *@ccc.com e-mails (according to LSC's using external emails restrictions), but tests are using *@gmail.com for e-mail verification we should setup norma fail e-mails forwarding.

[normalization fail e-mail] --> |*@ccc.com forward to *@gmail.com e.g. ld.automation.testing+norma.fail@gmail.com| 


Filtering and forwarding rule example:
```
Matches: from:(noreply-hrv@bn.ccc.com) subject:(*Hrv Metadata Normalization Failure Notification*)
Do this: Skip Inbox, Mark as read, Forward to ld.automation.testing+norma.fail@gmail.com
```

### Setup Selenoid
Read: [Selenoid how to start](https://aerokube.com/selenoid/latest/#_start_selenoid)
Run Selenoid with:
```
./cm selenoid start --args "-limit 10 -timeout 10m"
```
Run selenoid UI with:
```
./cm selenoid-ui start
```

Video recording :
```
docker pull selenoid/video-recorder:latest-release
```
by default video has stored in: ./video
