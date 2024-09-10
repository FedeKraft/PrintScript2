package lexer

import reader.Reader
import token.*

class Lexer(private val reader: Reader, private val patternsMap: Map<Regex, TokenType>): TokenProvider {
    private var currentLine = 1
    private var currentColumn = 1
    private var currentChar: Char? = reader.read() // Leer el primer carácter

    override fun hasNextToken(): Boolean {
        return currentChar != null
    }

    override fun nextToken(): Token {
        if (currentChar == null) {
            return Token(TokenType.UNKNOWN, TokenValue.NullValue, currentLine, currentColumn)
        }

        // Manejar espacios y saltos de línea
        manageWhiteSpacesAndLineBreaks()

        // Si llegamos al final después de saltar espacios
        if (currentChar == null) {
            return Token(TokenType.UNKNOWN, TokenValue.NullValue, currentLine, currentColumn)
        }

        val word = StringBuilder()

        // Construir el token hasta un delimitador (espacio, símbolo, etc.)
        tokenizeTillDelimiter(word)

        val value = word.toString()

        // Buscar coincidencias de patrones
        val tokenType = searchInTokenPatterns(value)

        if (tokenType != null) {
            return when(tokenType) {
                TokenType.STRING -> Token(tokenType, TokenValue.StringValue(value), currentLine, currentColumn)
                TokenType.NUMBER -> Token(tokenType, TokenValue.NumberValue(value.toDouble()), currentLine, currentColumn)
                else -> Token(tokenType, TokenValue.StringValue(value), currentLine, currentColumn)
            }
        }

        // Si es un delimitador (como un símbolo ";"), manejarlo por separado
        if (isDelimiter(currentChar!!)) {
            return makeDelimiterToken()
        }
        return Token(TokenType.UNKNOWN, TokenValue.StringValue(value), currentLine, currentColumn)
    }

    private fun makeDelimiterToken(): Token {
        val tokenType = searchInTokenPatterns(currentChar.toString())
        if(tokenType == null) {
            currentChar = reader.read() // Avanzar al siguiente carácter
            return Token(TokenType.UNKNOWN, TokenValue.StringValue(currentChar.toString()), currentLine, currentColumn)
        }
        val delimiterToken = Token(tokenType, TokenValue.StringValue(currentChar.toString()), currentLine, currentColumn)
        currentChar = reader.read() // Avanzar al siguiente carácter
        return delimiterToken
    }

    private fun searchInTokenPatterns(value: String) = patternsMap.entries.find { it.key.matches(value) }?.value

    private fun tokenizeTillDelimiter(word: StringBuilder) {
        while (currentChar != null && !currentChar!!.isWhitespace() && !isDelimiter(currentChar!!)) {
            word.append(currentChar)
            currentChar = reader.read()
            if (currentChar == '\n') {
                currentLine++
                currentColumn = 1
            } else {
                currentColumn++
            }
        }
    }

    private fun manageWhiteSpacesAndLineBreaks() {
        while (currentChar != null && currentChar!!.isWhitespace()) {
            if (currentChar == '\n') {
                currentLine++
                currentColumn = 1
            } else {
                currentColumn++
            }
            currentChar = reader.read()
        }
    }

    private fun isDelimiter(char: Char): Boolean {
        val delimiters = ";:=+-*/(){}".toCharArray()
        return delimiters.contains(char)
    }


}
