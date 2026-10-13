package read;

import java.nio.charset.StandardCharsets;

public class FileInfo {
    private static final byte[] MAGIC_NUMBER = "minidb".getBytes(StandardCharsets.UTF_8);

    private int version = 1;
    private int recordCount;

    public FileInfo() {
    }

    public FileInfo(int version, int recordCount) {
        setVersion(version);
        setRecordCount(recordCount);
    }

    public byte[] getMagicNumber() {
        return MAGIC_NUMBER.clone();
    }

    public void setRecordCount(int recordCount) {
        if (recordCount < 0) {
            throw new IllegalArgumentException("recordCount는 0 이상이야 합니다." + recordCount);
        }
        this.recordCount = recordCount;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public int getVersion() {
        return version;
    }
}
