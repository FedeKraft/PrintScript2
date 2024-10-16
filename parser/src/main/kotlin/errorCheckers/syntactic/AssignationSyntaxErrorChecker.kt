package errorCheckers.syntactic

import org.example.errorCheckers.ErrorChecker
import token.Token
import token.TokenType

class AssignationSyntaxErrorChecker : ErrorChecker {
    override fun check(tokens: List<Token>): Boolean {
        checkNecessaryTokens(tokens)
        checkNecessaryTokensOrder(tokens)
        return true
    }

    private fun checkNecessaryTokens(tokens: List<Token>) {
        checkUnknownTokens(tokens)
        if (tokens.size < 3) {
            unexpectedTokenAfterIdentifier(tokens)
            missingTokenAfterAssign(tokens)
        }
        val requiredTokenTypes = listOf(TokenType.IDENTIFIER, TokenType.ASSIGN)
        val tokenTypes = getTokenTypes(tokens)


        for (tokenType in requiredTokenTypes) {
            lookForTokenInRequiredTokens(tokens, tokenType)
        }
        checkValueOfAssign(tokenTypes, tokens)
    }

    private fun checkValueOfAssign(
        tokenTypes: List<TokenType>,
        tokens: List<Token>
    ) {
        val valueTokenType = tokenTypes.last()
        if (valueTokenType != TokenType.STRING &&
            valueTokenType != TokenType.NUMBER &&
            valueTokenType != TokenType.IDENTIFIER &&
            valueTokenType != TokenType.BOOLEAN &&
            valueTokenType != TokenType.READ_ENV &&
            valueTokenType != TokenType.READ_INPUT
        ){
            throw RuntimeException(
                "Missing value token in variable " +
                        "assignment line: ${tokens.last().line}, column: ${tokens.last().column}",
            )
        }
    }

    private fun lookForTokenInRequiredTokens(tokens: List<Token>, tokenType: TokenType) {
        val token = tokens.find { it.type == tokenType }
        if (token == null) {
            throw RuntimeException(
                "Missing $tokenType token in variable assignmen" +
                        " line: ${tokens.last().line}, column: ${tokens.last().column}",
            )
        }
    }

    private fun getTokenTypes(tokens: List<Token>) = tokens.map { it.type }

    private fun checkUnknownTokens(tokens: List<Token>) {
        val unknownToken = tokens.find { it.type == TokenType.UNKNOWN }
        if (unknownToken != null) {
            throw RuntimeException(
                "Unknown token in assignment, line: ${unknownToken.line}, column: ${unknownToken.column}",
            )
        }
    }

    private fun unexpectedTokenAfterIdentifier(tokens: List<Token>) {
        if (tokens[0].type == TokenType.IDENTIFIER && tokens[1].type != TokenType.ASSIGN) {
            throw RuntimeException(
                "Missing ASSIGN token line: ${tokens.last().line}, column: ${tokens.last().column}",
            )
        }
    }

    private fun missingTokenAfterAssign(tokens: List<Token>) {
        if (tokens[0].type == TokenType.IDENTIFIER && tokens[1].type == TokenType.ASSIGN) {
            throw RuntimeException(
                "Missing value token after = in line: ${tokens[1].line}, column: ${tokens[1].column}",
            )
        }
    }

    private fun checkNecessaryTokensOrder(tokens: List<Token>) {
        val expectedOrder = listOf(TokenType.IDENTIFIER, TokenType.ASSIGN)
        for ((index, expectedTokenType) in expectedOrder.withIndex()) {
            if (tokens[index].type != expectedTokenType) {
                throw RuntimeException(
                    "Invalid token order in variable assignment" +
                        " line: ${tokens.last().line}, column: ${tokens.last().column}",
                )
            }
        }
    }

}
