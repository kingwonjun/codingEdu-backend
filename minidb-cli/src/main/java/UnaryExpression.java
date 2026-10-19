public class UnaryExpression implements Expression{

    private final UnaryOperator operator;
    private final Expression operand;

    public UnaryExpression(UnaryOperator operator, Expression operand) {
        this.operator = operator;
        this.operand = operand;
    }

    public UnaryOperator operator() {
        return operator;
    }

    public Expression operand() {
        return operand;
    }
}
