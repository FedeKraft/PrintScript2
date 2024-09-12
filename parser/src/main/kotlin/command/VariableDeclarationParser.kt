package command

import PrattParser
import ast.BooleanLiteralNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.NullValueNode
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
    private var tokenType: TokenType = TokenType.STRING

    override fun parse(tokens: List<Token>): StatementNode {
        checkSyntax(tokens)
        val identifierNode = parseIdentifier(tokens[1])
        tokenType = tokens[3].type
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
            handleComplexArgs(identifierNode, args, tokens)
        } else {
            val expressionNode = parseExpression(tokens[5])
            createVariableDeclarationNode(identifierNode, expressionNode, tokens[0])
        }
    }

    // Checks the syntax using the error checker
    private fun checkSyntax(tokens: List<Token>) {
        val errorChecker = VariableDeclarationSyntaxErrorChecker()
        if (!errorChecker.check(tokens)) {
            throw RuntimeException("Syntax error in variable declaration statement")
        }
    }

    // Parses an identifier token into an IdentifierNode
    private fun parseIdentifier(token: Token): IdentifierNode = IdentifierNode(
        token.value.toString(),
        token.line,
        token.column,
    )

    // Handles complex cases with multiple arguments
    private fun handleComplexArgs(
        identifierNode: IdentifierNode,
        args: List<Token>,
        tokens: List<Token>,
    ): VariableDeclarationNode =
        if (args[0].type != TokenType.READ_INPUT && args[0].type != TokenType.READ_ENV) {
            val wrappedArgs = wrapInParentheses(args)
            val expressionNode = PrattParser(wrappedArgs).parseExpression()
            createVariableDeclarationNode(identifierNode, expressionNode, tokens[0])
        } else {
            val node = lookForReadEnvOrReadInput(args)
            createVariableDeclarationNode(identifierNode, node, tokens[0])
        }

    // Wraps arguments in parentheses for parsing
    private fun wrapInParentheses(args: List<Token>): List<Token> =
        listOf(Token(TokenType.LEFT_PARENTHESIS, TokenValue.StringValue("("), 0, 0)) +
            args +
            listOf(Token(TokenType.RIGHT_PARENTHESIS, TokenValue.StringValue(")"), 0, 0))

    // Creates a VariableDeclarationNode with given components
    private fun createVariableDeclarationNode(
        identifierNode: IdentifierNode,
        expressionNode: ExpressionNode,
        token: Token,
    ): VariableDeclarationNode = VariableDeclarationNode(
        identifierNode,
        tokenType,
        expressionNode,
        token.line,
        token.column,
    )

    // Parses an individual token into an ExpressionNode
    private fun parseExpression(token: Token): ExpressionNode =
        when (token.type) {
            TokenType.IDENTIFIER -> IdentifierNode(getStringValue(token), token.line, token.column)
            TokenType.STRING -> StringLiteralNode(getStringValue(token), token.line, token.column)
            TokenType.NUMBER -> NumberLiteralNode(getNumberValue(token), token.line, token.column)
            TokenType.BOOLEAN -> BooleanLiteralNode(getBooleanValue(token), token.line, token.column)
            else -> throw RuntimeException("Unexpected token type in variable declaration")
        }

    // Extracts string value from token
    private fun getStringValue(token: Token): String =
        (token.value as? TokenValue.StringValue)?.value
            ?: throw RuntimeException("Expected a StringValue for token type ${token.type}")

    // Extracts number value from token
    private fun getNumberValue(token: Token): Double =
        (token.value as? TokenValue.NumberValue)?.value
            ?: throw RuntimeException("Expected a NumberValue for token type ${token.type}")

    // Extracts boolean value from token
    private fun getBooleanValue(token: Token): Boolean =
        (token.value as? TokenValue.BooleanValue)?.value
            ?: throw RuntimeException("Expected a BooleanValue for token type ${token.type}")

    // Handles the specific case of ReadEnv or ReadInput tokens
    private fun lookForReadEnvOrReadInput(tokens: List<Token>): ExpressionNode =
        when (tokens[0].type) {
            TokenType.READ_ENV -> ReadEnvNode(getStringValue(tokens[2]), tokens[0].line, tokens[0].column)
            TokenType.READ_INPUT -> ReadInputNode(getStringValue(tokens[2]), tokens[0].line, tokens[0].column)
            else -> throw RuntimeException("Unexpected token type in ReadEnv or ReadInput")
        }
}
