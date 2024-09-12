package config

data class SingleSpaceBetweenTokensConfig(
    val enabled: Boolean,
)
data class SpaceBeforeColonConfig(
    val enabled: Boolean,
)

data class SpaceAfterColonConfig(
    val enabled: Boolean,
)

data class SpaceAroundEqualsConfig(
    val enabled: Boolean,
)

data class NewlineBeforePrintlnConfig(
    val enabled: Boolean,
    val newlineCount: Int,
)

data class NewlineAfterSemicolonConfig(
    val enabled: Boolean,
)

data class SpaceAroundOperatorsConfig(
    val enabled: Boolean,
)
data class Indentation(
    val enabled: Boolean,
    val n: Int,
)

data class FormatterConfig(
    val singleSpaceBetweenTokens: SingleSpaceBetweenTokensConfig,
    val spaceBeforeColon: SpaceBeforeColonConfig,
    val spaceAfterColon: SpaceAfterColonConfig,
    val spaceAroundEquals: SpaceAroundEqualsConfig,
    val newlineBeforePrintln: NewlineBeforePrintlnConfig,
    val newlineAfterSemicolon: NewlineAfterSemicolonConfig,
    val spaceAroundOperators: SpaceAroundOperatorsConfig,
    val indentation: Indentation,
)
