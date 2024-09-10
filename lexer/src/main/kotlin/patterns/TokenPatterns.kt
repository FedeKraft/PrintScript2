// TokenPatterns.kt
package patterns

object TokenPatterns {
    // KEYWORDS
    val LET = Regex("\\blet\\b")
    val PRINT = Regex("\\bprint\\b")
    val IF = Regex("\\bif\\b")           // Nuevo patrón para 'if'
    val ELSE = Regex("\\belse\\b")       // Nuevo patrón para 'else'
    val CONST = Regex("\\bconst\\b")     // Nuevo patrón para 'const'

    // VAL TYPES
    val STRING_TYPE = Regex("\\bString\\b")
    val NUMBER_TYPE = Regex("\\bNumber\\b")
    val BOOLEAN_TYPE = Regex("\\bboolean\\b")  // Tipo booleano
    val IDENTIFIER = Regex("\\b[a-zA-Z_][a-zA-Z0-9_]*")

    // LITERALS
    val NUMBER = Regex("\\b\\d+(\\.\\d+)?\\b")
    val STRING = Regex("\".*?\"|'.*?'")
    val BOOLEAN = Regex("\\b(true|false)\\b")  // Nuevo patrón para literales booleanos

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
    val LEFT_BRACE = Regex("\\{")        // Nuevo patrón para '{'
    val RIGHT_BRACE = Regex("\\}")       // Nuevo patrón para '}'
}
