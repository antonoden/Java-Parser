import java.util.ArrayList;
import java.util.List;

public class KeywordTable {

    private List<Token> keywords;

    public KeywordTable() {
        this.keywords = new ArrayList<Token>() {{
            add(new Token(TokenType.PROGRAM,   "program"));
            add(new Token(TokenType.INPUT,     "input"));
            add(new Token(TokenType.OUTPUT,    "output"));
            add(new Token(TokenType.VAR,       "var"));
            add(new Token(TokenType.BEGIN,     "begin"));
            add(new Token(TokenType.END,       "end"));
            add(new Token(TokenType.BOOLEAN,   "boolean"));
            add(new Token(TokenType.INTEGER,   "integer"));
            add(new Token(TokenType.REAL,      "real"));
        }};
    }

    public void printTable() {
        System.out.println("_".repeat(56));
        for(int i=0; i<keywords.size(); i++) {
            if(keywords.get(i).isAscii) {
                System.out.printf("\t%s\t%s\n", keywords.get(i).getValue(), keywords.get(i).getAsciiType());
            } else {
                System.out.printf("\t%s\t%s\n", keywords.get(i).getType(), keywords.get(i).getType().getValue());
            }
        }
        System.out.println("_".repeat(56));
    }

    /* Returns true if string feeded to function is contained within keywordtable */
    public boolean isKeyword(String string) {
        for(Token keyword : keywords) {
            if(keyword.getValue().equals(string)) {
                return true;
            }
        }
        return false;
    }

    /* retunrns Tokentype of argumented string. If string is not contained in table Tokentype.ID is returned */
    public TokenType getTokentype(String string) {
        for(Token keyword : keywords) {
            if(keyword.getValue().equals(string)) {
                return keyword.getType();
            }
        }
        return TokenType.ID;
    }
}
