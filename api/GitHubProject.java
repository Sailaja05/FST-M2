package LiveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class GitHubProject {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    String ssh_key;
    int keyId;



    @BeforeClass
    public void setUp() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .addHeader("Content-Type", "Application/json")
                .addHeader("Authorization","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")
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectResponseTime(lessThan(300l), TimeUnit.SECONDS)
                .build();

    }

    @Test(priority =1)
    public void addSSHKey() {
        String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCT1Fdn+PZPK8MzcFSHJBQI9IMWZdzAvFsDhqvu59p2qNfsrvOQYqx+vJAX4Sra874FSuIsEQNM4UsBpNvpkqivTsPlPDWWpIu34ZLm3U0eKGpfBoOW6pomCpsVz30lftrzrDtNPI/qH1oPRhNMFa/9PziUEQWBt8606ont7WKpAS+Rz0Sg9zxMMLGstVdQLsKAXGe/YVBAG4wQkus6fLvjKwY3gAXT30gxESjXP94Iojod4rqcQGvHGQBxkn4MJA4pVfwkwDe7hrNj9qezAfFZoODCdGPqTvfP3Dy3pWqJYMpDdCX67akdwqbWbl6EflZUwwqexyZsES+OYqcX1Ts9 }";
        // Generate Response
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post("/user/keys");

        ssh_key = response.then().extract().path("keyId");
        System.out.println(response.getBody().asPrettyString());

        //Assertions
        response.then().statusCode(201);
    }

    @Test(priority =2)
    public void getSSHKey()
    {

        Response response = given().spec(requestSpec)
                .pathParams("keyId",ssh_key)
                .get("./user/keys/{keyId}");

        System.out.println(response.getBody().asPrettyString());
        response.then().statusCode(200);
        response.then().spec(responseSpec);

    }

    @Test(priority=3)
    public void removeSSHKey(){
        Response response = given().spec(requestSpec)
                .pathParams("keyId",ssh_key)
                .when().delete("./user/keys/{keyId}");

        System.out.println(response.getBody().asPrettyString());
        response.then().statusCode(200);
    }

}