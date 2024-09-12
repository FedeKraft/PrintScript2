package command

import PrattParser
import ast.BooleanLiteralNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.PrintStatementNode
import ast.ReadEnvNode
import ast.ReadInputNode
import ast.StatementNode
import ast.StringLiteralNode
import org.example.errorCheckers.syntactic.PrintSyntaxErrorChecker
import token.Token
import token.TokenType
import token.TokenValue

class PrintParser : Parser {

    override fun parse(tokens: List<Token>): StatementNode {
        // Perform syntax error checking
        checkSyntaxErrors(tokens)

        val args = tokens.subList(1, tokens.size)

        // Handle cases where args size is greater than 3
        if (args.size > 3) {
            return handleComplexArgs(tokens, args)
        }

        // Handle simpler cases with a single expression
        val expressionNode = parseSimpleExpression(tokens[2])
        return PrintStatementNode(expressionNode, tokens[0].line, tokens[0].column)
    }

    // Checks for syntax errors using PrintSyntaxErrorChecker
    private fun checkSyntaxErrors(tokens: List<Token>) {
        val errorChecker = PrintSyntaxErrorChecker()
        if (!errorChecker.check(tokens)) {
            throw RuntimeException("Syntax error in print statement")
        }
    }

    // Handles cases where the argument list is more complex
    private fun handleComplexArgs(tokens: List<Token>, args: List<Token>): PrintStatementNode {
        if (args[1].type != TokenType.READ_INPUT && args[1].type != TokenType.READ_ENV) {
            val expressionNode = PrattParser(args).parseExpression()
            return PrintStatementNode(expressionNode, tokens[0].line, tokens[0].column)
        }
        val node = lookForReadEnvOrReadInput(args)
        return PrintStatementNode(node, tokens[0].line, tokens[0].column)
    }

    // Parses simple expressions (IDENTIFIER, STRING, NUMBER, BOOLEAN)
    private fun parseSimpleExpression(token: Token): ExpressionNode {
        return when (token.type) {
            TokenType.IDENTIFIER -> IdentifierNode(parseStringValue(token), token.line, token.column)
            TokenType.STRING -> StringLiteralNode(parseStringValue(token), token.line, token.column)
            TokenType.NUMBER -> NumberLiteralNode(parseNumberValue(token), token.line, token.column)
            TokenType.BOOLEAN -> BooleanLiteralNode(parseBooleanValue(token), token.line, token.column)
            else -> throw RuntimeException("Unexpected token type in print statement")
        }
    }

    // Retrieves string value from a token
    private fun parseStringValue(token: Token): String {
        return (token.value as? TokenValue.StringValue)?.value
            ?: throw RuntimeException("Expected a StringValue for token type ${token.type}")
    }

    // Retrieves number value from a token
    private fun parseNumberValue(token: Token): Double {
        return (token.value as? TokenValue.NumberValue)?.value
            ?: throw RuntimeException("Expected a NumberValue for token type ${token.type}")
    }

    // Retrieves boolean value from a token
    private fun parseBooleanValue(token: Token): Boolean {
        return (token.value as? TokenValue.BooleanValue)?.value
            ?: throw RuntimeException("Expected a BooleanValue for token type ${token.type}")
    }

    // Handles READ_ENV or READ_INPUT tokens and returns the corresponding node
    private fun lookForReadEnvOrReadInput(tokens: List<Token>): ExpressionNode {
        return when (tokens[1].type) {
            TokenType.READ_ENV -> ReadEnvNode(
                (tokens[3].value as TokenValue.StringValue).value,
                tokens[0].line,
                tokens[0].column,
            )
            TokenType.READ_INPUT -> ReadInputNode(
                (tokens[3].value as TokenValue.StringValue).value,
                tokens[0].line,
                tokens[0].column,
            )
            else -> throw RuntimeException("Expected READ_ENV or READ_INPUT token")
        }
    }
}
