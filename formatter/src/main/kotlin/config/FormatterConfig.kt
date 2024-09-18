package config

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

data class Indentation(
    val enabled: Boolean,
    val n: Int,
)

data class FormatterConfig(
    val spaceBeforeColon: SpaceBeforeColonConfig,
    val spaceAfterColon: SpaceAfterColonConfig,
    val spaceAroundEquals: SpaceAroundEqualsConfig,
    val newlineBeforePrintln: NewlineBeforePrintlnConfig,
    val indentation: Indentation,
)
