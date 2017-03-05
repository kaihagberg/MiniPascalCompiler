package syntaxtree;

/**
 * Created by Kai on 2/14/17.
 */
public class AssignmentStatementNode extends StatementNode {

    private VariableNode lvalue;
    private ExpressionNode expression;

    public VariableNode getLvalue() {
        return lvalue;
    }

    public void setLvalue(VariableNode lvalue) {
        this.lvalue = lvalue;
    }

    public ExpressionNode getExpression() {
        return expression;
    }

    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }



    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Assignment\n";
        answer += this.lvalue.indentedToString(level + 1);
        answer += this.expression.indentedToString(level + 1);
        return answer;
    }
}
