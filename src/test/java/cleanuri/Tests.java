package cleanuri;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Tests {
    @Test
    public void sendRequest() throws IOException {
        try {
            File file = new File("src/test/resources/files/file.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                RestAssured.baseURI = "https://cleanuri.com/api/v1";
                Response response = given().with().
                        formParam("url", line).
                        when().post("/shorten");
                System.out.println(line);
                System.out.println(response.jsonPath().getString("result_url"));
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
