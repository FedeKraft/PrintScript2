package org.example.rules

import ASTNode
import SpaceAroundCharsConfig
import VariableDeclarationNode

class SpaceAroundCharsRule(private val config: SpaceAroundCharsConfig) : FormatterRule {

    override fun apply(node: ASTNode, code: StringBuilder): StringBuilder {
        if (!config.enabled || node !is VariableDeclarationNode) {
            return code
        }

        var formattedCode = code
        var charIndex = findCharIndex(code, config.charToFormat, node.line, node.column)

        // Elimina los espacios antes del carácter si config.spaceBefore es falso
        if (!config.spaceBefore) {
            while (charIndex > 0 && formattedCode[charIndex - 1] == ' ') {
                formattedCode.deleteCharAt(charIndex - 1)
                charIndex--
            }
        }

        // Añade un espacio antes del carácter si config.spaceBefore es verdadero
        if (config.spaceBefore) {
            formattedCode.insert(charIndex, ' ')
            charIndex++
        }

        // Elimina los espacios después del carácter si config.spaceAfter es falso
        while (charIndex < formattedCode.length - 1 && formattedCode[charIndex + 1] == ' ') {
            formattedCode.deleteCharAt(charIndex + 1)
        }

        // Añade un espacio después del carácter si config.spaceAfter es verdadero
        if (config.spaceAfter) {
            formattedCode.insert(charIndex + 1, ' ')
        }

        return formattedCode
    }

    private fun findCharIndex(code: StringBuilder, charToFind: Char, line: Int, column: Int): Int {
        return code.indexOfFirst {  it == charToFind }
    }
}
