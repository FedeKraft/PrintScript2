package errorCheckers.syntactic

import org.example.errorCheckers.ErrorChecker
import token.Token
import token.TokenType
import kotlin.math.abs

class PrintSyntaxErrorChecker : ErrorChecker {
    override fun check(tokens: List<Token>): Boolean {
        checkNecessaryTokens(tokens)
        checkNecessaryTokensOrder(tokens)
        checkArgsOrder(getArguments(tokens))
        return true
    }

    private fun checkNecessaryTokens(tokens: List<Token>) {

        lookForUnknownTokens(tokens)

        lookForRequiredTokens(tokens)
        lookForMissingTokens(tokens)
        lookForMissingArgs(tokens)
    }

    private fun lookForRequiredTokens(tokens: List<Token>) {
        val requiredTokenTypes = listOf(TokenType.PRINT, TokenType.LEFT_PARENTHESIS, TokenType.RIGHT_PARENTHESIS)

        for (tokenType in requiredTokenTypes) {
            val token = tokens.find { it.type == tokenType }
            if (token == null) {
                throw RuntimeException(
                    "Print statement is missing a $tokenType token line:" +
                            " ${tokens.first().line}, column: ${tokens.first().column}",
                )
            }
        }
    }

    private fun lookForMissingTokens(tokens: List<Token>) {
        if (tokens.size < 3) {
            throw RuntimeException(
                "Missing tokens in print statement line: ${tokens[0].line}"
            )
        }
    }
    private fun lookForMissingArgs(tokens: List<Token>) {
    val leftParenthesis = tokens.indexOfFirst { it.type == TokenType.LEFT_PARENTHESIS }
    val rightParenthesis = tokens.indexOfFirst { it.type == TokenType.RIGHT_PARENTHESIS }


    if (rightParenthesis != -1 && leftParenthesis != -1 && abs(rightParenthesis - leftParenthesis) == 1) {
        throw RuntimeException(
            "Missing arguments in print statement line: ${tokens[leftParenthesis].line}, column: ${tokens[leftParenthesis].column}"
        )
    }
}
    private fun lookForUnknownTokens(tokens: List<Token>) {
        val unknownToken = tokens.find { it.type == TokenType.UNKNOWN }
        if (unknownToken != null) {
            throw RuntimeException(
                "Unknown token in print statement line: ${unknownToken.line}, column: ${unknownToken.column}",
            )
        }
    }

    private fun checkNecessaryTokensOrder(tokens: List<Token>) {
        val expectedOrder = listOf(TokenType.PRINT, TokenType.LEFT_PARENTHESIS, TokenType.RIGHT_PARENTHESIS)
        val statementTokenTypes = getStatementTokenTypes(tokens)
        for ((index, expectedTokenType) in expectedOrder.withIndex()) {
            if (statementTokenTypes[index] != expectedTokenType) {
                throw RuntimeException(
                    "Invalid token order in print statement" +
                            " line: ${tokens[index].line}, column: ${tokens[index].column}",
                )
            }
        }
    }

    private fun checkArgsOrder(args: List<Token>) {
        checkAmountOfArgs(args)
        val argsTokenTypes = getStatementTokenTypes(args)
        checkForValidOperators(args, argsTokenTypes)
        checkForValidOperands(args, argsTokenTypes)
    }

    private fun checkForValidOperands(
        args: List<Token>,
        argsTokenTypes: List<TokenType>
    ) {
        for (i in args.indices step 2) {
            if (argsTokenTypes[i] !in
                listOf(
                    TokenType.IDENTIFIER,
                    TokenType.NUMBER,
                    TokenType.STRING,
                    TokenType.BOOLEAN,
                    TokenType.READ_ENV,
                    TokenType.READ_INPUT,
                )
            ) {
                throw RuntimeException(
                    "Invalid argument in print statement line: ${args[i].line}, column: ${args[i].column}",
                )
            }
        }
    }

    private fun checkForValidOperators(
        args: List<Token>,
        argsTokenTypes: List<TokenType>
    ) {
        for (i in 1 until args.size step 2) {
            if (argsTokenTypes[i] !in
                listOf(
                    TokenType.ARITHMETIC_OP,
                    TokenType.EQUALS,
                    TokenType.SUM,
                    TokenType.SUBTRACT,
                    TokenType.MULTIPLY,
                    TokenType.DIVIDE,
                    TokenType.READ_ENV,
                    TokenType.READ_INPUT,
                    TokenType.STRING,
                    TokenType.LEFT_PARENTHESIS,
                    TokenType.RIGHT_PARENTHESIS,
                )
            ) {
                throw RuntimeException(
                    "Invalid arithmetic operator in print statement" +
                            " line: ${args[i].line}, column: ${args[i].column}",
                )
            }
        }
    }

    private fun checkAmountOfArgs(args: List<Token>) {
        if (args.size % 2 == 0) {
            throw RuntimeException(
                "Invalid number of arguments in print statement " +
                        "line: ${args.last().line}, column: ${args.last().column - 1}",
            )
        }
    }

    private fun getStatementTokenTypes(tokens: List<Token>) = tokens.map { it.type }

    private fun getArguments(tokens: List<Token>): List<Token> {
        val leftParenthesis = tokens.indexOfFirst { it.type == TokenType.LEFT_PARENTHESIS }
        val rightParenthesis = tokens.indexOfFirst { it.type == TokenType.RIGHT_PARENTHESIS }
        if (leftParenthesis == -1 || rightParenthesis == -1 || leftParenthesis >= rightParenthesis) {
            throw RuntimeException(
                "Invalid parenthesis in print statement" +
                    "line: ${tokens.last().line}, column: ${tokens.last().column}",
            )
        }
        return tokens.subList(leftParenthesis + 1, rightParenthesis)
    }
}
