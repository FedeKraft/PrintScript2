package org.example.errorCheckers.syntactic

import org.example.errorCheckers.ErrorChecker
import token.Token
import token.TokenType

class PrintSyntaxErrorChecker : ErrorChecker {

    override fun check(tokens: List<Token>): Boolean {
        checkNecessaryTokens(tokens)
        checkNecessaryTokensOrder(tokens)
        checkArgsOrder(getArguments(tokens))
        return true
    }

    private fun checkNecessaryTokens(tokens: List<Token>) {
        if (tokens[1].type == TokenType.LEFT_PARENTHESIS && tokens[2].type == TokenType.RIGHT_PARENTHESIS) {
            throw RuntimeException("Missing args in print statement")
        }

        val unknownToken = tokens.find { it.type == TokenType.UNKNOWN }
        if (unknownToken != null) {
            throw RuntimeException(
                "Unknown token in print statement, line: ${unknownToken.line}, column: ${unknownToken.column}",
            )
        }

        val tokenTypes = tokens.map { it.type }
        val printTokenTypes = listOf(TokenType.PRINT, TokenType.LEFT_PARENTHESIS, TokenType.RIGHT_PARENTHESIS)

        for (tokenType in printTokenTypes) {
            if (tokenType !in tokenTypes) {
                throw RuntimeException("Missing token $tokenType in print statement")
            }
        }
    }

    private fun checkNecessaryTokensOrder(tokens: List<Token>) {
        val statementTokenTypes = tokens.map { it.type }
        val printTokenTypes = listOf(TokenType.PRINT, TokenType.LEFT_PARENTHESIS, TokenType.RIGHT_PARENTHESIS)
        var lastIndex = -1
        for (tokenType in printTokenTypes) {
            val currentIndex = statementTokenTypes.indexOf(tokenType)
            if (currentIndex == -1 || currentIndex < lastIndex) {
                throw RuntimeException("Unexpected token order in print statement")
            }
            lastIndex = currentIndex
        }
    }

    private fun checkArgsOrder(args: List<Token>) {
        if (args.size % 2 == 0) {
            throw RuntimeException("Invalid number of arguments in print statement")
        }
        val argsTokenTypes = args.map { it.type }
        for (i in 1 until args.size step 2) {
            if (argsTokenTypes[i] !in listOf(
                    TokenType.ARITHMETIC_OP,
                    TokenType.EQUALS,
                    TokenType.SUM,
                    TokenType.SUBTRACT,
                    TokenType.MULTIPLY,
                    TokenType.DIVIDE,
                )
            ) {
                throw RuntimeException("Invalid arithmetic operator in print statement")
            }
        }
        for (i in args.indices step 2) {
            if (argsTokenTypes[i] !in listOf(TokenType.IDENTIFIER, TokenType.NUMBER, TokenType.STRING)) {
                throw RuntimeException("Invalid argument in print statement")
            }
        }
    }

    private fun getArguments(tokens: List<Token>): List<Token> {
        val leftParenthesis = tokens.indexOfFirst { it.type == TokenType.LEFT_PARENTHESIS }
        val rightParenthesis = tokens.indexOfFirst { it.type == TokenType.RIGHT_PARENTHESIS }
        if (leftParenthesis == -1 || rightParenthesis == -1 || leftParenthesis >= rightParenthesis) {
            throw RuntimeException("Invalid parenthesis in print statement")
        }
        return tokens.subList(leftParenthesis + 1, rightParenthesis)
    }
}
