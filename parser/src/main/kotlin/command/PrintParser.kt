package command

import PrattParser
import ast.BooleanLiteralNode
import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.PrintStatementNode
import ast.StatementNode
import ast.StringLiteralNode
import org.example.errorCheckers.syntactic.PrintSyntaxErrorChecker
import token.Token
import token.TokenType
import token.TokenValue

class PrintParser : Parser {
    override fun parse(tokens: List<Token>): StatementNode {
        val errorChecker = PrintSyntaxErrorChecker()

        if (!errorChecker.check(tokens)) {
            throw RuntimeException("Syntax error in print statement")
        }
        val args = tokens.subList(1, tokens.size)

        if (args.size > 3) {
            val expressionNode = PrattParser(args).parseExpression()
            return PrintStatementNode(expressionNode, tokens[0].line, tokens[0].column)
        }

        val expressionToken = tokens[2]
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

        // Create PrintStatementNode and return
        val printNode = PrintStatementNode(expressionNode, expressionToken.line, expressionToken.column)
        return printNode
    }
}
