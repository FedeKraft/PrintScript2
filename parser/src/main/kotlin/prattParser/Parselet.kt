import ast.ExpressionNode
import token.Token

interface PrefixParselet {
    fun parse(parser: PrattParser, token: Token): ExpressionNode
}

interface InfixParselet {
    fun parse(parser: PrattParser, left: ExpressionNode, token: Token): ExpressionNode
    fun precedence(): Int
}
