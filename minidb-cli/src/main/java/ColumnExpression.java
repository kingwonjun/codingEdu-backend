public class ColumnExpression implements Expression{
    private final String name;

    public ColumnExpression(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }
}
