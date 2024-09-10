package formatter.config

data class SpaceBeforeColonConfig(val enabled: Boolean)
data class SpaceAfterColonConfig(val enabled: Boolean)
data class SpaceAroundEqualsConfig(val enabled: Boolean)
data class NewlineBeforePrintlnConfig(val enabled: Boolean, val newlineCount: Int)
data class NewlineAfterSemicolonConfig(val enabled: Boolean)
data class SingleSpaceBetweenTokensConfig(val enabled: Boolean)
data class SpaceAroundOperatorsConfig(val enabled: Boolean)

data class FormatterConfig(
    val spaceBeforeColon: SpaceBeforeColonConfig,
    val spaceAfterColon: SpaceAfterColonConfig,
    val spaceAroundEquals: SpaceAroundEqualsConfig,
    val newlineBeforePrintln: NewlineBeforePrintlnConfig,
    val newlineAfterSemicolon: NewlineAfterSemicolonConfig,
    val singleSpaceBetweenTokens: SingleSpaceBetweenTokensConfig,
    val spaceAroundOperators: SpaceAroundOperatorsConfig,
)
