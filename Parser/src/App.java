public class App {

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        FileFeeder fileFeeder = new FileFeeder("fun");
        for(int i = 0; i < fileFeeder.getNumberOfFiles(); i++) {
            parser.parse(fileFeeder.getNextFile());
        }
    }
}

