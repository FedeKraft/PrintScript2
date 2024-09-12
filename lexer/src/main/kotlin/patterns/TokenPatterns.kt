package patterns

object TokenPatterns {
    // KEYWORDS
    val READ_INPUT = Regex("\\breadInput\\b")
    val READ_ENV = Regex("\\breadEnv\\b")
    val LET = Regex("\\blet\\b")
    val PRINT = Regex("\\bprintln\\b")
    val IF = Regex("\\bif\\b")
    val ELSE = Regex("\\belse\\b")
    val CONST = Regex("\\bconst\\b")

    // VAL TYPES
    val STRING_TYPE = Regex("\\bstring\\b")
    val NUMBER_TYPE = Regex("\\bnumber\\b")
    val BOOLEAN_TYPE = Regex("\\bboolean\\b")
    val IDENTIFIER = Regex("\\b[a-zA-Z_][a-zA-Z0-9_]*")

    // LITERALS
    val NUMBER = Regex("\\b\\d+(\\.\\d+)?\\b")
    val STRING = Regex("\"([^\"]*)\"|\'([^\']*)\'")
    val BOOLEAN = Regex("\\b(true|false)\\b")

    // OPERATORS
    val SUM = Regex("\\+")
    val SUBTRACT = Regex("-")
    val MULTIPLY = Regex("\\*")
    val DIVIDE = Regex("/")

    // SYMBOLS
    val ASSIGN = Regex("=")
    val SEMICOLON = Regex(";")
    val COLON = Regex(":")
    val LEFT_PARENTHESIS = Regex("\\(")
    val RIGHT_PARENTHESIS = Regex("\\)")
    val LEFT_BRACE = Regex("\\{")
    val RIGHT_BRACE = Regex("\\}")
}
