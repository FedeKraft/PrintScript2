package token

interface TokenProvider {
    fun getNextToken(): Token
    fun hasNextToken(): Boolean
}
