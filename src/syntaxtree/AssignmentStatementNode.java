package syntaxtree;

/**
 * Created by Kai on 2/14/17.
 */
public class AssignmentStatementNode extends SyntaxTreeNode {

    private VariableNode lvalue;
    private ExpressionNode expression;

    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Assignment\n";
        answer += this.lvlalue.indentedToString(level + 1);
        answer += this.expression.indentedToString(level + 1);
        return answer;
    }
}
