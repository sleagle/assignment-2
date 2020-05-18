package au.edu.utas.sddhewa.assignment.util;

public enum FieldKey {

    CREATE_TABLE("CREATE TABLE "),
    CREATE_TABLE_OPEN(" ( "),
    CREATE_TABLE_CLOSE(" );"),
    PK_AUTO_INCREMENT(" INTEGER PRIMARY KEY AUTOINCREMENT, "),
    INT(" INT"),
    STRING(" STRING"),
    BOOLEAN(" BOOLEAN"),
    DATE(" DATE"),
    FLOAT(" FLOAT"),
    BLOB(" BLOB "),
    COMMA(", "),
    NOT_NULL(" NOT NULL,"),
    NOT_NULL_WITHOUT(" NOT NULL");

    public String value;

    private FieldKey(String value) {
        this.value = value;
    }
}
