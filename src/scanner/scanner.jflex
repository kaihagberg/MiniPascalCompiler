/**
 * Kai Hagberg - lexical anaylzer
 */
 
/* Declarations */
 
package scanner;
 
%%

%public
%class MyScanner		/* Names the produced java file */
%function nextToken		/* Renames the yylex() function */
%type Token      /* Defines the return type of the scanning function */
%line
%column
%eofval{
  return null;
%eofval}

%{
  public int getLine() {return yycolumn;}

  public int getColumn() {return yycolumn;}

  private LookupTable table = new LookupTable();
%}
 
/* Definitions */

letter         = [a-zA-Z]
word           = {letter}+
digit          = [0-9]
number         = {digit}+
whitespace	   = [ \n\t\r]|(([\{])([^\{])*([\}]))
symbol 		 	  = [;,.:\[\]()+-=<>\*/]
id 		 	 	  = ({letter}+)({letter}|{digit})*
symbols 	 	  = {symbol}|:=|<=|>=|<>
optional_fraction = ([.])({number})
optional_exponent = ([E]([+]|[-])?{number})
num 			  = {number}{optional_fraction}?{optional_exponent}?{optional_fraction}?
other             = .
 
 
%%
 
/* Lexical Rules */

{id}            {

                    TokenType tt = table.get(yytext());
                        if(tt == null){
                		    return new Token(yytext(), TokenType.ID);
                		    }
                		    else{
                		        return new Token(yytext(), tt);
                		    }

                }


{num}           {
                    // found number
                    return new Token(yytext(), TokenType.NUMBER);
                }
 				
{whitespace}	{   /* ignore whitespace and comments */

                    if((yytext().charAt(0)=='{')&&(yytext().charAt(yytext().length()-1)=='}')) {
                        System.out.println("Comment: " + yytext());
                    }
                }

{symbols}       {
                    TokenType tt = table.get(yytext());
                    return(new Token(yytext(),tt));
                }
 
.				{ 
 					System.out.println("Illegal char: '" + yytext() + "' found."); 
 				}
 				