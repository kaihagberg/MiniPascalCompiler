package parser;

import scanner.TokenType;

import java.util.HashMap;

public class SymbolTable {

    HashMap<String, Symbol> identifierTable = new HashMap<String, Symbol>();

    public SymbolTable(){}

    /**
     * Adds a symbol to the SymbolTable
     * @param identifier of the symbol
     * @param tokenType of the symbol
     * @param dataType of the token (int or real)
     */
    public void add(String identifier, TokenType tokenType, TokenType dataType){
        Symbol symbol = new Symbol(identifier, tokenType, dataType);
        identifierTable.put(identifier, symbol);
    }

    /**
     * Determines if the id represents a variable
     * @param name of the Symbol
     * @return boolean
     */
    public boolean isVariableName(String name){
        boolean answer = false;
        if(identifierTable.containsKey(name)){
            if( identifierTable.get(name).getTokenType() == TokenType.VAR){
                answer = true;
            }
        }
        return answer;
    }

    /**
     * Determines if the id represents a function
     * @param name of the Symbol
     * @return boolean
     */
    public boolean isFunctionName(String name){
        boolean answer = false;
        if(identifierTable.containsKey(name)){
            if( identifierTable.get(name).getTokenType() == TokenType.FUNCTION){
                answer = true;
            }
        }
        return answer;
    }

    /**
     * Determines if the id represents a program
     * @param name of the Symbol
     * @return boolean
     */
    public boolean isProgramName(String name){
        boolean answer = false;
        if(identifierTable.containsKey(name)){
            if( identifierTable.get(name).getTokenType() == TokenType.PROGRAM){
                answer = true;
            }
        }
        return answer;
    }

    /**
     * Determines if the id represents an array
     * @param name of the Symbol
     * @return boolean
     */
    public boolean isArrayName(String name){
        boolean answer = false;
        if(identifierTable.containsKey(name)){
            if( identifierTable.get(name).getTokenType() == TokenType.ARRAY){
                answer = true;
            }
        }
        return answer;
    }

        private class Symbol{
            private String id;
            private TokenType tokenType;
            private TokenType dataType;

            /**
             * constructor for Symbol
             * @param id of the token
             * @param tokenType The token type of the object
             * @param dataType the data type of id Integer or Real
             */
            public Symbol(String id, TokenType tokenType, TokenType dataType){
                this.id = id;
                this.tokenType = tokenType;
                this.dataType = dataType;
            }

            /**
             * getter for the identifier
             * @return a string representation of the ID
             */
            public String getId(){
                return id;
            }

            /**
             * returns the data type
             * @return the data type of the symbol
             */
            public TokenType getDataType(){
                return this.dataType;
            }

            /**
             * Returns the token type
             * @return the token type of the symbol
             */
            public TokenType getTokenType(){
                return this.tokenType;
            }

            /**
             * returns a string
             * @return a string version of the object
             */
            @Override
            public String toString() {
                return "Symbol{" +
                        "id='" + id + '\'' +
                        ", dataType=" + dataType +
                        ", tokenType=" + tokenType +
                        '}';
            }
        }
}