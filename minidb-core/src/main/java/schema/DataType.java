package schema;

public enum DataType {
    // 정수형
    TINYINT, SMALLINT, INT, BIGINT,

    // 실수형
    FLOAT, DOUBLE, DECIMAL,

    // 문자열
    CHAR, VARCHAR, TEXT, BINARY, VARBINARY, BLOB,

    // 날짜와 시간
    DATE, TIME, DATETIME, TIMESTAMP, YEAR,

    // 논리형
    BOOLEAN,

    // 기타
    ENUM, SET, JSON, UUID, XML
}
