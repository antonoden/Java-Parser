public class OpEntry {
    
    private char operator;
    private TokenType arg1;
    private TokenType arg2;
    private TokenType result;

    public OpEntry(char operator, TokenType arg1, TokenType arg2, TokenType result) {

        this.operator = operator;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
    }

    public char getOperator() {
        return this.operator;
    }

    public TokenType getArg1() {
        return this.arg1;
    }

    public TokenType getArg2() {
        return this.arg2;
    }

    public TokenType getResult() {
        return this.result;
    }

}
