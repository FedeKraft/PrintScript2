// TokenPatterns.kt
package token

object TokenPatterns {
    val KEYWORDS = Regex("\\b(let|println)\\b")
    val IDENTIFIER = Regex("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b")
    val NUMBER = Regex("\\b\\d+(\\.\\d+)?\\b")
    val STRING = Regex("\".*?\"|'.*?'")  // Mantiene las comillas para extracción
    val STRING_TYPE = Regex("\\bString\\b")
    val NUMBER_TYPE = Regex("\\bNumber\\b")
    val OPERATOR = Regex("[+\\-*/=]")
    val SYMBOL = Regex("[;:(){}]")
    val WHITESPACE = Regex("\\s+")
}
