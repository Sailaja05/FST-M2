package examples;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;



public class SecondTest {

    @Test
    public void getRequestTest() {

        // set base uri
        String BaseURI = "https://petstore.swagger.io/v2/pet";

        //get Response

        Response response =given()
                .header("Content-Type", "applocation/json")
                .queryParam("status", "sold")

                .when()
                .get(BaseURI + "/findByStatus");

        System.out.println("As Single String" +response.getBody().asString());
        System.out.println("As formatted String" +response.getBody().asPrettyString());
        //String petStatus = response.then().extract().path();



                response.then()
                .statusCode(200)
                .body("[0].status", equalTo("sold"));


    }
}


