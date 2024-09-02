package org.example.errorCheckers

import token.Token

interface ErrorChecker {
    fun check(tokens: List<Token>): Boolean
}
