package filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class CurlFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        String curlCommand = requestSpec.getURI() + " \\\n";
        for (Header header : requestSpec.getHeaders().asList()) {
            curlCommand += "-H '" + header.getName() +": "+header.getValue()+ "' \\\n";
        }
        if (requestSpec.getBody() != null) {
            curlCommand += "-d '" + requestSpec.getBody().toString() + "' \\\n";
        }
        System.out.println(curlCommand);
        return ctx.next(requestSpec, responseSpec);
    }
}
