package command

import PrattParser
import ast.BooleanLiteralNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.ReadEnvNode
import ast.ReadInputNode
import ast.StatementNode
import ast.StringLiteralNode
import ast.VariableDeclarationNode
import errorCheckers.syntactic.VariableDeclarationSyntaxErrorChecker
import token.Token
import token.TokenType
import token.TokenValue

class VariableDeclarationParser : Parser {

    override fun parse(tokens: List<Token>): StatementNode {
        val errorChecker = VariableDeclarationSyntaxErrorChecker()
        if (!errorChecker.check(tokens)) {
            throw RuntimeException("Syntax error in variable declaration statement")
        }
        val identifierToken = tokens[1]
        val identifierNode =
            IdentifierNode(identifierToken.value.toString(), identifierToken.line, identifierToken.column)

        val args = tokens.subList(5, tokens.size)
        // let a : String = "a"
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
                    return VariableDeclarationNode(identifierNode, expressionNode, tokens[0].line, tokens[0].column)
                }
            }
            val node = lookForReadEnvOrReadInput(args)
            return VariableDeclarationNode(identifierNode, node, tokens[0].line, tokens[0].column)
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
            else -> throw RuntimeException("Unexpected token type in print statement")
        }

        val variableNode =
            VariableDeclarationNode(identifierNode, expressionNode, identifierToken.line, identifierToken.column)

        return variableNode
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
