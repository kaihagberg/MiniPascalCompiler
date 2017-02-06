package scanner;

import java.util.HashMap;

/**
 * A lookup table to find the token types for symbols based on the String.
 */
public class LookupTable extends HashMap<String,TokenType> {

    /**
     * Creates a lookup table, loading all the token types.
     */
    public LookupTable() {
        this.put( "+", TokenType.PLUS);
        this.put( "-", TokenType.MINUS);
        this.put( "*", TokenType.MULTIPLY);
        this.put( "/", TokenType.DIVIDE);
        this.put( "(", TokenType.LEFT_PAREN);
        this.put( ")", TokenType.RIGHT_PAREN);
        this.put( "[", TokenType.LEFT_BRACKET);
        this.put( "]", TokenType.RIGHT_BRACKET);
        this.put( "and", TokenType.AND);
        this.put( "array", TokenType.ARRAY);
        this.put( ":=", TokenType.ASSIGNMENT_OP);
        this.put( "begin", TokenType.BEGIN);
        this.put( "div", TokenType.DIV);
        this.put( "do", TokenType.DO);
        this.put( "else", TokenType.ELSE);
        this.put( "end", TokenType.END);
        this.put( "function", TokenType.FUNCTION);
        this.put( "if", TokenType.IF);
        this.put( "integer", TokenType.INTEGER);
        this.put( "mod", TokenType.MOD);
        this.put( "not", TokenType.NOT);
        this.put( "of", TokenType.OF);
        this.put( "or", TokenType.OR);
        this.put( "procedure", TokenType.PROCEDURE);
        this.put( "program", TokenType.PROGRAM);
        this.put( "real", TokenType.REAL);
        this.put( "then", TokenType.THEN);
        this.put( "var", TokenType.VAR);
        this.put( "while", TokenType.WHILE);
        this.put( ";", TokenType.SEMI_COLON);
        this.put( ":", TokenType.COLON);
        this.put( ",", TokenType.COMMA);
        this.put( ".", TokenType.PERIOD);
        this.put( "=", TokenType.EQUALS);
        this.put( "<>", TokenType.NOT_EQUAL);
        this.put( "<", TokenType.LESS_THAN);
        this.put( "<=", TokenType.LESS_THAN_EQUAL_TO);
        this.put( ">", TokenType.GREATER_THAN);
        this.put( ">=", TokenType.GREATER_THAN_EQUAL_TO);
    }
}