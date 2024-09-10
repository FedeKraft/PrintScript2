package command

import PrattParser
import ast.IdentifierNode
import ast.PrintStatementNode
import ast.StatementNode
import ast.StringLiteralNode
import org.example.errorCheckers.syntactic.PrintSyntaxErrorChecker
import token.Token
import token.TokenType
import token.TokenValue

class PrintParser : Parser {
    override fun parse(parser: List<Token>): StatementNode {
        val errorChecker = PrintSyntaxErrorChecker()

        if (!errorChecker.check(parser)) {
            throw RuntimeException("Syntax error in print statement")
        }
        val args = parser.subList(1, parser.size)

        if (args.size > 3) {
            val expressionNode = PrattParser(args).parseExpression()
            return PrintStatementNode(expressionNode)
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
            else -> throw RuntimeException("Unexpected token type in print statement")
        }

        // Create PrintStatementNode and return
        val printNode = PrintStatementNode(expressionNode)
        return printNode
    }
}
