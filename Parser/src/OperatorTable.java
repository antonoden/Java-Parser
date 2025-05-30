import java.util.List;

public class OperatorTable {

    private final List<OpEntry> OPTAB = List.of(
        new OpEntry('+', TokenType.INTEGER, TokenType.INTEGER, TokenType.INTEGER),
        new OpEntry('+', TokenType.REAL,    TokenType.REAL,    TokenType.REAL),
        new OpEntry('+', TokenType.INTEGER, TokenType.REAL,    TokenType.REAL),
        new OpEntry('+', TokenType.REAL,    TokenType.INTEGER, TokenType.REAL),
        new OpEntry('*', TokenType.INTEGER, TokenType.INTEGER, TokenType.INTEGER),
        new OpEntry('*', TokenType.REAL,    TokenType.REAL,    TokenType.REAL),
        new OpEntry('*', TokenType.INTEGER, TokenType.REAL,    TokenType.REAL),
        new OpEntry('*', TokenType.REAL,    TokenType.INTEGER, TokenType.REAL)
    );

    public OperatorTable() {
    }

    public TokenType getOperatorType(char operator, TokenType arg1, TokenType arg2) {

        for(OpEntry OP: this.OPTAB) {
            
            if( OP.getOperator() == operator && 
                OP.getArg1().getValue() == arg1.getValue() &&
                OP.getArg2().getValue() == arg2.getValue() ) {

                return OP.getResult();
            }
        }

        if (!isArgumentValid(arg1)) { }

        if (!isArgumentValid(arg2)) { }

        if (!isOperatorValid(operator)) { }
        
        return TokenType.UNDEF;
    }

    private boolean isArgumentValid(TokenType arg) {

        for(OpEntry OP : this.OPTAB) {
            
            if (arg.getValue() == OP.getArg1().getValue() || arg.getValue() == OP.getArg2().getValue()) {
                
                return true; 
            } 
        }

        return false;
    }

    private boolean isOperatorValid(char operator) {

        for(OpEntry OP : this.OPTAB) {

            if (operator == OP.getOperator()) {

                return true;
            }
        }

        return false;
    }
}