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

    @Test
    public void declarationsTest() throws Exception {
        MyParser instance = new MyParser("var foo: integer;" ,false);
        instance.declarations();
        System.out.println("SUCCESS!\n");

        MyParser instance2 = new MyParser("var voo,doo: array[1:20] of real;" ,false);
        instance2.declarations();
        System.out.println("SUCCESS!\n");
    }

    @Test
    public void subprogram_declarationTest() throws Exception {
        String sample = "function test( foo:real) :real;" + "var voo,doo: array[1:10] of real;" + "begin end .";
        MyParser instance = new MyParser(sample,false);
        instance.subprogram_declaration();
        System.out.println("SUCCESS!\n");

        sample = "procedure test(foo:real );" + "var voo,doo: array[10:4] of real;" + "begin end .";
        instance = new MyParser(sample,false);
        instance.subprogram_declaration();
        System.out.println("SUCCESS!\n");
    }


    @Test
    public void variableTest() throws Exception {
        MyParser instance = new MyParser("", true);
        instance.variable();
        System.out.println("SUCCESS!\n");
    }

    //TODO: Add more test cases
}