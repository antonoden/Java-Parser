public class Symbol {
    
    private String name;
    private TokenType role;
    private TokenType type;
    private int size;
    private int address;

    public Symbol(String name, TokenType role, TokenType type, int size, int address) {

        this.name = name;
        this.role = role;
        this.type = type;
        this.size = size;
        this.address = address;
    }

    public String getName() { return this.name; }
    public TokenType getRole() { return this.role; }
    public TokenType getType() { return this.type; }
    public int getSize() { return this.size; }
    public int getAddress() { return this.address; }

    public void setType(TokenType type) { this.type = type; }
    public void setSize(int size) { this.size = size; }
    public void setAddress(int address) { this.address = address; }

    @Override
    public String toString() {
        
        String tempString = "";
        tempString += " ".repeat(11-this.name.length());
        tempString += this.name;
        tempString += " ".repeat(11-this.role.name().length());
        tempString += getRole().toString();
        tempString += " ".repeat(11-this.type.name().length());
        tempString += getType().toString();
        tempString += " ".repeat(10-String.valueOf(this.size).length());
        tempString += String.valueOf(this.size);
        tempString += " ".repeat(10-String.valueOf(this.address).length());
        tempString += String.valueOf(this.address)+" ";
        tempString += "\n";

        return tempString;
    }

}
