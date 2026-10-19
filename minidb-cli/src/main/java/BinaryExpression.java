public class BinaryExpression implements Expression{

    private final Expression left;
    private final BinaryOperator operator;
    private final Expression right;


    public BinaryExpression(Expression left, BinaryOperator operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expression left() {
        return left;
    }

    public Expression right() {
        return right;
    }

    public BinaryOperator operator() {
        return operator;
    }
}
