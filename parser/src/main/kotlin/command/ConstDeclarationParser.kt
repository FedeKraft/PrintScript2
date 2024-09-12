package command

import PrattParser
import ast.BooleanLiteralNode
import ast.ConstDeclarationNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.ReadEnvNode
import ast.ReadInputNode
import ast.StatementNode
import ast.StringLiteralNode
import token.Token
import token.TokenType
import token.TokenValue

class ConstDeclarationParser : Parser {
    override fun parse(tokens: List<Token>): StatementNode {
        val identifierToken = tokens[1]
        val identifierNode =
            IdentifierNode(identifierToken.value.toString(), identifierToken.line, identifierToken.column)

        val args = tokens.subList(5, tokens.size)
        // const a : String = "a"
        if (args.size > 1) {
            if (args[0].type != TokenType.READ_INPUT) {
                if (args[0].type != TokenType.READ_ENV) {
                    val newArgs = listOf(
                        Token(
                            TokenType.LEFT_PARENTHESIS,
                            TokenValue.StringValue("("),
                            0,
                            0,
                        ),
                    ) + args + listOf(
                        Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 0, 0),
                    )
                    val expressionNode = PrattParser(newArgs).parseExpression()
                    return ConstDeclarationNode(identifierNode, expressionNode, tokens[0].line, tokens[0].column)
                }
            }
            val node = lookForReadEnvOrReadInput(args)
            return ConstDeclarationNode(identifierNode, node, tokens[0].line, tokens[0].column)
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
            TokenType.BOOLEAN -> {
                val value = when (val tokenValue = expressionToken.value) {
                    is TokenValue.BooleanValue -> tokenValue.value
                    else -> throw RuntimeException("Expected a BooleanValue for BOOLEAN")
                }
                BooleanLiteralNode(value, expressionToken.line, expressionToken.column)
            }
            else -> throw RuntimeException("Unexpected token type in const declaration")
        }

        return ConstDeclarationNode(identifierNode, expressionNode, identifierToken.line, identifierToken.column)
    }

    private fun lookForReadEnvOrReadInput(tokens: List<Token>): ExpressionNode {
        if (tokens[0].type == TokenType.READ_ENV) {
            val value = (tokens[2].value as TokenValue.StringValue).value
            return ReadEnvNode(value, tokens[0].line, tokens[0].column)
        }
        if (tokens[0].type == TokenType.READ_INPUT) {
            val value = (tokens[2].value as TokenValue.StringValue).value
            return ReadInputNode(value, tokens[0].line, tokens[0].column)
        }
        return null!!
    }
}
