package sabry.muhammed;

public class Token {

    /**
     * the tokenType identifier
     */
    private String tokenType;
    /**
     * the string that the tokenType was created from
     */
    private final String sequence;

    /**
     * Construct the tokenType with its values
     *
     * @param tokenType the tokenType identifier
     * @param sequence  the string that the tokenType was created from
     */
    Token(String tokenType, String sequence) {
        super();
        this.tokenType = tokenType;
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return sequence;
    }

    static final String RESERVED_WORD = "Reserved Word";
    static final String PLUS = "Plus";
    static final String MINUS = "Minus";
    static final String MULTIPLICATION = "Multiplication";
    static final String DIVISION = "Division";
    static final String EQUAL = "Equal";
    static final String LESS_THAN = "Less than";
    static final String OPEN_BRACKET = "Open bracket";
    static final String CLOSE_BRACKET = "Close bracket";
    static final String SEMICOLON = "Semicolon";
    static final String ASSIGN = "Assign";
    static final String NUMBER = "Number";
    static final String IDENTIFIER = "Identifier";
    static final String COMMENT = "Comment";

    String getTokenType() {
        return tokenType;
    }
}
