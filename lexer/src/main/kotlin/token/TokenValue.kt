package token

sealed class TokenValue {

    data class StringValue(val value: String) : TokenValue() {
        override fun toString(): String {
            return  value.replace('"', '\'').replace("'", "")
        }
    }
    data class NumberValue(val value: Double) : TokenValue() {
        override fun toString(): String {
            return value.toString()
        }
    }
    data class BooleanValue(val value: Boolean) : TokenValue() {
        override fun toString(): String {
            return value.toString()
        }
    }
    object NullValue : TokenValue()
}
