package patterns

object TokenPatterns {
    // KEYWORDS
    val LET = Regex("\\blet\\b")
    val PRINT = Regex("\\bprint\\b")
    val IF = Regex("\\bif\\b")
    val ELSE = Regex("\\belse\\b")
    val CONST = Regex("\\bconst\\b")

    // VAL TYPES
    val STRING_TYPE = Regex("\\bString\\b")
    val NUMBER_TYPE = Regex("\\bNumber\\b")
    val BOOLEAN_TYPE = Regex("\\bboolean\\b")
    val IDENTIFIER = Regex("\\b[a-zA-Z_][a-zA-Z0-9_]*")

    // LITERALS
    val NUMBER = Regex("\\b\\d+(\\.\\d+)?\\b")
    val STRING = Regex("\"([^\"]*)\"|\'([^\']*)\'")  // Captura literales entre comillas dobles o simples
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
