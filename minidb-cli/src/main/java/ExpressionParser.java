import java.util.List;
import java.util.Locale;

public class ExpressionParser {

    private final List<String> tokens;
    private int position = 0;

    public ExpressionParser(List<String> tokens) {
        this.tokens = tokens;
    }

    private Expression parsePrimary() {
        String token = tokens.get(position);

        position++;
        // 일단 시작은 정수
        if (isInteger(token)) {
            return new LiteralExpression(Integer.parseInt(token));
        } else if (isString(token)) {
            token = token.substring(1, token.length() - 1);
            return new LiteralExpression(token);
        } else {
            return new ColumnExpression(token);
        }
    }

    private Expression parseCompare() {
        //position++을 굳이 더할 필요가 있나? 이거는 나중에 봐야겠다.
        Expression left = parsePrimary();
        String token = tokens.get(position);
        BinaryOperator operator  = parseOperator(token);
        position++;
        Expression right = parsePrimary();

        return new BinaryExpression(left, operator, right);
    }

    private Expression parseLogical() {
        Expression left = parseCompare();

        if (tokens.get(position).equalsIgnoreCase("AND")){
            BinaryOperator operator = BinaryOperator.AND;
            position++;
            Expression right = parseCompare();
            return new BinaryExpression(left, operator, right);
        }
        if (tokens.get(position).equalsIgnoreCase("OR")){
            BinaryOperator operator = BinaryOperator.OR;
            position++;
            Expression right = parsePrimary();

        }
        if (tokens.get(position).equalsIgnoreCase(")")) {
            return parseCompare();
        }
        return new BinaryExpression(left, operator, right);
    }


    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isString(String token) {
        return token.length() >= 2 && token.startsWith("'") && token.endsWith("'");
    }

    public static BinaryOperator parseOperator(String token) {
        return switch (token.toUpperCase()) {
            case "+" -> BinaryOperator.ADD;
            case "-" -> BinaryOperator.SUBTRACT;
            case "*" -> BinaryOperator.MULTIPLY;
            case "/" -> BinaryOperator.DIVIDE;

            case ">" -> BinaryOperator.GREATER_THAN;
            case ">=" -> BinaryOperator.GREATER_THAN_OR_EQUAL;
            case "<" -> BinaryOperator.LESS_THAN;
            case "<=" -> BinaryOperator.LESS_THAN_OR_EQUAL;

            case "=" -> BinaryOperator.EQUAL;
            case "!=", "<>" -> BinaryOperator.NOT_EQUAL;

            case "AND" -> BinaryOperator.AND;
            case "OR" -> BinaryOperator.OR;

            default -> throw new IllegalArgumentException(
                    "Unknown binary operator: " + token
            );
        };
    }
}

