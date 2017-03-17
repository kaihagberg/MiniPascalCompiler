package compiler;

import parser.MyParser;
import parser.Symbol;
import syntaxtree.ProgramNode;

import java.io.File;

/**
 * This class contains a main which runs the parser as a compiler.
 * It takes in a filename and outputs a success/failure along with printing out the contents of the symbol table.
 */
public class CompilerMain {

    public static void main(String[] args) {
        File fileToParse = new File("src/test/simple.pas");
        MyParser parser = new MyParser(fileToParse.toString(), true);

        for(Symbol symbol : parser.symbolTable.getSymbols()) {
            System.out.println(symbol.toString());
        }

        ProgramNode programNode = parser.program();
        System.out.println(programNode.indentedToString(0));
        System.out.println(programNode);
    }
}
