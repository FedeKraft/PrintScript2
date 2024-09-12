package token

sealed class TokenValue {
    data class StringValue(
        val value: String,
    ) : TokenValue() {
        override fun toString(): String = value.replace('"', '\'').replace("'", "")
    }

    data class NumberValue(
        val value: Double,
    ) : TokenValue() {
        override fun toString(): String = value.toString()
    }

    data class BooleanValue(
        val value: Boolean,
    ) : TokenValue() {
        override fun toString(): String = value.toString()
    }

    object NullValue : TokenValue()
}
