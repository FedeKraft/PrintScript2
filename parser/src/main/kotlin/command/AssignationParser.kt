package command

import PrattParser
import ast.AssignationNode
import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.StatementNode
import ast.StringLiteralNode
import org.example.errorCheckers.syntactic.AssignationSyntaxErrorChecker
import token.Token
import token.TokenType
import token.TokenValue

class AssignationParser : Parser {
    override fun parse(parser: List<Token>): StatementNode {
        val errorChecker = AssignationSyntaxErrorChecker()
        if (!errorChecker.check(parser)) {
            throw RuntimeException("Syntax error in assignation statement")
        }
        val identifierToken = parser[0]
        val identifierNode =
            IdentifierNode(identifierToken.value.toString())
        val args = parser.subList(2, parser.size)
        // a = "a"
        if (args.size > 1) {
            val newArgs = listOf(Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 0, 0)) + args + listOf(
                Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 0, 0),
            )
            val expressionNode = PrattParser(newArgs).parseExpression()
            return AssignationNode(identifierNode, expressionNode)
        }
        val expressionToken = parser[2]

        val expressionNode = when (expressionToken.type) {
            TokenType.IDENTIFIER -> {
                val value = when (val tokenValue = expressionToken.value) {
                    is TokenValue.StringValue -> tokenValue.value
                    else -> throw RuntimeException("Expected a StringValue for IDENTIFIER")
                }
                IdentifierNode(value)
            }
            TokenType.STRING -> {
                val value = when (val tokenValue = expressionToken.value) {
                    is TokenValue.StringValue -> tokenValue.value
                    else -> throw RuntimeException("Expected a StringValue for STRING")
                }
                StringLiteralNode(value)
            }
            TokenType.NUMBER -> {
                val value = when (val tokenValue = expressionToken.value) {
                    is TokenValue.NumberValue -> tokenValue.value
                    else -> throw RuntimeException("Expected a NumberValue for NUMBER")
                }
                NumberLiteralNode(value)
            }
            else -> throw RuntimeException("Unexpected token type in print statement")
        }
        val assignationNode =
            AssignationNode(identifierNode, expressionNode)

        return assignationNode
    }
}
