package command

import PrattParser
import ast.BooleanLiteralNode
import ast.ConstDeclarationNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.NullValueNode
import ast.NumberLiteralNode
import ast.ReadEnvNode
import ast.ReadInputNode
import ast.StatementNode
import ast.StringLiteralNode
import ast.VariableDeclarationNode
import token.Token
import token.TokenType
import token.TokenValue

class ConstDeclarationParser : Parser {
    override fun parse(tokens: List<Token>): StatementNode {
        val identifierNode = parseIdentifier(tokens[1])
        val tokenType = tokens[3].type
        if (tokens.size < 5) {
            return VariableDeclarationNode(
                identifierNode,
                tokenType,
                NullValueNode(0, 0),
                tokens[0].line,
                tokens[0].column,
            )
        }
        val args = tokens.subList(5, tokens.size)
        return if (args.size > 1) {
            handleMultipleArgs(identifierNode, args, tokens)
        } else {
            val expressionNode = parseExpressionNode(tokens[5])
            ConstDeclarationNode(identifierNode, expressionNode, identifierNode.line, identifierNode.column)
        }
    }

    // Parses identifier token into an IdentifierNode
    private fun parseIdentifier(token: Token): IdentifierNode =
        IdentifierNode(
            token.value.toString(),
            token.line,
            token.column,
        )

    // Handles cases where args size is greater than 1
    private fun handleMultipleArgs(
        identifierNode: IdentifierNode,
        args: List<Token>,
        tokens: List<Token>,
    ): ConstDeclarationNode =
        if (args[0].type != TokenType.READ_INPUT && args[0].type != TokenType.READ_ENV) {
            val newArgs = wrapInParentheses(args)
            val expressionNode = PrattParser(newArgs).parseExpression()
            ConstDeclarationNode(identifierNode, expressionNode, identifierNode.line, identifierNode.column)
        } else {
            val node = lookForReadEnvOrReadInput(args)
            ConstDeclarationNode(identifierNode, node, identifierNode.line, identifierNode.column)
        }

    // Wraps arguments in parentheses for parsing
    private fun wrapInParentheses(args: List<Token>): List<Token> =
        listOf(
            Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 0, 0),
        ) + args +
            listOf(
                Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 0, 0),
            )

    // Parses an individual expression token
    private fun parseExpressionNode(token: Token): ExpressionNode =
        when (token.type) {
            TokenType.IDENTIFIER -> IdentifierNode(parseStringValue(token), token.line, token.column)
            TokenType.STRING -> StringLiteralNode(parseStringValue(token), token.line, token.column)
            TokenType.NUMBER -> NumberLiteralNode(parseNumberValue(token), token.line, token.column)
            TokenType.BOOLEAN -> BooleanLiteralNode(parseBooleanValue(token), token.line, token.column)
            else -> throw RuntimeException("Unexpected token type in const declaration")
        }

    // Retrieves string value from a token
    private fun parseStringValue(token: Token): String =
        (token.value as? TokenValue.StringValue)?.value
            ?: throw RuntimeException("Expected a StringValue for token type ${token.type}")

    // Retrieves number value from a token
    private fun parseNumberValue(token: Token): Double =
        (token.value as? TokenValue.NumberValue)?.value
            ?: throw RuntimeException("Expected a NumberValue for token type ${token.type}")

    // Retrieves boolean value from a token
    private fun parseBooleanValue(token: Token): Boolean =
        (token.value as? TokenValue.BooleanValue)?.value
            ?: throw RuntimeException("Expected a BooleanValue for token type ${token.type}")

    // Checks if token is READ_ENV or READ_INPUT and returns corresponding node
    private fun lookForReadEnvOrReadInput(tokens: List<Token>): ExpressionNode =
        when (tokens[0].type) {
            TokenType.READ_ENV ->
                ReadEnvNode(
                    (tokens[2].value as TokenValue.StringValue).value,
                    tokens[0].line,
                    tokens[0].column,
                )
            TokenType.READ_INPUT ->
                ReadInputNode(
                    (tokens[2].value as TokenValue.StringValue).value,
                    tokens[0].line,
                    tokens[0].column,
                )
            else -> throw RuntimeException("Expected READ_ENV or READ_INPUT token")
        }
}
