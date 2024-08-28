package org.example

data class LinterError(
    val message: String,
    val line: Int,
    val column: Int,
)
