import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileFeeder {
    
    private List<Path> filePaths;
    private int currentFilePath;

    public FileFeeder() {
        this.filePaths = new ArrayList<Path>();
        loadFilePaths("");
    }

    /* Constructor makes is possible to filter out testfiles by only feeding those starting with variable searchString */
    public FileFeeder(String searchString) {
        this.filePaths = new ArrayList<Path>();
        loadFilePaths(searchString);
    }

    /* Loads in filepaths from testfiels directory */
    private void loadFilePaths(String searchString) {
        try {
            Files.list(Paths.get("Parser/TestSuite/"))
                .filter(Files::isRegularFile)
                .filter(file -> file.getFileName().toString().startsWith(searchString))
                .forEach(filePaths::add);
            currentFilePath = 0;
        } catch (IOException e) {
            System.err.println("Error reading directory: " + e.getMessage());
        }
    }

    /* Returns next filepath in filepathlist. If standing at last filepath next resets to first filepath in list */
    public Path getNextFile() {
        if (currentFilePath == filePaths.size()) {
            currentFilePath = 0;    
        }
        return this.filePaths.get(currentFilePath++);
    }

    /* Returns number of filespaths */
    public int getNumberOfFiles() {
        return this.filePaths.size();
    }
}
