package scanner;

/**
 * Token for the expression grammar.
 */
public class Token {

    private String lexeme;
    private TokenType type;

    /**
     * Creates a token with the given lexeme and type.
     * @param lex The lexeme for this token.
     * @param tokenType The type for this token.
     */
    public Token( String lex, TokenType tokenType) {
        this.lexeme = lex;
        this.type = tokenType;
    }

    public String getLexeme() { return this.lexeme;}
    public TokenType getType() { return this.type;}

    /**
     * Creates the String representation of this token including
     * the lexeme and type.
     * @return The String representation of this token.
     */
    @Override
    public String toString() {
        return "Token: \"" + this.lexeme + "\" of type: " + this.type;
    }
}