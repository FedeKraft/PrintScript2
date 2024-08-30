package org.example

import ASTNode
import ProgramNode
import org.example.rules.FormatterRule

class Formatter(private val rules: List<FormatterRule>) {

    fun format(program: ProgramNode, code: String): String {
        // Aplica la lógica de newline antes de dividir el código en líneas
        val codeWithNewlines = applyNewlineAfterSemicolon(code)
        val splittedCode = splitCodeIntoLines(codeWithNewlines)
        val formattedCode = StringBuilder()
        for ((index, statement) in program.statements.withIndex()) {
            formattedCode.append(applyRules(statement, StringBuilder(splittedCode[index].trim())))
            if (index != program.statements.size - 1) {
                formattedCode.append("\n") // Añade un salto de línea después de cada statement
            }
        }
        return normalizeLineEndings(formattedCode.toString().trim())
    }

    private fun applyNewlineAfterSemicolon(code: String): String {
        val codeBuilder = StringBuilder(code)
        var index = 0
        while (index < codeBuilder.length) {
            if (codeBuilder[index] == ';') {
                if (index + 1 < codeBuilder.length && codeBuilder[index + 1] != '\n') {
                    codeBuilder.insert(index + 1, '\n')
                }
            }
            index++
        }
        return codeBuilder.toString()
    }

    private fun applyRules(node: ASTNode, code: StringBuilder): StringBuilder {
        var formattedCode = code
        for (rule in rules) {
            formattedCode = rule.apply(node, formattedCode)
        }
        return formattedCode
    }

    private fun splitCodeIntoLines(code: String): Array<String> {
        return code.split("\n").toTypedArray()
    }

    private fun normalizeLineEndings(code: String): String {
        return code.replace("\r\n", "\n")
    }
}
