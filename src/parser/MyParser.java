package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
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

    ///////////////////////////////
    //       Constructors
    ///////////////////////////////

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
    }

    ///////////////////////////////
    //       Methods
    ///////////////////////////////

    public void program() {
        if (lookahead.getType() == TokenType.PROGRAM) {
            match(TokenType.PROGRAM);
            match(TokenType.ID);
            match(TokenType.SEMI_COLON);
            declarations();
            subprogram_declarations();
            compound_statement();
            match(TokenType.PERIOD);
        } else {
            error("program");
        }
    }

    private void identifier_list() {
        match(TokenType.ID);
        if (lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            identifier_list();
        }
    }

    private void declarations() {
        if (lookahead.getType() == TokenType.VAR) {
            match(TokenType.VAR);
            identifier_list();
            match(TokenType.COLON);
            type();
            match(TokenType.SEMI_COLON);
            declarations();
        } else {
            //lambda case
        }
    }

    private void type() {
        if (lookahead.getType() == TokenType.ARRAY) {
            match(TokenType.ARRAY);
            match(TokenType.LEFT_BRACKET);
            match(TokenType.NUMBER);
            match(TokenType.COLON);
            match(TokenType.NUMBER);
            match(TokenType.OF);
            standard_type();
        } else if (lookahead.getType() == TokenType.INTEGER || lookahead.getType() == TokenType.REAL) {
            standard_type();
        } else {
            error("Expected var type");
        }
    }

    private void standard_type() {
        switch (lookahead.getType()) {
            case INTEGER:
                match(TokenType.INTEGER);
                break;
            case REAL:
                match(TokenType.REAL);
                break;
            default:
                break;
        }
    }

    private void subprogram_declarations() {
        if (lookahead.getType() == TokenType.FUNCTION || lookahead.getType() == TokenType.PROCEDURE) {
            subprogram_declaration();
            if (lookahead.getType() == TokenType.SEMI_COLON) {
                match(TokenType.SEMI_COLON);
                subprogram_declarations();
            }
        } else {
            //lambda case
        }
    }

    private void subprogram_declaration() {
        subprogram_head();
        declarations();
        subprogram_declarations();
        compound_statement();
    }

    private void subprogram_head() {
        if (lookahead.getType() == TokenType.FUNCTION) {
            match(TokenType.FUNCTION);
            match(TokenType.ID);
            arguments();
            match(TokenType.COLON);
            standard_type();
            match(TokenType.SEMI_COLON);
        } else if (lookahead.getType() == TokenType.PROCEDURE) {
            match(TokenType.PROCEDURE);
            match(TokenType.ID);
            arguments();
            match(TokenType.SEMI_COLON);
        }
    }

    private void arguments() {
        if (lookahead.getType() == TokenType.LEFT_PAREN) {
            match(TokenType.LEFT_PAREN);
            parameter_list();
            match(TokenType.RIGHT_PAREN);
        } else {
            //lambda case
        }
    }

    private void parameter_list() {
        identifier_list();
        match(TokenType.SEMI_COLON);
        type();
        if (lookahead.getType() == TokenType.SEMI_COLON) {
            match(TokenType.SEMI_COLON);
            parameter_list();
        }
    }

    private void compound_statement() {
        if (lookahead.getType() == TokenType.BEGIN) {
            match(TokenType.BEGIN);
            optional_statements();
            match(TokenType.END);
        } else {
            error("compound statement");
        }
    }

    private void optional_statements() {
        TokenType nextType = lookahead.getType();
        if (nextType == TokenType.ID || nextType == TokenType.BEGIN || nextType == TokenType.IF || nextType == TokenType.WHILE) {
            statement_list();
        } else {
            //lambda option
        }
    }

    private void statement_list() {
        statement();
        if (lookahead.getType() == TokenType.SEMI_COLON) {
            match(TokenType.SEMI_COLON);
            statement_list();
        }
    }

    private void statement() {
        switch (lookahead.getType()) {
            case ID:
                match(TokenType.ID);
                if (lookahead.getType() == TokenType.LEFT_BRACKET) {
                    variable();
                } else if (lookahead.getType() == TokenType.LEFT_PAREN) {
                    procedure_statement();
                } else if (lookahead.getType() == TokenType.ASSIGNMENT_OP) {
                    match(TokenType.ASSIGNMENT_OP);
                    expression();
                } else {
                    error("invalid statement");
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
            default:
                error("statement expected");
        }
    }

    private void variable() {
        expression();
        match(TokenType.RIGHT_BRACKET);
    }

    private void procedure_statement() {
        match(TokenType.LEFT_PAREN);
        expression_list();
        match(TokenType.RIGHT_PAREN);
    }

    private void expression_list() {
        expression();
        if (lookahead.getType() == TokenType.SEMI_COLON) {
            match(TokenType.SEMI_COLON);
            expression_list();
        }
    }

    private void expression() {
        simple_expression();
        if (isRelop(lookahead)) {
            relop();
            simple_expression();
        } else {
            //lambda case
        }
    }

    private void simple_expression() {
        if (isTerm(lookahead)) {
            term();
            simple_part();
        } else if (lookahead.getType() == TokenType.MINUS || lookahead.getType() == TokenType.PLUS) {
            sign();
            term();
            simple_part();
        }
    }

    private void simple_part() {
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
        term_prime();
    }

    /**
     * Executes the rule for the term prime; non-terminal symbol in
     * the expression grammar.
     */
    public void term_prime() {
        if (isMulop(lookahead)) {
            mulop();
            factor();
            term_prime();
        } else {
            // lambda option
        }
    }

    /**
     * Executes the rule for the factor non-terminal symbol in
     * the expression grammar.
     */
    public void factor() {
        // Executed this decision as a switch instead of an
        // if-else chain. Either way is acceptable.
        switch (lookahead.getType()) {
            case LEFT_PAREN:
                match(TokenType.LEFT_PAREN);
                exp();
                match(TokenType.RIGHT_PAREN);
                break;
            case NUMBER:
                match(TokenType.NUMBER);
                break;
            case ID:
                match(TokenType.ID);
                // add if statement here
                break;
            //case NOT:
            //    match(TokenType.NOT);
            //    break;
            default:
                error("Factor");
                break;
        }
    }

    private void sign() {
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

    private boolean isTerm(Token token) {
        boolean answer = false;
        TokenType nextType = token.getType();
        if (nextType == TokenType.ID || nextType == TokenType.NUMBER || nextType == TokenType.NOT) {
            answer = true;
        }
        return answer;
    }

    private void relop() {
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

    private boolean isRelop(Token token) {
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
     * Executes the rule for the exp non-terminal symbol in
     * the expression grammar.
     */
    public void exp() {
        term();
        exp_prime();
    }

    /**
     * Executes the rule for the exp prime; non-terminal symbol in
     * the expression grammar.
     */
    public void exp_prime() {
        if (lookahead.getType() == TokenType.PLUS ||
                lookahead.getType() == TokenType.MINUS) {
            addop();
            term();
            exp_prime();
        } else {
            // lambda option
        }
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
    private boolean isMulop(Token token) {
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
        System.exit(0);
    }
}