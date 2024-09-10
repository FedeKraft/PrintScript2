package token

interface TokenProvider {
    fun nextToken(): Token
    fun hasNextToken(): Boolean
}
