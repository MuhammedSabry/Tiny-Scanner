package sabry.muhammed;

import jdk.nashorn.internal.runtime.ParserException;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class for reading an input string and separating it into tokens that can be
 * fed into Parser.
 * <p>
 * The user can add regular expressions that will be matched against the front
 * of the string. Regular expressions should not contain beginning-of-string or
 * end-of-string anchors or any capturing groups as these will be added by the
 * tokenizer itslef.
 */
class Tokenizer {

    /**
     * a tokenizer that can handle mathematical expressions
     */
    private static Tokenizer expressionTokenizer = null;
    /**
     * a list of TokenInfo objects
     * <p>
     * Each tokenType type corresponds to one entry in the list
     */
    private LinkedList<TokenInfo> tokensInfo;

    private Tokenizer() {
        super();
        tokensInfo = new LinkedList<>();
    }

    /**
     * Add a regular expression and a tokenType id to the internal list of recognized tokens
     *
     * @param regex     the regular expression to match against
     * @param tokenType the tokenType type that the regular expression is linked to
     */
    private void add(String regex, String tokenType) {
        tokensInfo.add(new TokenInfo(Pattern.compile("^(" + regex + ")"), tokenType));
    }

    /**
     * A static method that returns a tokenizer for mathematical expressions
     *
     * @return a tokenizer that can handle mathematical expressions
     */
    static Tokenizer getExpressionTokenizer() {
        if (expressionTokenizer == null)
            expressionTokenizer = createExpressionTokenizer();
        return expressionTokenizer;
    }

    /**
     * A static method that actually creates a tokenizer for mathematical expressions
     *
     * @return a tokenizer that can handle mathematical expressions
     */
    private static Tokenizer createExpressionTokenizer() {
        Tokenizer tokenizer = new Tokenizer();

        String reservedWords = "(if|then|else|end|repeat|until|read|write)";
        tokenizer.add("(?:^|\\W)(?i)" + reservedWords + "(?-i)(?:$|\\W)", Token.RESERVED_WORD);
        tokenizer.add("(\\{(.*)\\})",Token.COMMENT);
        tokenizer.add("\\*", Token.MULTIPLICATION);
        tokenizer.add("\\+", Token.PLUS);
        tokenizer.add("\\-", Token.MINUS);
        tokenizer.add("\\/", Token.DIVISION);
        tokenizer.add("<", Token.LESS_THAN);
        tokenizer.add(";", Token.SEMICOLON);
        tokenizer.add(":=", Token.ASSIGN);
        tokenizer.add("\\(", Token.OPEN_BRACKET);
        tokenizer.add("\\)", Token.CLOSE_BRACKET);
        tokenizer.add("[A-Za-z][A-Za-z0-9]*", Token.IDENTIFIER);
        tokenizer.add("(?:\\d+\\.?|\\.\\d)\\d*(?:[Ee][-+]?\\d+)?", Token.NUMBER);
        tokenizer.add("\\=", Token.EQUAL);

        return tokenizer;
    }

    /**
     * Tokenize an input string.
     * <p>
     * The reult of tokenizing can be accessed via getTokens
     *
     * @param str the string to tokenize
     */
    List<Token> tokenize(String str) {
        List<Token> tokens = new LinkedList<>();
        String s = str.trim();
        while (!s.equals("")) {
            boolean match = false;
            for (TokenInfo info : tokensInfo) {
                Matcher m = info.regex.matcher(s);
                if (m.find()) {
                    match = true;
                    String tok = m.group().trim();
                    s = m.replaceFirst("").trim();
                    if (info.tokenType.equals(Token.COMMENT))
                        tok = tok.substring(1, tok.length() - 1).trim();
                    tokens.add(new Token(info.tokenType, tok));
                    break;
                }
            }
            if (!match)
                throw new ParserException("Unexpected character in input: " + s);
        }
        return tokens;
    }

    /**
     * Internal class holding the information about a tokenType type.
     */
    private class TokenInfo {

        /**
         * the tokenType id that the regular expression is linked to
         */
        final String tokenType;
        /**
         * the regular expression to match against
         */
        private final Pattern regex;

        /**
         * Construct TokenInfo with its values
         */
        private TokenInfo(Pattern regex, String tokenType) {
            this.regex = regex;
            this.tokenType = tokenType;
        }
    }
}
