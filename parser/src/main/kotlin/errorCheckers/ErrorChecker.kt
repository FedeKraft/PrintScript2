package org.example.errorCheckers

import Token

interface ErrorChecker {
    fun check(tokens: List<Token>): Boolean
}
