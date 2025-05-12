import java.nio.file.Path;

public class Parser {

    private TokenTable tokens;
    private KeywordTable keywords;
    private Lexer lexer;

    public Parser() {
        this.tokens = new TokenTable();
        this.keywords = new KeywordTable();     
    }

    public void parse(Path filepath) {
        this.lexer = new Lexer(filepath);
        System.out.printf("TestSuite/%s\n", this.lexer.getFilename());
        this.lexer.printProgram();
    }
}
