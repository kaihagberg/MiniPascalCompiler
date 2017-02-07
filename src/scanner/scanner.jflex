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

digit          = [0-9]
number         = {digit}+
symbol         = "+" | "-" | "*" | "/" | "(" | ")"
whitespace		= [ \n\t]
 
 
%%
 
/* Lexical Rules */


{number}        {
                    // found number
                    Token t = new Token(yytext(), TokenType.NUMBER);
                    return t;
                }
 				
{whitespace}	{ /* ignore whitespace */ }

{symbol}       {
                    // found symbol
                    String lexeme = yytext();
                    TokenType ett = table.get(lexeme);
                    Token t = new Token(yytext(), ett);
                    return t;
                }
 
.				{ 
 					System.out.println("Illegal char: '" + yytext() + "' found."); 
 				}
 				