data class LinterConfig(
    val printSimpleExpression: PrintSimpleExpressionConfig,
    val snakeCaseIdentifier: SnakeCaseIdentifierConfig,
    val camelCaseIdentifier: CamelCaseIdentifierConfig
)

data class PrintSimpleExpressionConfig(
    val enabled: Boolean
)

data class SnakeCaseIdentifierConfig(
    val enabled: Boolean
)

data class CamelCaseIdentifierConfig(
    val enabled: Boolean
)
