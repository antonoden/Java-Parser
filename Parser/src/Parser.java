import java.nio.file.Path;

public class Parser {

    private OperatorTable optab;
    private SymbolTable symtab;
    private Lexer lexer;
    private boolean isParseOK;
    private Token lookahead;

    public Parser() {
        this.optab = new OperatorTable();
        this.symtab = new SymbolTable();
        this.isParseOK = true;     
    }

    public boolean parse(Path filepath) {
        this.lexer = new Lexer(filepath);
        System.out.printf("testing TestSuite/%s\n\n", this.lexer.getFilename());
        System.out.println("_".repeat(56)+" ");
        System.out.printf(" THE PROGRAM TEXT\n");
        System.out.println("_".repeat(56)+" ");

        this.lexer.printProgram();
        System.out.printf("%s", "_".repeat(56)+" ");

        this.lookahead = this.lexer.getNextToken();
        if(this.lookahead.getValue().equals("$")) {

            System.out.printf("\nWARNING:  Input file is empty");
            this.isParseOK = false;
        } else {

            this.parseprogram();
        }

        String tokenTail = this.lexer.getTokensLeftToParse();
        if(tokenTail.length() > 2) {
            System.out.println("\nSYNTAX:\tExtra symbols after end of parse!");
            System.out.printf("\t%s", tokenTail);
            this.isParseOK = false;
        }

        System.out.println("\n"+"_".repeat(56)+" ");
        System.out.printf("%s",this.symtab.toString());

        if(!this.isParseOK) {
            System.out.println("\n \n Parse Failed! ");
        } else {
            System.out.println("\n \n Parse Successful! ");
        }
        System.out.println("_".repeat(56));
        
        return this.isParseOK;
    }

/********************************************************************************** */
/* Functions to find out if expected token match reality in tokenlist                   */
/************************************************************************************* */

    private void match(Token expectedToken) {

        if(this.lookahead.isAscii) {
            if(this.lookahead.getAsciiType() != expectedToken.getAsciiType()) {
                if(expectedToken.isAscii) {

                    System.out.printf("\nSYNTAX:\tSymbol expected %s found %s",
                            expectedToken.getValue(), this.lookahead.getValue());
                    this.isParseOK = false;
                } else {

                    System.out.printf("\nSYNTAX:\tSymbol expected %s found %s",
                            expectedToken.getType().toString(), this.lookahead.getValue());
                    this.isParseOK = false;
                }
            } else {

                this.lookahead = this.lexer.getNextToken();
            }
        } else {
            if (this.lookahead.getType() != expectedToken.getType()) {

                System.out.printf("\nSYNTAX:\tSymbol expected %s found %s",
                                expectedToken.getValue(), 
                                this.lookahead.getValue());
                this.isParseOK = false;
            } else {

                this.lookahead = this.lexer.getNextToken();
            }    
        }
    }

/********************************************************************************** */
/*  Grammar functions */
/*********************************************************************************** */

    private void parseprogram() {
            
        prog_head(); var_part(); stat_part();
    }

    private void prog_head() {

            if(this.lookahead.getType() == TokenType.ID) {

                symtab.addProgramName(this.lookahead.getValue());
            }
            match(new Token(TokenType.PROGRAM)); 

            if(this.lookahead.getType() != TokenType.ID) {
                
                symtab.addProgramName("???");
            } else {

                symtab.addProgramName(this.lookahead.getValue());
            }
            match(new Token(TokenType.ID));

            match(new Token('('));
            match(new Token(TokenType.INPUT));
            match(new Token(','));
            match(new Token(TokenType.OUTPUT));
            match(new Token(')')); 
            match(new Token(';'));       
    }

    /* START VAR PART */

    private void var_part() {

            match(new Token(TokenType.VAR));
            var_dec_list();
    }

    private void var_dec_list() {

        var_dec();
        if(this.lookahead.getType() == TokenType.ID) {
            var_dec_list();
        }
    }

    private void var_dec() {

        id_list();
        match(new Token(':'));
        type();
        match(new Token(';'));
    }

    private void id_list() {
 
        if(this.lookahead.getType() == TokenType.ID) {

            if(!symtab.findName(this.lookahead.getValue())) {

                symtab.addVariableName(this.lookahead.getValue());
            } else {

                System.out.printf("\nSEMANTIC: ID already declared: %s", this.lookahead.getValue());
                this.isParseOK = false;
            }
        }
        match(new Token(TokenType.ID));
        if (this.lookahead.getValue().equals(",")) {
            match(new Token(','));
            id_list();
        }
    }

    private void type() {

        TokenType toktyp = this.lookahead.getType();
        if(toktyp == TokenType.INTEGER || toktyp == TokenType.REAL || toktyp == TokenType.BOOLEAN) {

            symtab.setTypeUndefinedVariables(toktyp);
            match(new Token(toktyp));

        } else {

            symtab.setTypeUndefinedVariables(TokenType.ERROR);
            System.out.printf("\nSYNTAX:	Type name expected found  %s", this.lookahead.getValue());
            this.isParseOK=false;
        }

    }
    /* END VAR PART */

    /* START STAT PART */
    private void stat_part() {

        match(new Token(TokenType.BEGIN));
        stat_list();
        match(new Token(TokenType.END));
        match(new Token('.'));
    }

    private void stat_list() {

        stat();
        if(this.lookahead.getValue().equals(";")) {

            match(new Token(';'));
            stat_list();
        }
    }

    private void stat() {

        assign_stat();
    }

    private void assign_stat() {

        TokenType result, arg;

        if(symtab.findName(lookahead.getValue())) {

            result = symtab.getType(lookahead.getValue());
        } else {

            if(lookahead.getType() == TokenType.ID) {

                System.out.printf("\nSEMANTIC: ID NOT declared: %s", lookahead.getValue());
            }
            this.isParseOK = false;
            result = TokenType.ERROR;
        }
        match(new Token(TokenType.ID));
        match(new Token(TokenType.ASSIGN));

        arg = expr();

        if(result != arg) {

            System.out.printf("\nSEMANTIC: Assign types: %s := %s", result.toString(), arg.toString());
            if(!((result == TokenType.INTEGER || result == TokenType.REAL) && 
                    (arg == TokenType.INTEGER || arg == TokenType.REAL))) {

                this.isParseOK = false;
            }
        }
    }

    private TokenType expr() {

        TokenType arg1, arg2, result;

        arg1 = term();
        if(this.lookahead.getValue().equals("+")) {

            match(new Token('+'));
            arg2 = expr();
            result = optab.getOperatorType('+', arg1, arg2);

        } else { result = arg1; }

        return result;
    }

    private TokenType term() {

        TokenType arg1, arg2, result;

        arg1 = factor();
        if(this.lookahead.getValue().equals("*")) {

            match(new Token('*'));
            arg2 = term();
            result = optab.getOperatorType('*', arg1, arg2);
        
        } else { result = arg1; }

        return result;
    }

    private TokenType factor() {

        TokenType type;

        if(this.lookahead.getValue().equals("(")) {

            match(new Token('('));
            type = expr();
            match(new Token(')'));
        
        } else {

            type = operand();
        }

        return type;
    }

    private TokenType operand() {

        TokenType type;
        
        if(lookahead.getType() == TokenType.NUMBER) {
            
            type = TokenType.INTEGER;
            match(new Token(TokenType.NUMBER));
        
        } else if (lookahead.getType() == TokenType.ID) {

            if(!symtab.findName(lookahead.getValue())) {

                System.out.printf("\nSEMANTIC: ID NOT declared: %s", lookahead.getValue());
                this.isParseOK = false;
            }
            type = symtab.getType(lookahead.getValue());
            match(new Token(TokenType.ID));

        } else {
            System.out.printf("\nSYNTAX:	Operand expected");
            this.isParseOK = false;
            type = TokenType.ERROR;
        }

        return type;
    }

}
