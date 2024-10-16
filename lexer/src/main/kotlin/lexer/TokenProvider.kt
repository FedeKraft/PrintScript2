package lexer

import token.Token

interface TokenProvider {
    fun nextToken(): Token

    fun hasNextToken(): Boolean
}
