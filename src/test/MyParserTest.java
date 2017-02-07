package test;

import org.junit.Test;
import parser.MyParser;

/**
 *  Test for MyParser using program method and sample file
 */
public class MyParserTest {

    @Test
    public void programTest() throws Exception {
        MyParser instance = new MyParser("src/test/simple.pas", true);
        instance.program();
        System.out.println("It parsed");
    }
}