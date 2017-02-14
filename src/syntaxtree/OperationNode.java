package syntaxtree;

import scanner.TokenType;

/**
 * Represents any operation in an expression.
 */
public class OperationNode extends ExpressionNode {

    private ExpressionNode left;

    private ExpressionNode right;

    private TokenType operation;

    /**
     * Creates an operation node given the operation token.
     * @param op
     */
    public OperationNode(TokenType op) {
        this.operation = op;
    }

    public ExpressionNode getLeft() {
        return this.left;
    }
    public ExpressionNode getRight() {
        return this.right;
    }
    public TokenType getOperation() {
        return this.operation;
    }

    public void setLeft(ExpressionNode node) {
        this.left = node;
    }

    public void setRight(ExpressionNode node) {
        this.right = node;
    }


}
