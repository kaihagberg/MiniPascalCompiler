package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import scanner.MyScanner;
import scanner.Token;
import scanner.TokenType;

/**
 * The parser recognizes whether an input string of tokens is an expression.
 * To use a parser, create an instance pointing at a file,
 * and then call the top-level function, <code>exp()</code>.
 * If the functions returns without an error, the file
 * contains an acceptable expression.
 * @based on code from Erik Steinmetz
 */
public class MyParser {

    ///////////////////////////////
    //    Instance Variables
    ///////////////////////////////

    private Token lookahead;
    private MyScanner scanner;
    public SymbolTable symbolTable;
    private boolean withoutError = true;

    ///////////////////////////////
    //       Constructors
    ///////////////////////////////

    /**
     * Constructor for the parser
     * @param text filepath for text being parsed
     * @param isFilename boolean that is true if file is being passed
     */
    public MyParser(String text, boolean isFilename) {
        if (isFilename) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(text);
            } catch (FileNotFoundException ex) {
                error("No file");
            }
            InputStreamReader isr = new InputStreamReader(fis);
            scanner = new MyScanner(isr);

        } else {
            scanner = new MyScanner(new StringReader(text));
        }
        try {
            lookahead = scanner.nextToken();
        } catch (IOException ex) {
            error("Scan error");
        }
        symbolTable = new SymbolTable();
    }

    ///////////////////////////////
    //       Methods
    ///////////////////////////////

    /**
     * Main method in the recognizer. This will recursively call all other methods.
     * @return returns true if no errors are found
     */
    public boolean program() {
        if (lookahead.getType() == TokenType.PROGRAM) {
            match(TokenType.PROGRAM);
            if(lookahead.getType() == TokenType.ID) {
                symbolTable.add(lookahead.getLexeme(), TokenType.PROGRAM, "", null, null);
                match(TokenType.ID);
            }
            else {
                error("Expected program identifier");
            }
            match(TokenType.SEMI_COLON);
            declarations();
            subprogram_declarations();
            compound_statement();
            match(TokenType.PERIOD);
        } else {
            error("program");
        }
        return withoutError;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> identifier_list() {
        ArrayList<String> identifierList = new ArrayList<>();
        identifierList.add(lookahead.getLexeme());
        match(TokenType.ID);
        if (lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            identifierList.addAll(identifier_list());
        }
        return identifierList;
    }

    /**
     *
     */
    public void declarations() {
        if (lookahead.getType() == TokenType.VAR) {
            match(TokenType.VAR);
            ArrayList<String> identifierList = identifier_list();
            match(TokenType.COLON);
            type(identifier_list());
            match(TokenType.SEMI_COLON);
            declarations();
        } else {
            //lambda case
        }
    }

    /**
     *
     * @param identifierList
     */
    public void type(ArrayList<String> identifierList) {
        TokenType tokenType = null;
        String dataType = null;
        Integer arrayStart = null;
        Integer arrayEnd = null;

        if (lookahead.getType() == TokenType.ARRAY) {
            match(TokenType.ARRAY);
            match(TokenType.LEFT_BRACKET);
            if(lookahead.getType() == TokenType.NUMBER) {
                arrayStart = Integer.parseInt(lookahead.getLexeme());
                match(TokenType.NUMBER);
            } else {
                error("Expected array index");
            }
            match(TokenType.COLON);
            if(lookahead.getType() == TokenType.NUMBER) {
                arrayEnd = Integer.parseInt(lookahead.getLexeme());
                match(TokenType.NUMBER);
            } else {
                error("Expected array index");
            }
            match(TokenType.RIGHT_BRACKET);
            match(TokenType.OF);
            tokenType = TokenType.ARRAY;
            dataType = standard_type();
        } else if (lookahead.getType() == TokenType.INTEGER || lookahead.getType() == TokenType.REAL) {
            tokenType = TokenType.VAR;
            dataType = standard_type();
        } else {
            error("Expected var type");
        }
    }

    /**
     *
     * @return
     */
    public String standard_type() {
        String standardType = null;

        switch (lookahead.getType()) {
            case INTEGER:
                standardType = "INTEGER";
                match(TokenType.INTEGER);
                break;
            case REAL:
                standardType = "REAL";
                match(TokenType.REAL);
                break;
            default:
                error("Expected type: INTEGER or REAL");
                break;
        }
        return standardType;
    }

    /**
     *
     */
    public void subprogram_declarations() {
        if (lookahead.getType() == TokenType.FUNCTION || lookahead.getType() == TokenType.PROCEDURE) {
            subprogram_declaration();
            match(TokenType.SEMI_COLON);
            subprogram_declarations();
        }
        else {
            //lambda case
        }
    }

    /**
     *
     */
    public void subprogram_declaration() {
        subprogram_head();
        declarations();
        subprogram_declarations();
        compound_statement();
    }

    /**
     *
     */
    public void subprogram_head() {
        String identifier = null;

        if (lookahead.getType() == TokenType.FUNCTION) {
            match(TokenType.FUNCTION);
            if(lookahead.getType() == TokenType.ID) {
                identifier = lookahead.getLexeme();
                match(TokenType.ID);
            }
            arguments();
            match(TokenType.COLON);
            symbolTable.add(identifier, TokenType.FUNCTION, standard_type(), null, null);
            match(TokenType.SEMI_COLON);
        } else if (lookahead.getType() == TokenType.PROCEDURE) {
            match(TokenType.PROCEDURE);
            if(lookahead.getType() == TokenType.ID) {
                identifier = lookahead.getLexeme();
                match(TokenType.ID);
            }
            arguments();
            symbolTable.add(identifier, TokenType.PROCEDURE, null, null, null);
            match(TokenType.SEMI_COLON);
        } else {
            error("Not a subprogram_head");
        }
    }

    /**
     *
     */
    public void arguments() {
        if (lookahead.getType() == TokenType.LEFT_PAREN) {
            match(TokenType.LEFT_PAREN);
            parameter_list();
            match(TokenType.RIGHT_PAREN);
        } else {
            //lambda case
        }
    }

    /**
     *
     */
    public void parameter_list() {
        ArrayList<String> identifierList = identifier_list();
        match(TokenType.COLON);
        type(identifierList);
        if (lookahead.getType() == TokenType.SEMI_COLON) {
            match(TokenType.SEMI_COLON);
            parameter_list();
        }
    }

    /**
     *
     */
    public void compound_statement() {
        if (lookahead.getType() == TokenType.BEGIN) {
            match(TokenType.BEGIN);
            optional_statements();
            match(TokenType.END);
        } else {
            error("Compound statement");
        }
    }

    /**
     *
     */
    public void optional_statements() {
        TokenType nextType = lookahead.getType();
        if (nextType == TokenType.ID || nextType == TokenType.BEGIN || nextType == TokenType.IF || nextType == TokenType.WHILE) {
            statement_list();
        } else {
            //lambda option
        }
    }

    /**
     *
     */
    public void statement_list() {
        statement();
        if (lookahead.getType() == TokenType.SEMI_COLON) {
            match(TokenType.SEMI_COLON);
            statement_list();
        }
    }

    /**
     *
     */
    public void statement() {
        switch (lookahead.getType()) {
            case ID:
                String identifier = lookahead.getLexeme();
                if(symbolTable.isArrayName(identifier) || symbolTable.isVariableName(identifier)) {
                    variable();
                    match(TokenType.ASSIGNMENT_OP);
                    expression();
                } else if(symbolTable.isFunctionName(identifier)) {
                    procedure_statement();
                } else {
                    error(identifier + " has not been declared");
                }
                break;
            case IF:
                match(TokenType.IF);
                expression();
                match(TokenType.THEN);
                statement();
                if (lookahead.getType() == TokenType.ELSE) {
                    match(TokenType.ELSE);
                    statement();
                }
                break;
            case WHILE:
                match(TokenType.WHILE);
                expression();
                match(TokenType.DO);
                statement();
                break;
            case BEGIN:
                compound_statement();
                break;
            default:
                error("Expected statement");
        }
    }

    /**
     *
     */
    public void variable() {
        match(TokenType.ID);
        if(lookahead.getType() == TokenType.LEFT_BRACKET) {
            match(TokenType.LEFT_BRACKET);
            expression();
            match(TokenType.RIGHT_BRACKET);
        } else {
            error("Expected expression");
        }
    }

    /**
     *
     */
    public void procedure_statement() {
        match(TokenType.LEFT_PAREN);
        expression_list();
        match(TokenType.RIGHT_PAREN);
    }

    /**
     *
     */
    public void expression_list() {
        expression();
        if (lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            expression_list();
        }
    }

    /**
     *
     */
    public void expression() {
        simple_expression();
        if (isRelop(lookahead)) {
            relop();
            simple_expression();
        } else {
            //lambda case
        }
    }

    /**
     *
     */
    public void simple_expression() {
        if (isTerm(lookahead)) {
            term();
            simple_part();
        } else if (lookahead.getType() == TokenType.MINUS || lookahead.getType() == TokenType.PLUS) {
            sign();
            term();
            simple_part();
        }
    }

    /**
     *
     */
    public void simple_part() {
        if (lookahead.getType() == TokenType.PLUS || lookahead.getType() == TokenType.MINUS) {
            addop();
            term();
            simple_part();
        } else {
            //lambda option
        }
    }

    /**
     * Executes the rule for the term non-terminal symbol in
     * the expression grammar.
     */
    public void term() {
        factor();
        term_part();
    }

    /**
     * Executes the rule for the term_part; non-terminal symbol in
     * the expression grammar.
     */
    public void term_part() {
        if (isMulop(lookahead)) {
            mulop();
            factor();
            term_part();
        } else {
            // lambda option
        }
    }

    /**
     * Executes the rule for the factor non-terminal symbol in
     * the expression grammar.
     */
    public void factor() {
        switch (lookahead.getType()) {
            case LEFT_PAREN:
                match(TokenType.LEFT_PAREN);
                expression();
                match(TokenType.RIGHT_PAREN);
                break;
            case NUMBER:
                match(TokenType.NUMBER);
                break;
            case ID:
                String identifier = lookahead.getLexeme();
                if(symbolTable.isArrayName(identifier) || symbolTable.isVariableName(identifier)) {
                    variable();
                } else if(symbolTable.isFunctionName(identifier)) {
                    procedure_statement();
                } else {
                    error(identifier + " has not been declared");
                }
                break;
            case NOT:
                match(TokenType.NOT);
                factor();
                break;
            default:
                error("Factor");
                break;
        }
    }

    public void sign() {
        switch (lookahead.getType()) {
            case MINUS:
                match(TokenType.MINUS);
                break;
            case PLUS:
                match(TokenType.PLUS);
                break;
            default:
                break;
        }
    }

    public boolean isTerm(Token token) {
        boolean answer = false;
        TokenType nextType = token.getType();
        if (nextType == TokenType.ID || nextType == TokenType.NUMBER || nextType == TokenType.NOT) {
            answer = true;
        }
        return answer;
    }

    public void relop() {
        switch (lookahead.getType()) {
            case EQUALS:
                match(TokenType.EQUALS);
                break;
            case NOT_EQUAL:
                match(TokenType.NOT_EQUAL);
                break;
            case LESS_THAN:
                match(TokenType.LESS_THAN);
                break;
            case LESS_THAN_EQUAL_TO:
                match(TokenType.LESS_THAN_EQUAL_TO);
                break;
            case GREATER_THAN_EQUAL_TO:
                match(TokenType.GREATER_THAN_EQUAL_TO);
                break;
            case GREATER_THAN:
                match(TokenType.LESS_THAN);
                break;
            default:
                error("expected relop");
        }
    }

    public boolean isRelop(Token token) {
        boolean answer = false;
        TokenType nextType = token.getType();
        if (nextType == TokenType.EQUALS || nextType == TokenType.NOT_EQUAL || nextType == TokenType.LESS_THAN ||
                nextType == TokenType.LESS_THAN_EQUAL_TO || nextType == TokenType.GREATER_THAN_EQUAL_TO ||
                nextType == TokenType.GREATER_THAN) {
            answer = true;
        }
        return answer;
    }

    /**
     * Executes the rule for the addop non-terminal symbol in
     * the expression grammar.
     */
    public void addop() {
        if (lookahead.getType() == TokenType.PLUS) {
            match(TokenType.PLUS);
        } else if (lookahead.getType() == TokenType.MINUS) {
            match(TokenType.MINUS);
        } else {
            error("Addop");
        }
    }

    /**
     * Determines whether or not the given token is
     * a mulop token.
     *
     * @param token The token to check.
     * @return true if the token is a mulop, false otherwise
     */
    public boolean isMulop(Token token) {
        boolean answer = false;
        if (token.getType() == TokenType.MULTIPLY ||
                token.getType() == TokenType.DIVIDE) {
            answer = true;
        }
        return answer;
    }

    /**
     * Executes the rule for the mulop non-terminal symbol in
     * the expression grammar.
     */
    public void mulop() {
        if (lookahead.getType() == TokenType.MULTIPLY) {
            match(TokenType.MULTIPLY);
        } else if (lookahead.getType() == TokenType.DIVIDE) {
            match(TokenType.DIVIDE);
        } else {
            error("Mulop");
        }
    }

    /**
     * Matches the expected token.
     * If the current token in the input stream from the scanner
     * matches the token that is expected, the current token is
     * consumed and the scanner will move on to the next token
     * in the input.
     * The null at the end of the file returned by the
     * scanner is replaced with a fake token containing no
     * type.
     *
     * @param expected the expected token type.
     */
    public void match(TokenType expected) {
        System.out.println("match( " + expected + ")");
        if (this.lookahead.getType() == expected) {
            try {
                this.lookahead = scanner.nextToken();
                if (this.lookahead == null) {
                    this.lookahead = new Token("End of File", null);
                }
            } catch (IOException ex) {
                error("Scanner exception");
            }
        } else {
            error("Match of " + expected + " found " + this.lookahead.getType()
                    + " instead.");
        }
    }

    /**
     * Errors out of the parser.
     * Prints an error message and then exits the program.
     * @param message The error message to print.
     */
    public void error( String message) {
        System.out.println( "Error " + message + " at line " +
                this.scanner.getLine() + " column " +
                this.scanner.getColumn());
        System.exit(1);
    }
}