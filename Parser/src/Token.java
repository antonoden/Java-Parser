public class Token {

    private final TokenType type;
    private final String value;
    private final int asciitype;
    public final boolean isAscii;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
        this.asciitype = -1;
        this.isAscii = false;
    }

    public Token(TokenType type) {
        this.type = type;
        this.value = type.toString();
        this.asciitype = -1;
        this.isAscii = false;
    }

    public Token(char character) {
        this.type = TokenType.CHAR;
        this.value = String.valueOf(character);
        this.asciitype = (int) character;
        this.isAscii = true;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue () {
        return value;
    }

    public int getAsciiType () {
        return asciitype;
    }

}
