package servisec;


import io.restassured.response.Response;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Utilities {
    public static Map<Integer, String> gParam = new HashMap<Integer, String>();
    public static Map<Integer, String> gParam2 = new HashMap<Integer, String>();

    public void sendGoodRequests() throws IOException {
        try {
//          Считываем из файла испорченные ссылки
            File file = new File("src/test/resources/files/longUrls.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
//          Отправляем запросы сервису cleanuri
                Response response = given().with().
                        formParam("url", line).
                        when().post("https://cleanuri.com/api/v1/shorten");
                System.out.println(response.jsonPath().getString("result_url"));
//          Проверяем, что сервис ответил корректно
                assertTrue(response.jsonPath().getString("result_url").contains("https://cleanuri.com/"));
                logger(response.jsonPath().getString("result_url"));
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void sendNegativeRequests() {
        try {
//          Считываем из файла испорченные ссылки
            File file = new File("src/test/resources/files/brokenUrls.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
//                Отправляем запросы сервису cleanuri
                Response response = given().with().
                        formParam("url", line).
                        when().post("https://cleanuri.com/api/v1/shorten");
                System.out.println(response.getBody().asString());
//                Проверяем, что сервис ответил ошибкой
                assertTrue(response.getBody().asString().contains("error"));
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendNegativeRequestNoUrl() {
//                Отправляем запросы сервису cleanuri без указания обязательного параметра url
        Response response = given().when().post("https://cleanuri.com/api/v1/shorten");
        System.out.println(response.getBody().asString());
//                Проверяем, что сервис ответил ошибкой
        assertTrue(response.getBody().asString().contains("error"));
    }

    public static void logger(String input) throws IOException {
        FileWriter fw = new FileWriter("src/test/resources/files/newUrls.txt", true);
        fw.write(input);
        fw.write("\n");
        fw.close();
    }

    public void sendRequests() throws IOException {

        int s = 0;
        try {
            File file = new File("src/test/resources/files/newUrls.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                Response response = given().header("User-Agent", "PostmanRuntime/7.28.4").
                        when().get(line);
                System.out.println(response.statusCode());
                assertEquals(response.statusCode(), 200);
                s++;
                gParam2.put(s, String.valueOf(response.getHeaders()));
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getContent() throws IOException {
        int s = 0;
        try {
            File file = new File("src/test/resources/files/longUrls.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            while (line != null) {
                Response response = given().header("User-Agent", "PostmanRuntime/7.28.4").with().
                        when().get(line);
                s++;
                gParam.put(s, String.valueOf(response.getHeaders()));
                System.out.println(line);
                System.out.println(String.valueOf(response.getHeaders()));
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
