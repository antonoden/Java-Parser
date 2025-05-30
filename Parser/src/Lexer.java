import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private List<String> program;
    private String filename;
    private String filepath;
    private List<Token> tokens;
    private KeywordTable keywordtable;
    private int currentToken;

    public Lexer(Path filePath) {  

        /********************************************* */
        /* reading in file from argumented filepath */
        try {
            this.program = Files.readAllLines(filePath);
            this.program.add("$");
            this.filepath = filePath.toString();
            this.filename = filePath.getFileName().toString();
        } catch (IOException e) {
            System.out.println("IO Exception attempting to read file: " + e.getMessage());
        } catch (SecurityException e) {
            System.out.println("Security Exception attempting to read file: " + e.getMessage());
        }

        /****************************************** */
        /* lexify the program read in above */
        try {
            this.tokens = new ArrayList<Token>(); // list with Tokens to be filled
            
            // supporting objects filling list above
            this.keywordtable = new KeywordTable();
            int charpointer;
            StringBuilder tempString = new StringBuilder();

            for(String line : program) if(!line.isEmpty()) {
                
                charpointer = 0;

                // loops throught line
                while(charpointer < line.length()) {
                    
                    // reset tempString so it can be filled by next lexeme
                    tempString.delete(0, tempString.length());

                    // skip/ignore spaces
                    while(line.charAt(charpointer) == ' ') {
                        if(++charpointer >= line.length()) break;
                    }

                    // If line.length reached we have no more lexemes to find in line. 
                    if(charpointer >= line.length()) break;
                    
                    // lexeme is assumed to be an ID or a keyword
                    if(Character.isLetter(line.charAt(charpointer))) {
                        // fetch all characters in ID/keyword
                        do {
                            tempString.append(line.charAt(charpointer++));

                            if(charpointer >= line.length()) break;

                        } while(Character.isDigit(line.charAt(charpointer)) || 
                                Character.isLetter(line.charAt(charpointer)));

                        // checks if keyword and adds keywords, if not add as ID. 
                        if(keywordtable.isKeyword(tempString.toString())) {
                            this.tokens.add(new Token(
                                            keywordtable.getTokentype(tempString.toString()), 
                                            tempString.toString()
                                        ));
                        } else {
                            this.tokens.add(new Token(TokenType.ID, tempString.toString()));
                        }

                    } 

                    // lexeme is assumed to be a number
                    else if(Character.isDigit(line.charAt(charpointer))) {

                        while(Character.isDigit(line.charAt(charpointer))) {

                            tempString.append(line.charAt(charpointer++));

                            if(charpointer >= line.length()) break;
                        }
                        this.tokens.add(new Token(TokenType.NUMBER, tempString.toString()));
                    }

                    // lexeme is assumed to be a special character
                    else {  
                        if(line.charAt(charpointer) == ':' && line.length()-1 > charpointer) {
                            if(line.charAt(charpointer+1) == '=') {

                                this.tokens.add(new Token(TokenType.ASSIGN));
                                charpointer += 2;
                            } else {

                                this.tokens.add(new Token(line.charAt(charpointer)));
                                charpointer++;
                            }
                        } else {

                            this.tokens.add(new Token(line.charAt(charpointer)));
                            charpointer++;
                        }

                        
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception attempting to lexify program: " + e.getMessage());
        }

        this.currentToken = 0;
    }
    /* END CONSTRUCTOR */

    public String getFilename() {

        return this.filename;
    }

    public String getfilepath() {

        return this.filepath;
    }

    public Token lookahead() {

        return this.tokens.get(currentToken);
    }

    public Token getNextToken() {

        return this.tokens.get(currentToken++);
    }

    public boolean programEmpty() {
        if(this.tokens.isEmpty()) { 
            
            return true; 
        }
        return false;
    }

    /* Returns tokens still left to return from this.tokens */
    public String getTokensLeftToParse() {
        String tokenlistTail = "";
        int tokenlistLength = this.tokens.size();
        for(int i=currentToken-1; i<tokenlistLength-1; i++) {
            tokenlistTail += this.tokens.get(i).getValue()+" ";
        }
        return tokenlistTail;
    }

/************************************************************************ */
/* FUNCTIONS TO PRINT OUT INFORMATION FROM THE LEXER */
/*************************************************************************** */

    /* Function that prints out the program that's in buffer */
    public void printProgram() {
        
        for (String line : program) {
            
            System.out.println(line);
        }
    }

    public void printLexemeList() {
        System.out.print("Lexemelist: ");
        for(Token token : this.tokens) {
            System.out.print("["+ token.getValue() + "]");
        }
        System.out.println();
    }
}
