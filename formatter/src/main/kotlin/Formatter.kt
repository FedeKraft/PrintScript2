package org.example

import ASTNode
import ProgramNode
import org.example.rules.FormatterRule

class Formatter(private val rules: List<FormatterRule>) {
    fun format(program: ProgramNode, code: String): String {
        val splittedCode = splitCodeIntoLines(code)
        val formattedCode = StringBuilder()
        for ((index, statement) in program.statements.withIndex()) {
            formattedCode.append(applyRules(statement, StringBuilder(splittedCode[index].trim())))
        }
        return formattedCode.toString().trim()
    }

    private fun applyRules(node: ASTNode, code: StringBuilder): String {
        var formattedCode = code
        for (rule in rules) {
            formattedCode = rule.apply(node, formattedCode)
        }
        return formattedCode.toString()
    }

    fun splitCodeIntoLines(code: String): Array<String> {
        return code.split("\n").toTypedArray()
    }
}