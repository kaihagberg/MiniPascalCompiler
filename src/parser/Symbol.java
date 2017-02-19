package parser;

import scanner.TokenType;

public class Symbol{
    private String id;
    private TokenType tokenType;
    private String dataType;
    private Integer arrayStart;
    private Integer arrayEnd;

    /**
     * constructor for Symbol
     * @param id of the token
     * @param tokenType The token type of the object
     * @param dataType the data type of id Integer or Real
     */
    public Symbol(String id, TokenType tokenType, String dataType, Integer arrayStart, Integer arrayEnd){
        this.id = id;
        this.tokenType = tokenType;
        this.dataType = dataType;
        this.arrayStart = arrayStart;
        this.arrayEnd = arrayEnd;
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
    public String getDataType(){
        return dataType;
    }

    /**
     * Returns the token type
     * @return the token type of the symbol
     */
    public TokenType getTokenType(){
        return tokenType;
    }

    /**
     * Returns the index for start of array
     * @return integer representing start of array
     */
    public int getArrayStart() {
        return arrayStart;
    }

    /**
     * Returns the index for start of array
     * @return integer representing start of array
     */
    public int getArrayEnd() {
        return arrayEnd;
    }

    /**
     * returns a string
     * @return a string version of the object
     */
    @Override
    public String toString() {
        return "Symbol{" +
                "id='" + id + '\'' +
                ", tokenType=" + tokenType +
                ", dataType=" + dataType +
                '}';
    }
}
