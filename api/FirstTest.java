package examples;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FirstTest {

    @Test
    public void getRequestTest(){

        // set base uri
        String BaseURI = "https://petstore.swagger.io/v2/pet";

        //get Response

        given()
                .header("Content-Type","applocation/json")
                .queryParam("status","sold")

        .when()
                .get(BaseURI + "/findByStatus")
        .then()
                .statusCode(200)
                .body("[0].status", equalTo("sold"));




    }

}
