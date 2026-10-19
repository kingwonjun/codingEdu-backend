public class LiteralExpression implements Expression{

    private final Object value;

    public LiteralExpression(Object value) {
        this.value = value;
    }

    public Object value() {
        return value;
    }
}
