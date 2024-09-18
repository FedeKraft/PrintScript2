package config

import com.fasterxml.jackson.annotation.JsonProperty

data class Config(
    @JsonProperty("identifier_format")
    val identifierFormat: String = "none", // Default to none
    @JsonProperty("mandatory-variable-or-literal-in-println")
    val mandatoryVariableOrLiteral: String = "none",
)
