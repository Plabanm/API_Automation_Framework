package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.*;
import java.util.Properties;


public class Utils {
    public static RequestSpecification request;

    public RequestSpecification RequestSpecification() throws IOException {

        if (request == null) {
            PrintStream log = new PrintStream(new FileOutputStream("log.txt"));

             request = new RequestSpecBuilder()
                    .setBaseUri(getGlobalVlaue("baseUrl"))
                    .addQueryParam("key", "qaclick123")
                    .addFilter(RequestLoggingFilter.logRequestTo(log))
                    .addFilter(ResponseLoggingFilter.logResponseTo(log))
                    .setContentType(ContentType.JSON).build();
            return request;
        }
        return request;
    }

    public static String getGlobalVlaue(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/java/resources/global.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public String getJsonPath(Response response, String key){
        String resp = response.asString();
        JsonPath jp = new JsonPath(resp);
        return jp.get(key).toString();

    }
}
