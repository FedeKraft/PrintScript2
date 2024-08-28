package org.example

import ASTNode
import ProgramNode
import org.example.rules.FormatterRule

// Formatter.kt
class Formatter(private val rules: List<FormatterRule>) {
    fun format(program: ProgramNode, code: String): String {
        val formattedCode = StringBuilder()
        for (statement in program.statements){
            formattedCode.append(applyRules(statement, StringBuilder(code)))
        }
        return formattedCode.toString()
      }

    private fun applyRules(node: ASTNode, code: StringBuilder): String {
        var formattedCode = StringBuilder()
        for (rule in rules) {
            formattedCode = rule.apply(node, code)
        }
        return formattedCode.toString()
    }
}