package apitest;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class ReqResTestBase {
   private JSONObject requestParams;
   private String BASE_URL = "https://reqres.in/api/";
    private String USERS_URL = BASE_URL +"users/";
    private String UNKNOWN_RESOURCE_URL = BASE_URL+"unknown/";
    private String LOGIN_URL = BASE_URL+"login/";

    public void verifyUserDataPresenceOnPage(int pageNumber) {
        given().
                param("page", pageNumber).
                when().
                get(BASE_URL+ "users").
                then().
                log().body().
                statusCode(200);

    }
    public void verifyUsersAmountOnPage(int pageNumber, int usersAmount) {
        given().
                when().
                get(USERS_URL+"?page="+pageNumber).
                then().
                log().body().
                statusCode(200).
                body("data", hasSize(3));
    }
    public void verifyUserPresenceById(int userId) {
        given().
                when().
                get(USERS_URL+userId).
                then().
                log().body().
                statusCode(200);
    }
    public void verifyUserNamesById(int userId, String firstName, String lastName) {
        given().
                when().
                get(USERS_URL+userId).
                then().
                log().body().
                statusCode(200).
                body("data.first_name", equalTo(firstName)).
                body("data.last_name", equalTo(lastName));
    }
    public void verifyUserAbsenceById(int userId) {
        given().
                when().
                get(USERS_URL+userId).
                then().
                log().body().
                statusCode(404);
    }
    public void verifyResourceDataById(int resourceId, String resourceName,int resourceYear, String resourceColor) {
        given().
                when().
                get(UNKNOWN_RESOURCE_URL+resourceId).
                then().
                log().body().
                statusCode(200).
                body("data.name", equalTo(resourceName), "data.year", equalTo(resourceYear),"data.color", equalTo(resourceColor));
    }

    public void verifyUserCreation(String userName, String userJob) {
        requestParams = new JSONObject();
        requestParams.put("name", userName).put("job", userJob);
        given().
               headers("Content-Type", "application/json").
                body(requestParams.toString()).
                when().
                post(USERS_URL).
                then().
                log().body().
                statusCode(201).
                body("name", equalTo(userName), "job", equalTo(userJob));
    }

    public void verifyUserUpdate(String userName, String userJob) {
        requestParams = new JSONObject();
        requestParams.put("name", userName).put("job", userJob);
        given().
                headers("Content-Type", "application/json").
                body(requestParams.toString()).
                when().
                put(USERS_URL).
                then().
                log().body().
                statusCode(200).
                body("name", equalTo(userName), "job", equalTo(userJob), "updatedAt", anything());
    }
    public void verifyUserDeleteById(int userID) {
        given().
                when().
                delete(USERS_URL+userID).
                then().
                log().body().
                statusCode(204);
    }
    public void verifyUnsuccessfulLogin(String password) {
        requestParams = new JSONObject();
        requestParams.put("password", password);
        given().
                body(requestParams.toString()).
                when().
                post(LOGIN_URL).
                then().
                log().body().
                statusCode(400).
                body("error", equalTo("Missing email or username"));
    }



}
