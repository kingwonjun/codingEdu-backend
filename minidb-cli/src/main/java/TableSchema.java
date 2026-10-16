import java.util.ArrayList;
import java.util.List;

public class TableSchema {

    private List<ColumnInfo> columnInfo = new ArrayList<>();
    private List<TableConstraint> tableConstraints = new ArrayList<>();


    public List<TableConstraint> getTableConstraints() {
        return tableConstraints;
    }

    public void setTableConstraints(List<TableConstraint> tableConstraints) {
        this.tableConstraints = tableConstraints;
    }

    public List<ColumnInfo> getColumnInfo() {
        return columnInfo;
    }

    public void setColumnInfo(List<ColumnInfo> columnInfo) {
        this.columnInfo = columnInfo;
    }
}
