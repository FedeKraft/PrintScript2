import ast.ExpressionNode
import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.StringLiteralNode
import token.Token
import token.TokenType
import token.TokenValue

class PrattParser(private val tokens: List<Token>) {
    private var currentPosition = 0

    private val prefixParselets = mutableMapOf<TokenType, PrefixParselet>()
    private val infixParselets = mutableMapOf<TokenType, InfixParselet>()

    init {
        // Registro de parselets prefix para manejar números y paréntesis
        registerPrefix(
            TokenType.NUMBER,
            object : PrefixParselet {
                override fun parse(parser: PrattParser, token: Token): ExpressionNode {
                    return when (token.value) {
                        is TokenValue.NumberValue -> NumberLiteralNode(
                            (token.value as TokenValue.NumberValue).value,
                            token.line,
                            token.column,
                        )
                        else -> throw RuntimeException("Expected a NumberValue for NUMBER")
                    }
                }
            },
        )
        registerPrefix(
            TokenType.IDENTIFIER,
            object : PrefixParselet {
                override fun parse(parser: PrattParser, token: Token): ExpressionNode {
                    return when (token.value) {
                        is TokenValue.StringValue -> IdentifierNode(
                            (token.value as TokenValue.StringValue).value,
                            token.line,
                            token.column,
                        )
                        else -> throw RuntimeException("Expected a StringValue for IDENTIFIER")
                    }
                }
            },
        )
        registerPrefix(
            TokenType.STRING,
            object : PrefixParselet {
                override fun parse(parser: PrattParser, token: Token): ExpressionNode {
                    return when (token.value) {
                        is TokenValue.StringValue -> StringLiteralNode(
                            (token.value as TokenValue.StringValue).value,
                            token.line,
                            token.column,
                        )
                        else -> throw RuntimeException("Expected a StringValue for STRING")
                    }
                }
            },
        )

        registerPrefix(
            TokenType.LEFT_PARENTHESIS,
            object : PrefixParselet {
                override fun parse(parser: PrattParser, token: Token): ExpressionNode {
                    val expression = parser.parseExpression()
                    parser.consume(TokenType.RIGHT_PARENTHESIS)
                    return expression
                }
            },
        )
        registerInfix(TokenType.SUM, BinaryOperatorParselet(Precedence.SUM))
        registerInfix(TokenType.SUBTRACT, BinaryOperatorParselet(Precedence.SUM))
        registerInfix(TokenType.MULTIPLY, BinaryOperatorParselet(Precedence.PRODUCT))
        registerInfix(TokenType.DIVIDE, BinaryOperatorParselet(Precedence.PRODUCT))
    }

    fun registerPrefix(tokenType: TokenType, parselet: PrefixParselet) {
        prefixParselets[tokenType] = parselet
    }

    fun registerInfix(tokenType: TokenType, parselet: InfixParselet) {
        infixParselets[tokenType] = parselet
    }

    fun parseExpression(precedence: Int = 0): ExpressionNode {
        val token = consume()
        val prefix = prefixParselets[token.type] ?: throw IllegalArgumentException(
            "No prefix parselet for ${token.type}",
        )
        var left = prefix.parse(this, token)

        while (currentPosition < tokens.size && precedence < getPrecedence()) {
            val infixToken = consume()
            val infix = infixParselets[infixToken.type] ?: throw IllegalArgumentException(
                "No infix parselet for ${infixToken.type}",
            )
            left = infix.parse(this, left, infixToken)
        }

        return left
    }

    private fun consume(): Token {
        if (currentPosition >= tokens.size) {
            throw IllegalArgumentException("No more tokens available for consumption")
        }
        return tokens[currentPosition++]
    }

    private fun consume(expected: TokenType): Token {
        val token = consume()
        if (token.type != expected) {
            throw IllegalArgumentException("Expected token of type $expected but got ${token.type}")
        }
        return token
    }

    private fun getPrecedence(): Int {
        val token = peek()
        return infixParselets[token.type]?.precedence() ?: 0
    }

    private fun peek(): Token {
        if (currentPosition >= tokens.size) {
            throw IllegalArgumentException("No more tokens available for peeking")
        }
        return tokens[currentPosition]
    }
}
