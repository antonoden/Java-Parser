import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private List<String> program;
    private String filename;
    private String filepath;
    private List<Token> lexemes;
    private TokenTable tokentable;
    private KeywordTable keywordtable;

    public Lexer(Path filePath) {  

        /********************************************* */
        /* reading in file from argumented filepath */
        try {
            this.program = Files.readAllLines(filePath);
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
            this.lexemes = new ArrayList<Token>(); // list with Tokens to be filled with lexemes
            
            // supporting objects filling list above
            this.keywordtable = new KeywordTable();
            this.tokentable = new TokenTable();
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

                        // checks if keyword and add if so, if not add as ID. 
                        if(keywordtable.isKeyword(tempString.toString())) {
                            
                            lexemes.add(new Token(
                                            keywordtable.getTokentype(tempString.toString()), 
                                            tempString.toString()
                                        ));
                        } else {
                            lexemes.add(new Token(TokenType.ID, tempString.toString()));
                        }

                    } 

                    // lexeme is assumed to be a number
                    else if(Character.isDigit(line.charAt(charpointer))) {

                        while(Character.isDigit(line.charAt(charpointer))) {

                            tempString.append(line.charAt(charpointer++));

                            if(charpointer >= line.length()) break;
                        }
                        lexemes.add(new Token(TokenType.NUMBER, tempString.toString()));
                    }

                    // lexeme is assumed to be a special character
                    else {  
                        tempString.append(line.charAt(charpointer));
                        if(line.charAt(charpointer) == ':') {
                            charpointer++;
                            if(line.charAt(charpointer) == '=') {
                                tempString.append(line.charAt(charpointer++));
                            }
                        } else {
                            charpointer++;
                        }
                        lexemes.add(new Token(TokenType.CHAR, tempString.toString()));
                    }
                    //System.out.println("charpointer:" + charpointer + ", length:" + line.length());
                }
            }
        } catch (Exception e) {
            System.out.println("Exception attempting to lexify program: " + e.getMessage());
        }

        System.out.print("Lexemelist: ");
        for(Token token : lexemes) {
            System.out.print("["+ token.getValue() + "]");
        }
        System.out.println();
    }

    public String getFilename() {
        return this.filename;
    }

    public String getfilepath() {
        return this.filepath;
    }

/************************************************************************ */
/* FUNCTIONS TO PRINT OUT INFORMATION FROM THE LEXER */
/*************************************************************************** */
    /* Function to create division within output */
    private void printLineDivider(int numberOfCharacters, char character) {
        for(int i=0; i<numberOfCharacters; i++) {
            System.out.print(character);
        }
        System.out.println();
    }

    /* printing out filename of program in buffer */
    public void printProgramFilename() {
        System.out.println(filename);
    }

    /* Function that prints out the program that's in buffer */
    public void printProgram() {
        printLineDivider(56, '_');
        for (String line : program) {
            System.out.println(line);
        }
        printLineDivider(56, '_');
    }
}
