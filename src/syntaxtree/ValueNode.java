package syntaxtree;

/**
 * Created by Kai on 2/9/17.
 */
public class ValueNode extends ExpressionNode {

    String attribute;

    public ValueNode(String attr) {
        this.attribute = attr;
    }

    public String getAttribute() {
        return(this.attribute);
    }

    @Override
    public String toString() {
        return(attribute);
    }

    @Override
    public String indentedToString(int level) {
        String answer = super.indentedToString(level);
        answer += "Value: " + this.attribute + "\n";
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        boolean answer = false;
        if(o )
    }
}
