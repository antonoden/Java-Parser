public enum TokenType {

    ID(257, "id"), 
    NUMBER(258, "number"), 
    CHAR(259, "char"), 
    ASSIGN(261, ":="), 
    PREDEF(262, "predef"), 
    TEMPTY(263, "tempty"), 
    UNDEF(264 ,"undef"), 
    ERROR(265, "error"), 
    TYPE(266, "type"), 
    PROGRAM(267, "program"), 
    INPUT(268, "input"), 
    OUTPUT(269, "output"), 
    VAR(270, "var"), 
    BEGIN(271, "begin"), 
    END(272, "end"), 
    BOOLEAN(273, "boolean"), 
    INTEGER(274, "integer"), 
    REAL(275, "real");

    private final int value;
    private final String name;

    TokenType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    TokenType() {
        this.value = -1;
        this.name = "";
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
