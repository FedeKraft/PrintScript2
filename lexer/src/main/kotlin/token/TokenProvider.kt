package token

interface TokenProvider {
    fun getNextStatementTokens(): List<Token>?
}
