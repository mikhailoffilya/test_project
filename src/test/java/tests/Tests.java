package tests;

import org.junit.jupiter.api.Test;
import servisec.Utilities;

import java.io.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Tests {
    Utilities utilities = new Utilities();
    Map<Integer, String> gParam = utilities.gParam;
    Map<Integer, String> gParam2 = utilities.gParam2;

    @Test
//    Позитивные проверки сервиса
    public void positiveTests() throws IOException {
        utilities.sendGoodRequests();
    }
    @Test
//    Проверка работы сервиса с испорченными ссылками
    public void brokenUrlsTests(){
        utilities.sendNegativeRequests();
        utilities.sendNegativeRequestNoUrl();
    }

    @Test
//    Проверка работы новых Urls
    public void newUrlsIsWorking() throws IOException {
        utilities.sendRequests();
    }
//    @Test
//    Проверка соответствия сожержимого longURL и URL после переобразования
//    public void equalsUrls() throws IOException {
//        utilities.getContent();
//        utilities.sendRequests();
//        assertEquals(gParam, gParam2);
//    }
}
