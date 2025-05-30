import java.util.ArrayList;
import java.util.List;

public class SymbolTable {
    
    private List<Symbol> symbols;

    public SymbolTable() {
        this.symbols = new ArrayList<Symbol>() {{
            add(new Symbol(TokenType.PREDEF.name(),  TokenType.TYPE, TokenType.PREDEF, 0, 0));
            add(new Symbol(TokenType.UNDEF.name(),   TokenType.TYPE, TokenType.PREDEF, 0, 0));
            add(new Symbol(TokenType.ERROR.name(),   TokenType.TYPE, TokenType.PREDEF, 0, 0));
            add(new Symbol(TokenType.INTEGER.name(), TokenType.TYPE, TokenType.PREDEF, 4, 0));
            add(new Symbol(TokenType.BOOLEAN.name(), TokenType.TYPE, TokenType.PREDEF, 4, 0));
            add(new Symbol(TokenType.REAL.name(),    TokenType.TYPE, TokenType.PREDEF, 8, 0));
        }};
    }

    @Override
    public String toString() {
        String tempString = "";

        tempString += "_".repeat(56)+" ";
        tempString += "\n THE SYMBOL TABLE\n";
        tempString += "_".repeat(56)+" ";
        tempString += "\n       NAME       ROLE       TYPE      SIZE      ADDR     \n";
        tempString += "_".repeat(56)+" ";
        tempString += "\n";
        for(Symbol symbol : symbols) {
            if(symbol.getType().getValue() != TokenType.PREDEF.getValue()) {

                tempString += symbol.toString();
            }
        }
        tempString += "_".repeat(56)+" ";
        tempString += "\n STATIC STORAGE REQUIRED is "+getProgramSize()+" BYTES\n";
        tempString += "_".repeat(56)+" ";

        return tempString;
    }

    /*****************************/
    /*         GETTERS           */
    /*****************************/
    public int getProgramSize() {

        int bytes = 0;
        for (Symbol symbol : this.symbols) {
            if(symbol.getType().getValue() != TokenType.PREDEF.getValue()) {
                if(symbol.getType().getValue() != TokenType.PROGRAM.getValue()) {

                    bytes += symbol.getSize();
                }
            }
        }
        return bytes;
    }

    public TokenType getType(String name) {

        for(Symbol symbol : symbols) {

            if(symbol.getName().equals(name)) { 
                
                return symbol.getType(); 
            }
        }
        return TokenType.ERROR;
    }

    public int getSize(TokenType type) {

        for(Symbol symbol : symbols) {
            if(symbol.getName().equals(type.name())) {

                return symbol.getSize();
            }
        }
        return 0;
    }

    /*****************************/
    /*          SETTERS          */
    /*****************************/
    public void setTypeUndefinedVariables(TokenType type) {

        for(int i=0; i<symbols.size(); i++) {

            if(symbols.get(i).getType().getValue() == TokenType.UNDEF.getValue()) {
                
                symbols.get(i).setType(type);
                symbols.get(i).setSize(getSize(type));
                if(symbols.get(i-1).getRole().getValue() == TokenType.PROGRAM.getValue()) {

                    symbols.get(i).setAddress(0);
                } else {

                    symbols.get(i).setAddress(
                                    symbols.get(i-1).getAddress()
                                    + symbols.get(i-1).getSize());
                }
                for(Symbol symbol : symbols) {
                    if(symbol.getRole().getValue() == TokenType.PROGRAM.getValue()) {

                        symbol.setSize(symbol.getSize()+getSize(type));
                    }
                }
            }
        }
    }

    /*****************************/
    /*          ADDERS           */
    /*****************************/
    public void addProgramName(String name) {

        if(!findName(name)) {

            symbols.add(new Symbol(
                            name, 
                            TokenType.PROGRAM, 
                            TokenType.PROGRAM, 
                            0, 
                            0));
        }
    }

    public void addVariableName(String name) {

        if(!findName(name)) {

            symbols.add(new Symbol(
                            name, 
                            TokenType.VAR, 
                            TokenType.UNDEF, 
                            0, 
                            0));
        }
    }

    /*****************************/
    /*          FINDERS          */
    /*****************************/
    public boolean findName(String name) {

        for(Symbol symbol : symbols) {

            if(symbol.getName().equals(name)) { return true; }
        }
        return false;
    }
}
