package org.example.rules

import ASTNode

interface FormatterRule {
    fun apply(node: ASTNode, code: StringBuilder): StringBuilder
}
