package com.ccc.hv.qa.api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ccc.hv.qa.api.pojo.KeycloakCreateUserRequest;
import com.ccc.hv.qa.api.pojo.KeycloakResponse;
import com.ccc.hv.qa.api.pojo.KeycloakResult;
import com.ccc.hv.qa.api.pojo.KeycloakUser;
import com.ccc.hv.qa.logging.AllureLogger;
import io.restassured.RestAssured;
import org.jetbrains.annotations.NotNull;
import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;

import static com.ccc.hv.qa.api.services.AuthAPIService.getKeycloakAPIService;
import static com.ccc.hv.qa.configs.EnvConfig.ENV_CONFIG;
import static io.restassured.RestAssured.given;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by R0manL on 24/09/20.
 */

public class UserAPIService {
    private static final AllureLogger log = new AllureLogger(MethodHandles.lookup().lookupClass().getSimpleName());
    private static final String KEYCLOAK_ROOT_PATH = ENV_CONFIG.keycloakPortalUrl() + "/portal-web/rest/secure";
    private static final String HV_ROOT_PATH = ENV_CONFIG.keycloakAuthUrl() + "/auth/admin/realms/LSCC";
    private static final String USERS_PATH = "/users";
    private static final String APPLICATION_JSON = "application/json";

    private static final String HV_APP_NAME = "hrv";
    private static final String USR_CREATED_STATUS = "Created";
    private static final int MAX_NUMBER_OF_USERS_TO_RETURN = 5000;

    private UserAPIService() {
        // None
    }

    public static UserAPIService getUserAPIService() {
        return new UserAPIService();
    }

    public void createUser(@NotNull KeycloakUser user) {
        final String USERNAME = user.getUsername();
        log.debug("Create a new '" + USERNAME + "' user.");

        KeycloakCreateUserRequest createUserRequest = KeycloakCreateUserRequest.builder()
                .application(HV_APP_NAME)
                .user(user)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        try {
            final String userJson = mapper.writeValueAsString(createUserRequest);
            KeycloakResponse response = given()
                    .auth().oauth2(getKeycloakAPIService().getSuperUserAccessToken())
                    .baseUri(KEYCLOAK_ROOT_PATH)
                    .basePath(USERS_PATH)
                    .contentType(APPLICATION_JSON)
                    .body(userJson)
                    .when()
                    .post()
                    .then()
                    .statusCode(HTTP_OK)
                    .extract()
                    .body()
                    .as(KeycloakResponse.class);

            KeycloakResult result = response.getResults().get(0);
            if (USR_CREATED_STATUS.equals(result.getMessage())) {
                log.debug("Keycloak user has been successfully created.");
            } else {
                throw new IllegalStateException("Can't create keycloak user.\nResult: '" + result.toString() + "'.");
            }


        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Jackson mapper issue: " + e.toString());
        }
    }

    @NotNull
    public KeycloakUser getUserIfExistsBy(@NotNull String email) {
        log.debug("Get '" + email + "' user from keycloak.");

        List<KeycloakUser> users = Arrays.asList(getUsersBy(email));
        if(users.isEmpty()) {
            throw new IllegalStateException("User with email: '" + email + "' does not exist.");
        }

        return users.get(0);
    }

    public KeycloakUser[] getUsersWith(@NotNull String textContainedInUsernameFirstLastNameOrEmail) {
        log.debug("Get all users from keycloak that contained '" + textContainedInUsernameFirstLastNameOrEmail + "' text.");
        return given()
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .contentType(APPLICATION_JSON)
                .auth().oauth2(getKeycloakAPIService().getKeyloakAdminAccessToken())
                .baseUri(HV_ROOT_PATH)
                .basePath(USERS_PATH)
                .param("max", MAX_NUMBER_OF_USERS_TO_RETURN)
                .param("search", textContainedInUsernameFirstLastNameOrEmail)
                .when()
                .get()
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .as(KeycloakUser[].class);
    }

    public void deleteUser(@NotNull String userId) {
        log.debug("Deleting '" + userId + "' user from keycloak.");

        given()
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .contentType(APPLICATION_JSON)
                .auth().oauth2(getKeycloakAPIService().getKeyloakAdminAccessToken())
                .baseUri(HV_ROOT_PATH)
                .basePath(USERS_PATH)
                .when()
                .delete(userId)
                .then()
                .statusCode(204);
    }

    private KeycloakUser[] getUsersBy(@NotNull String email) {
        return given()
                .config(RestAssured.config().encoderConfig(encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .contentType(APPLICATION_JSON)
                .auth().oauth2(getKeycloakAPIService().getKeyloakAdminAccessToken())
                .baseUri(HV_ROOT_PATH)
                .basePath(USERS_PATH)
                .param("email", email)
                .when()
                .get()
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .as(KeycloakUser[].class);
    }
}
