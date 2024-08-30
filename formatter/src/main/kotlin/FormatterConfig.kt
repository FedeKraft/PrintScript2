data class FormatterConfig(
    val spaceAroundChars: SpaceAroundCharsConfig,
    val newlineBeforePrintln: NewlineBeforePrintlnConfig,
)

data class SpaceAroundCharsConfig(
    val enabled: Boolean,
    val spaceBefore: Boolean,
    val spaceAfter: Boolean,
    val charToFormat: Char,
)

data class NewlineBeforePrintlnConfig(
    val enabled: Boolean,
    val newlineCount: Int,
)
