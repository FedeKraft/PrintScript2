package lexer

import token.*

class Lexer(val code: String): TokenProvider {
    private var currentIndex = 0
    private var currentLine = 1
    private var currentColumn = 1
    private val patternsMap = mapOf(
        TokenPatterns.NUMBER to TokenType.NUMBER,
        TokenPatterns.STRING to TokenType.STRING,
        TokenPatterns.LET to TokenType.LET,
        TokenPatterns.PRINT to TokenType.PRINT,
        TokenPatterns.STRING_TYPE to TokenType.STRING_TYPE,
        TokenPatterns.NUMBER_TYPE to TokenType.NUMBER_TYPE,
        TokenPatterns.IDENTIFIER to TokenType.IDENTIFIER,
        TokenPatterns.ASSIGN to TokenType.ASSIGN,
        TokenPatterns.SUM to TokenType.SUM,
        TokenPatterns.SUBTRACT to TokenType.SUBTRACT,
        TokenPatterns.MULTIPLY to TokenType.MULTIPLY,
        TokenPatterns.DIVIDE to TokenType.DIVIDE,
        TokenPatterns.SEMICOLON to TokenType.SEMICOLON,
        TokenPatterns.COLON to TokenType.COLON,
        TokenPatterns.LEFT_PARENTHESIS to TokenType.LEFT_PARENTHESIS,
        TokenPatterns.RIGHT_PARENTHESIS to TokenType.RIGHT_PARENTHESIS
    )

    override fun hasNextToken(): Boolean {
        skipWhitespace()
        return currentIndex < code.length
    }

    fun nextToken(): Token {
        if (!hasNextToken()) {
            return Token(TokenType.UNKNOWN, TokenValue.StringValue("unknown"), currentLine, currentColumn)
        }

        val startingLine = currentLine
        val startingColumn = currentColumn
        val remainingCode = code.substring(currentIndex)

        for ((pattern, tokenType) in patternsMap) {
            val matchResult = pattern.find(remainingCode)
            if (matchResult != null && matchResult.range.first == 0) { // Coincidencia al inicio
                val matchedText = matchResult.value
                updatePosition(matchedText)
                currentIndex += matchedText.length
                return Token(tokenType, getTokenValue(matchedText, tokenType), startingLine, startingColumn)
            }
        }

        // Si no se encuentra ningún token, avanzar en uno e indicar token desconocido
        updatePosition(code[currentIndex].toString())
        currentIndex++
        return Token(TokenType.UNKNOWN, TokenValue.StringValue("unknown"), startingLine, startingColumn)
    }

    private fun skipWhitespace() {
        while (currentIndex < code.length && code[currentIndex].isWhitespace()) {
            updatePosition(code[currentIndex].toString())
            currentIndex++
        }
    }

    private fun updatePosition(text: String) {
        for (char in text) {
            if (char == '\n') {
                currentLine++
                currentColumn = 1
            } else {
                currentColumn++
            }
        }
    }


    private fun getTokenValue(word: String, tokenType: TokenType): TokenValue {
        return when (tokenType) {
            TokenType.NUMBER -> TokenValue.NumberValue(word.toDouble())
            TokenType.STRING -> TokenValue.StringValue(word)
            TokenType.LET -> TokenValue.StringValue("let")
            TokenType.PRINT -> TokenValue.StringValue("print")
            TokenType.STRING_TYPE -> TokenValue.StringValue("String")
            TokenType.NUMBER_TYPE -> TokenValue.StringValue("Number")
            TokenType.IDENTIFIER -> TokenValue.StringValue(word)
            TokenType.ASSIGN -> TokenValue.StringValue("=")
            TokenType.SUM -> TokenValue.StringValue("+")
            TokenType.SUBTRACT -> TokenValue.StringValue("-")
            TokenType.MULTIPLY -> TokenValue.StringValue("*")
            TokenType.DIVIDE -> TokenValue.StringValue("/")
            TokenType.SEMICOLON -> TokenValue.StringValue(";")
            TokenType.COLON -> TokenValue.StringValue(":")
            TokenType.LEFT_PARENTHESIS -> TokenValue.StringValue("(")
            TokenType.RIGHT_PARENTHESIS -> TokenValue.StringValue(")")
            else -> TokenValue.StringValue("unknown")
        }
    }

    override fun getNextToken(): Token {
        return nextToken()
    }

}