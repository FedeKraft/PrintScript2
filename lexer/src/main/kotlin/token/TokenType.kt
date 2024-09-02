package token

enum class TokenType {

    // LITERAL TYPES
    NUMBER,
    STRING, // Renombrado de STRING_LITERAL a STRING para consistencia

    // KEYWORDS

    LET,
    PRINT,

    // IDENTIFIER
    IDENTIFIER,
    STRING_TYPE,
    NUMBER_TYPE,

    // OPERATORS
    ASSIGN,
    ARITHMETIC_OP,
    SUM,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    EQUALS,

    // SYMBOLS
    SEMICOLON,
    COLON,
    LEFT_PARENTHESIS,
    RIGHT_PARENTHESIS,

    // WHITESPACE

    // MISC
    UNKNOWN,
}
