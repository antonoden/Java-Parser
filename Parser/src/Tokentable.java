import java.util.ArrayList;
import java.util.List;

public class TokenTable {

    private List<Token> tokens;

    public TokenTable() {
        this.tokens = new ArrayList<Token>() {{
            add(new Token(TokenType.ID,      "id"));
            add(new Token(TokenType.NUMBER,  "number"));
            add(new Token(TokenType.ASSIGN,  ":="));
            add(new Token(TokenType.PREDEF,  "predef"));
            add(new Token(TokenType.TEMPTY,  "tempty"));
            add(new Token(TokenType.UNDEF,   "undef"));
            add(new Token(TokenType.ERROR,   "error"));
            add(new Token(TokenType.TYPE,    "type"));
            add(new Token('$'));
            add(new Token('('));
            add(new Token(')'));
            add(new Token('*'));
            add(new Token('+'));
            add(new Token(','));
            add(new Token('-'));
            add(new Token('.'));
            add(new Token('/'));
            add(new Token(':'));
            add(new Token(';'));
            add(new Token('='));
        }};
    }

    private void printLineDivider(int numberOfCharacters, char character) {
        for(int i=0; i<numberOfCharacters; i++) {
            System.out.print(character);
        }
        System.out.println();
    }

    public void printTable() {
        printLineDivider(56, '_');
        for(int i=0; i<tokens.size(); i++) {
            if(tokens.get(i).isAscii) {
                System.out.printf("\t%s\t%s\n", tokens.get(i).getValue(), tokens.get(i).getAsciiType());
            } else {
                System.out.printf("\t%s\t%s\n", tokens.get(i).getType(), tokens.get(i).getType().getValue());
            }
        }
        printLineDivider(56, '_');
    }

    public boolean isValidSymbol(char symbol) {
        for(Token token : tokens) {
            if(token.isAscii) {
                if(token.getValue() == Character.toString(symbol)) {
                    return true;
                }
            }
        }
        return false;
    }
}
