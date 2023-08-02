package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestUtil {

    private static final Logger log = LoggerFactory.getLogger(RequestUtil.class);
    public RequestSpecification requestSpec = RestAssured.given();

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
        return this.response;
    }

    public Response getResponse(){
        return this.response;
    }

    public String getEndpiont(){
        return this.endpiont;
    }


}
