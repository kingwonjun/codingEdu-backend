public class LiteralExpression implements Expression{

    private int numberValue;
    private String stringValue;
    private boolean isHasNull;

    public int getNumberValue() {
        return numberValue;
    }

    public void setNumberValue(int numberValue) {
        this.numberValue = numberValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public boolean isHasNull() {
        return isHasNull;
    }

    public void setHasNull(boolean hasNull) {
        isHasNull = hasNull;
    }
}
