import ast.BinaryExpressionNode
import ast.ExpressionNode
import token.Token
import token.TokenType

class BinaryOperatorParselet(private val precedence: Int) : InfixParselet {
    override fun parse(parser: PrattParser, left: ExpressionNode, token: Token): ExpressionNode {
        val right = parser.parseExpression(precedence)
        return when (token.type) {
            TokenType.SUM -> BinaryExpressionNode(left, TokenType.SUM, right)
            TokenType.SUBTRACT -> BinaryExpressionNode(left, TokenType.SUBTRACT, right)
            TokenType.MULTIPLY -> BinaryExpressionNode(left, TokenType.MULTIPLY, right)
            TokenType.DIVIDE -> BinaryExpressionNode(left, TokenType.DIVIDE, right)
            else -> throw RuntimeException("Unexpected token type in binary operator parselet")
        }
    }

    override fun precedence(): Int = precedence
}

object Precedence {
    const val SUM = 1
    const val PRODUCT = 2
}
