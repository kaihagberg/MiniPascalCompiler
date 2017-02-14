package syntaxtree;

import java.util.ArrayList;

/**
 * Created by Kai on 2/14/17.
 */
public class SubProgramDeclarationsNode extends SyntaxTreeNode {

    private ArrayList<SubProgramNode> prog = new ArrayList<SubProgramNode>();

    public void addSubProgramDeclaration(SubProgramNode subProgram) {
        prog.add(subProgram);
    }


}
