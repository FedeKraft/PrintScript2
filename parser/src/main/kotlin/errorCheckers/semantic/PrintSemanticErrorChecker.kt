package org.example.errorCheckers.semantic

import Token
import TokenType
import org.example.errorCheckers.ErrorChecker

class PrintSemanticErrorChecker : ErrorChecker {
    override fun check(tokens: List<Token>): Boolean {
        val args = getArguments(tokens)
        if (args.size > 1) {
            checkArgs(args)
        }
        return true
    }
    private fun checkArgs(args: List<Token>) {
    }
    private fun getArguments(tokens: List<Token>): List<Token> {
        val leftParenthesis = tokens.indexOf(tokens.find { it.type == TokenType.LEFT_PARENTHESIS })
        val rightParenthesis = tokens.indexOf(tokens.find { it.type == TokenType.RIGHT_PARENTHESIS })
        val args = tokens.subList(leftParenthesis + 1, rightParenthesis - 1)
        return args
    }
}
