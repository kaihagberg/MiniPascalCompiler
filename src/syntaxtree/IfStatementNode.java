package syntaxtree;

/**
 * Represents an if statement in Pascal.
 * The if statement includes a boolean expression and two statements.
 */
public class IfStatementNode extends SyntaxTreeNode {

    private ExpressionNode test;
    private StatementNode thenStatement;
    private StatementNode elseStatement;

    public ExpressionNode getTest() {
        return test;
    }

    public void setTest(ExpressionNode test) {
        this.test = test;
    }

    public StatementNode getThenStatement() {
        return thenStatement;
    }

    public void setThenStatement(StatementNode thenStatement) {
        this.thenStatement = thenStatement;
    }

    public StatementNode getElseStatement() {
        return elseStatement;
    }

    public void setElseStatement(StatementNode thenStatement) {
        this.elseStatement = elseStatement;
    }

    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Assignment\n";
        answer += this.test.indentedToString(level + 1);
        answer += this.thenStatement.indentedToString(level + 1);
        answer += this.elseStatement.indentedToString(level + 1);
        return answer;
    }

}
