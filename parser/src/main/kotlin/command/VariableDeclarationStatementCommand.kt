package command

import ASTNode
import IdentifierNode
import NumberLiteralNode
import PrattParser
import StringLiteralNode
import Token
import VariableDeclarationNode
import org.example.errorCheckers.syntactic.VariableDeclarationSyntaxErrorChecker

class VariableDeclarationStatementCommand : ParseCommand {

    override fun execute(tokens: List<Token>): ASTNode {
        val errorChecker = VariableDeclarationSyntaxErrorChecker()
        if (!errorChecker.check(tokens)) {
            throw RuntimeException("Syntax error in variable declaration statement")
        }

        val identifierToken = tokens[1]
        val identifierNode =
            IdentifierNode(identifierToken.value.toString(), identifierToken.line, identifierToken.column)

        val valueToken = tokens[5]
        val args = tokens.subList(5, tokens.size)
        // let a : String = "a"
        if (args.size > 1) {
            val newArgs = listOf(Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 0, 0)) + args + listOf(
                Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 0, 0),
            )
            val expressionNode = PrattParser(newArgs).parseExpression()
            return VariableDeclarationNode(identifierNode, expressionNode, tokens[0].line, tokens[0].column)
        }
        val expressionToken = tokens[5]

        val expressionNode = when (expressionToken.type) {
            TokenType.IDENTIFIER -> {
                val value = when (val tokenValue = expressionToken.value) {
                    is TokenValue.StringValue -> tokenValue.value
                    else -> throw RuntimeException("Expected a StringValue for IDENTIFIER")
                }
                IdentifierNode(value, expressionToken.line, expressionToken.column)
            }
            TokenType.STRING -> {
                val value = when (val tokenValue = expressionToken.value) {
                    is TokenValue.StringValue -> tokenValue.value
                    else -> throw RuntimeException("Expected a StringValue for STRING")
                }
                StringLiteralNode(value, expressionToken.line, expressionToken.column)
            }
            TokenType.NUMBER -> {
                val value = when (val tokenValue = expressionToken.value) {
                    is TokenValue.NumberValue -> tokenValue.value
                    else -> throw RuntimeException("Expected a NumberValue for NUMBER")
                }
                NumberLiteralNode(value, expressionToken.line, expressionToken.column)
            }
            else -> throw RuntimeException("Unexpected token type in print statement")
        }

        val variableNode =
            VariableDeclarationNode(identifierNode, expressionNode, identifierToken.line, identifierToken.column)

        return variableNode
    }
}
