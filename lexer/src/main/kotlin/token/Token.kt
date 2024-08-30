package token

data class Token(val type: TokenType, val value: TokenValue, val line: Int, val column: Int)
