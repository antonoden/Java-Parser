import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Lexer {
    private List<String> program;
    private FileReader filereader;
    private BufferedReader buffer;

    public Lexer() {  
    }

    public boolean fetchProgram(Path filePath) {
        try {
            this.program = Files.readAllLines(filePath);
        } catch (IOException e) {
            System.out.println("IO Exception attempting to read file: " + e.getMessage());
        }
        return true;
    }

    /* Function to create division within output */
    private void printLineDivider(int numberOfCharacters, char character) {
        for(int i=0; i<numberOfCharacters; i++) {
            System.out.print(character);
        }
        System.out.println();
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
