package steps.api;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.api;

public class API extends api {

    String baseURI;

    @Given("打开{word}")
    public void open(String url){
        baseURI = url;
        Response res = RestAssured.given()
                .get(url);
        res.prettyPrint();
    }
}
