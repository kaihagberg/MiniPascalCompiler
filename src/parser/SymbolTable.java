package parser;

import scanner.TokenType;

import java.util.Collection;
import java.util.HashMap;

public class SymbolTable {

    HashMap<String, Symbol> identifierTable = new HashMap<String, Symbol>();

    public SymbolTable() {
    }

    /**
     * Adds a symbol to the SymbolTable
     *
     * @param identifier of the symbol
     * @param tokenType  of the symbol
     * @param dataType   of the token (int or real)
     */
    public boolean add(String identifier, TokenType tokenType, String dataType, Integer start, Integer end) {
        boolean answer = false;
        if (!identifierTable.containsKey(identifier)) {
            Symbol symbol = new Symbol(identifier, tokenType, dataType, start, end);
            identifierTable.put(identifier, symbol);
            answer = true;
        }
        return answer;
    }

    /**
     * Determines if the id represents a variable
     *
     * @param name of the Symbol
     * @return boolean
     */
    public boolean isVariableName(String name) {
        boolean answer = false;
        if (identifierTable.containsKey(name)) {
            if (identifierTable.get(name).getTokenType() == TokenType.VAR) {
                answer = true;
            }
        }
        return answer;
    }

    /**
     * Determines if the id represents a function
     *
     * @param name of the Symbol
     * @return boolean
     */
    public boolean isFunctionName(String name) {
        boolean answer = false;
        if (identifierTable.containsKey(name)) {
            if (identifierTable.get(name).getTokenType() == TokenType.FUNCTION) {
                answer = true;
            }
        }
        return answer;
    }

    /**
     * Determines if the id represents a program
     *
     * @param name of the Symbol
     * @return boolean
     */
    public boolean isProgramName(String name) {
        boolean answer = false;
        if (identifierTable.containsKey(name)) {
            if (identifierTable.get(name).getTokenType() == TokenType.PROGRAM) {
                answer = true;
            }
        }
        return answer;
    }

    /**
     * Determines if the id represents an array
     *
     * @param name of the Symbol
     * @return boolean
     */
    public boolean isArrayName(String name) {
        boolean answer = false;
        if (identifierTable.containsKey(name)) {
            if (identifierTable.get(name).getTokenType() == TokenType.ARRAY) {
                answer = true;
            }
        }
        return answer;
    }

    public Collection<Symbol> getSymbols() {
        return identifierTable.values();
    }
}