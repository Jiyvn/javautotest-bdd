package cases;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import page.APIs;

import static helper.apiAutoHelper.curlRestConf;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class SampleTest {

    private static final Logger log = LoggerFactory.getLogger(SampleTest.class);
    // unable to initialize here
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    Response response;


//    @BeforeClass
//    public void setUp(){

//    }

    @DataProvider(name = "positive_full", parallel = true)
    public static Object[][] testData(){
        return new Object[][] {
                new Object[] {
                        new RequestSpecBuilder()
                                .addHeader("X-Forwarded-For", "45.114.118.141")
                                .addHeader("yyy", "")
                                .addHeader("xxxx", "")
                                .build(),
                        new ResponseSpecBuilder()
                                .expectStatusCode(200)
                                .expectBody("xxxxx", equalToIgnoringCase("id"))
                                .build()
                }
        };


    }

//    @Factory(dataProvider = "positive_full")
//    public ApiConfigTest(@ParameterKey("requestSpec") RequestSpecification reqSpec,
//                              @ParameterKey("responseSpec") ResponseSpecification resSpec) {
//        requestSpec = reqSpec;
//        responseSpec = resSpec;
//    }

    // step logging is ineffective
    public void sendGETPostiveFullParamsRequest(){

        response = given().config(curlRestConf).spec(requestSpec).get(new APIs<>().baseURI+"/api/xxxx");
    }

    public void responseIsInExpectation(){
        response.then().assertThat().spec(responseSpec);
    }

    @Test(
            groups = {"unittest"},
            dataProvider = "positive_full",
            description = "positive: full params",
            priority = 1

    )
    public void testApiConfigPositive(RequestSpecification reqSpec, ResponseSpecification resSpec){
        log.info("test positive_full");
        requestSpec = reqSpec;
        responseSpec = resSpec;
        sendGETPostiveFullParamsRequest();
        responseIsInExpectation();
        log.info("requestSpec getHeaders: \n"+ SpecificationQuerier.query(requestSpec).getHeaders());


    }

}
