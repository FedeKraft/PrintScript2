package config
data class Config(
    val identifier_format: String = "camel case", // Change to String
    val printSimpleExpression: PrintSimpleExpressionConfig = PrintSimpleExpressionConfig(),
)

data class IdentifierFormatConfig(
    val format: String = "camel case", // Valor por defecto
)

data class PrintSimpleExpressionConfig(
    val mandatoryVariableOrLiteral: Boolean = false, // Valor por defecto
)
