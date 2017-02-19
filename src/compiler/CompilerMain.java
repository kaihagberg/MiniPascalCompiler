package compiler;

import parser.MyParser;
import parser.Symbol;

import java.io.File;

/**
 * This class contains a main which runs the parser as a compiler.
 * It takes in a filename and outputs a success/failure along with printing out the contents of the symbol table.
 */
public class CompilerMain {

    public static void main(String[] args) {
        File fileToParse = new File("src/test/simplest.pas");
        MyParser parser = new MyParser(fileToParse.toString(), true);
        boolean isValid = parser.program();

        if(isValid) {
            System.out.println("File was parsed successfully");
        } else {
            System.out.println("Unable to parse file");
        }

        for(Symbol symbol : parser.symbolTable.getSymbols()) {
            System.out.println(symbol.toString());
        }
    }
}
