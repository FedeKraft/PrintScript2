package org.example.rules

import ASTNode

class SingleSpaceBetweenTokensRule : FormatterRule {
    override fun apply(node: ASTNode, code: StringBuilder): StringBuilder {
        val regex = Regex("\\s{2,}")
        val formattedCode = regex.replace(code.toString(), " ")
        return StringBuilder(formattedCode)
    }
}