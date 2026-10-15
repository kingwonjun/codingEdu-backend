public class ColumnInfo {
    private String name;
    private String type;
    private Integer maxLength;

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
}
