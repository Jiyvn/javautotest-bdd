package utils;

import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import com.github.dzieciou.testing.curl.Platform;
//import filters.CurlFilter;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import static io.restassured.RestAssured.given;

public class RequestUtil {

    private static final Logger log = LoggerFactory.getLogger(RequestUtil.class);
    RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(
            Options.builder()
                    .targetPlatform(Platform.UNIX)
                    .useLogLevel(Level.INFO)
                    .updateCurl(curl -> curl
                            .removeHeader("Host")
                            .removeHeader("User-Agent")
                            .removeHeader("Connection")
                            .removeHeader("Accept")
                            .removeHeader("Content-Length")
                            .setVerbose(false)
                            .setInsecure(false)
                            .setCompressed(false)
                    )
                    .build()
    );
//    public RequestSpecification requestSpec = RestAssured.given().filter(new CurlFilter());
    public RequestSpecification requestSpec = RestAssured.given().config(config);

    protected String baseUri;
    protected String endpiont;
    protected String url;
    protected String method;
    //    protected Map<String, Object> requestHeader = new HashMap<>();
//    protected Map<String, Object> requestParams = new HashMap<>();
    protected Response response;

    public RequestUtil(){

    }

    public RequestUtil(String baseUri){
        this.baseUri = baseUri.endsWith("/")? baseUri.substring(1): baseUri;
    }

    public RequestUtil setBaseUri(String baseUri){
        this.baseUri = baseUri;
        return this;
    }

    public String getBaseUri(){
        return this.baseUri;
    }

    public String getUrl(){
        return this.url;
    }

    public RequestUtil setEndpoint(String endpoint){
        this.endpiont = endpoint.startsWith("/")? endpoint.substring(1): endpoint;
        return this;
    }

    public RequestUtil addHeader(String key, Object value){
        this.requestSpec.header(key, value);
        return this;
    }

    public RequestUtil addParam(String key, Object value){
        this.requestSpec.param(key, value);
        return this;
    }

    public RequestUtil addQueryParam(String key, Object value){
        this.requestSpec.queryParam(key, value);
        return this;
    }

    public RequestUtil addFormParam(String key, Object value){
        this.requestSpec.formParam(key, value);
        return this;
    }

    public RequestUtil setMethod(String method){
        this.method = method;
        return this;
    }

    public Response build(){
        this.url = this.baseUri + "/" + this.endpiont;
        switch (this.method.toLowerCase()) {
            case "get" -> this.response = this.requestSpec.get(this.url);
            case "post" -> this.response = this.requestSpec.post(this.url);
            case "delete" -> this.response = this.requestSpec.delete(this.url);
            case "options" -> this.response = this.requestSpec.options(this.url);
            case "put" -> this.response = this.requestSpec.put(this.url);
            case "head" -> this.response = this.requestSpec.head(this.url);
        }
        outputRequestDetails();
        outputResponseDetails();
        return this.response;
    }

    public Response getResponse(){
        return this.response;
    }

    public String getEndpiont(){
        return this.endpiont;
    }

    public RequestSpecification getRequestSpec(){
        return this.requestSpec;
    }

    public void outputRequestDetails(){
        QueryableRequestSpecification query = SpecificationQuerier.query(this.requestSpec);
        log.info(
                "request headers: \n"+ query.getHeaders()
                        +"\n\n"+"request params: \n"+ query.getRequestParams()
                        +"\n\n"+"request queryParams: \n"+ query.getQueryParams()
                        +"\n\n"+"request formParams: \n"+ query.getFormParams()
                        +"\n\n"+"request body: \n"+ query.getBody()
        );
    }
    public void outputResponseDetails(){
        log.info(
                "response header: \n"+ this.response.getHeaders()
                        +"\n\n"+"response body: \n"+this.response.asPrettyString()
        );
    }

    public RequestUtil clearSpec(){
        this.requestSpec = given().config(config);
        return this;
    }
}
