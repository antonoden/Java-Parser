public class App {

    public static void main(String[] args) throws Exception {
        Parser parser;
        FileFeeder fileFeeder = new FileFeeder("");
        for(int i = 0; i < fileFeeder.getNumberOfFiles(); i++) {
            parser = new Parser();
            parser.parse(fileFeeder.getNextFile());
        }
    }
}

