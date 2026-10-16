import java.util.ArrayList;
import java.util.List;

public class TableConstraint {
    private List<String> pk = new ArrayList<>();
    private List<List<String>> unique = new ArrayList<>();

    public List<List<String>> getUnique() {
        return unique;
    }

    public void setUnique(List<List<String>> unique) {
        this.unique = unique;
    }

    public List<String> getPk() {
        return pk;
    }

    public void setPk(List<String> pk) {
        this.pk = pk;
    }
}
