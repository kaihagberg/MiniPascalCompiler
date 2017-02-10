package test;

import org.junit.Test;
import scanner.MyScanner;
import scanner.Token;
import scanner.TokenType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

/**
 * Test for MyScanner class
 */
public class MyScannerTest {

    public MyScannerTest() throws FileNotFoundException {
        // constructor needed
    }

    FileInputStream file = new FileInputStream("src/test/simplest.pas");

    @Test
    public void nextTokenTest() throws Exception {
        InputStreamReader isr = new InputStreamReader(file);
        MyScanner scanner = new MyScanner(isr);

        Token token = scanner.nextToken();
        assertEquals(TokenType.PROGRAM, token.getType());

        token = scanner.nextToken();
        assertEquals(TokenType.ID, token.getType());

        token = scanner.nextToken();
        assertEquals(TokenType.SEMI_COLON, token.getType());

        token = scanner.nextToken();
        assertEquals(TokenType.BEGIN, token.getType());

        token = scanner.nextToken();
        assertEquals(TokenType.END, token.getType());

        token = scanner.nextToken();
        assertEquals(TokenType.PERIOD, token.getType());
    }

    @Test
    public void testYytext()throws Exception{
        InputStreamReader isr = new InputStreamReader(file);
        MyScanner scanner = new MyScanner(isr);

        String text = scanner.yytext();
        assertEquals("", text);

        scanner.nextToken();
        text = scanner.yytext();
        assertEquals("program", text);

        scanner.nextToken();
        text = scanner.yytext();
        assertEquals("foo", text);

        scanner.nextToken();
        text = scanner.yytext();
        assertEquals(";", text);

        scanner.nextToken();
        text = scanner.yytext();
        assertEquals("begin", text);

        scanner.nextToken();
        text = scanner.yytext();
        assertEquals("end", text);

        scanner.nextToken();
        text = scanner.yytext();
        assertEquals(".", text);
    }
}