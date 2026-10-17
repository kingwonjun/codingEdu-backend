public class ColumnInfo {
    private String name;
    private String type;
    private Integer maxLength;
    private boolean nullable = true;
    private boolean hasDefaultNull;
    private Object defaultValue;

    public ColumnInfo() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isHasDefaultNull() {
        return hasDefaultNull;
    }

    public void setHasDefaultNull(boolean hasDefaultNull) {
        this.hasDefaultNull = hasDefaultNull;
    }
}
