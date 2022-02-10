package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;


// <T extends APIUtil<T>>: to avoid upcasting on chaining
public class apiUtil<T extends apiUtil<T>>{
    @SuppressWarnings("unchecked")
    private final T This = (T)this;
    protected String baseURI = "<url_domain>";
    public Response response;
    protected String token;

    public apiUtil(){}

    public apiUtil(String baseURI){
        this.baseURI = baseURI;
    }

    public T setToken(String token){
        this.token = token;
        return This;
    }

    public String getToken(){
        return token;
    }

    public T requestAnonymousToken(){
        String url = this.baseURI + "/api/auth/token";
        this.response = RestAssured.given()
                .header("header1", "hh")
                .formParam("formparam1", "ff")
                .post();
        return This;

    }

    public T login(String email, String password){
        String url = this.baseURI + "/api/auth/login";
        this.response = RestAssured.given()
                .header("Authorization", "Bearer " + this.token)
                .formParam("email", email)
                .formParam("password", password)
                .post(url);
        return This;
    }

    public T signup(String email, String password){
        String url = this.baseURI + "/api/auth/register";
        this.response = RestAssured.given()
                .header("Authorization", "Bearer " + this.token)
                .formParam("email", email)
                .formParam("password", password)
                .post(url);
        return This;
    }

}
