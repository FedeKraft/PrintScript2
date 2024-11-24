package config

import com.fasterxml.jackson.annotation.JsonProperty

data class FormatterConfig(
    @JsonProperty("space-before-colon")
    val spaceBeforeColon: Boolean = false,
    @JsonProperty("space-after-colon")
    val spaceAfterColon: Boolean = false,
    @JsonProperty("space-around-equals")
    val spaceAroundEquals: Boolean = false,
    @JsonProperty("newline-before-println")
    val newlineBeforePrintln: Int = 0,
    @JsonProperty("indentation")
    val indentation: Int = 4,
)
