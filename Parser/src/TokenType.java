public enum TokenType {

    ID(257), 
    NUMBER(258), 
    CHAR(259), 
    ASSIGN(261), 
    PREDEF(262), 
    TEMPTY(263), 
    UNDEF(264), 
    ERROR(265), 
    TYPE(266), 
    PROGRAM(267), 
    INPUT(268), 
    OUTPUT(269), 
    VAR(270), 
    BEGIN(271), 
    END(272), 
    BOOLEAN(273), 
    INTEGER(274), 
    REAL(275);

    private final int value;

    TokenType(int value) {
        this.value = value;
    }

    TokenType() {
        this.value = -1;
    }

    public int getValue() {
        return value;
    }
}
