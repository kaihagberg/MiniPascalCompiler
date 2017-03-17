package syntaxtree;

/**
 * Represents a value or number in an expression.
 * Created by Kai on 2/9/17.
 */
public class ValueNode extends ExpressionNode {

    String attribute;

    /**
     * Creates a ValueNode with given attribute
     * @param attr The attribute for this node
     */
    public ValueNode(String attr) {
        this.attribute = attr;
    }

    /**
     * Returns the attribute of this node.
     * @return The attribute of the value node.
     */
    public String getAttribute() {
        return(this.attribute);
    }

    /**
     * Returns the attribute as description of the node.
     * @return The attribute String of the node.
     */
    @Override
    public String toString() {
        return(attribute);
    }

    /**
     *
     * @param level
     * @return
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Value: " + this.attribute + "\n";
        return answer;
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        boolean answer = false;
        if(o instanceof ValueNode) {
            ValueNode other = (ValueNode)o;

        }
        return answer;
    }
}
