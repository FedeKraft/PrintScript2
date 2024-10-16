package lexer

import reader.Reader
import token.Token
import token.TokenType
import token.TokenValue

class Lexer(
    private val reader: Reader,
    private val patternsMap: Map<Regex, TokenType>,
) : TokenProvider {
    private var currentLine = 1
    private var currentColumn = 1
    private var currentChar: Char? = reader.read()

    override fun hasNextToken(): Boolean = currentChar != null

    override fun nextToken(): Token {
        // Si no hay más caracteres, devolver un token nulo
        if (currentChar == null) {
            return Token(TokenType.UNKNOWN, TokenValue.NullValue, currentLine, currentColumn)
        }

        // Manejar espacios y saltos de línea
        manageWhiteSpacesAndLineBreaks()

        // Si llegamos al final después de saltar espacios
        if (currentChar == null) {
            return Token(TokenType.UNKNOWN, TokenValue.NullValue, currentLine, currentColumn)
        }

        val startLine = currentLine
        val startColumn = currentColumn
        val word = StringBuilder()

        // Manejar literales de cadena
        if (currentChar == '\"') {
            return handleStringLiteral(startLine, startColumn)
        }

        // Si es un delimitador (como un símbolo ";"), manejarlo por separado
        if (isDelimiter(currentChar!!)) {
            return makeDelimiterToken(startLine, startColumn)
        }

        // Construir el token hasta un delimitador (espacio, símbolo, etc.)
        tokenizeTillDelimiter(word)

        val value = word.toString()

        // Buscar coincidencias de patrones
        val tokenType = searchInTokenPatterns(value)

        if (tokenType != null) {
            return generateTokens(tokenType, value, startLine, startColumn)
        }

        // Si no se encuentra un patrón, es un identificador (como 'print')
        return Token(TokenType.IDENTIFIER, TokenValue.StringValue(value), startLine, startColumn)
    }

    private fun generateTokens(
        tokenType: TokenType,
        value: String,
        startLine: Int,
        startColumn: Int,
    ): Token {
        return when (tokenType) {
            TokenType.STRING ->
                Token(
                    tokenType,
                    TokenValue.StringValue(value.removeSurrounding("\"")),
                    startLine,
                    startColumn,
                )

            TokenType.NUMBER ->
                Token(
                    tokenType,
                    TokenValue.NumberValue(value.toDouble()),
                    startLine,
                    startColumn,
                )

            TokenType.BOOLEAN ->
                Token(
                    tokenType,
                    TokenValue.BooleanValue(value.toBoolean()),
                    startLine,
                    startColumn,
                )

            else -> Token(tokenType, TokenValue.StringValue(value), startLine, startColumn)
        }
    }

    private fun handleStringLiteral(
        startLine: Int,
        startColumn: Int,
    ): Token {
        val stringLiteral = StringBuilder()

        // Avanzar el primer carácter de comillas
        currentChar = reader.read()
        currentColumn++

        while (currentChar != null && currentChar != '\"') {
            if (currentChar == '\\') {
                // Manejo de caracteres escapados como \" o \t
                currentChar = reader.read()
                currentColumn++
                when (currentChar) {
                    't' -> stringLiteral.append('\t')
                    'n' -> stringLiteral.append('\n')
                    '\\' -> stringLiteral.append('\\')
                    '\"' -> stringLiteral.append('\"')
                    else -> stringLiteral.append(currentChar)
                }
            } else {
                stringLiteral.append(currentChar)
            }
            currentChar = reader.read()
            if (currentChar == '\n') {
                currentLine++
                currentColumn = 1
            } else {
                currentColumn++
            }
        }

        // Saltar la comilla de cierre
        currentChar = reader.read()
        currentColumn++

        return Token(TokenType.STRING, TokenValue.StringValue(stringLiteral.toString()), startLine, startColumn)
    }

    private fun makeDelimiterToken(
        startLine: Int,
        startColumn: Int,
    ): Token {
        val tokenType = searchInTokenPatterns(currentChar.toString())
        val delimiterChar = currentChar.toString()
        currentChar = reader.read() // Avanzar al siguiente carácter
        currentColumn++

        if (tokenType != null) {
            return Token(tokenType, TokenValue.StringValue(delimiterChar), startLine, startColumn)
        }

        return Token(TokenType.UNKNOWN, TokenValue.StringValue(delimiterChar), startLine, startColumn)
    }

    private fun searchInTokenPatterns(value: String): TokenType? =
        patternsMap.entries.find { it.key.matches(value) }?.value

    private fun tokenizeTillDelimiter(word: StringBuilder) {
        while (currentChar != null && !currentChar!!.isWhitespace() && !isDelimiter(currentChar!!)) {
            word.append(currentChar)
            currentChar = reader.read()
            currentColumn++
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
        val delimiters = ";:=+-*/(){}[]".toCharArray()
        return delimiters.contains(char)
    }
}
