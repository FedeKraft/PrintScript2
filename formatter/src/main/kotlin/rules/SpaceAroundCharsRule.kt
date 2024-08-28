package org.example.rules

import ASTNode
import VariableDeclarationNode

class SpaceAroundCharsRule(
    private val spaceBefore: Boolean, private val spaceAfter: Boolean, private val CharToFormat: Char) : FormatterRule {

    override fun apply(node: ASTNode, code: StringBuilder): StringBuilder {
        //fijarse la configuracion de la regla para ese caracter

        if (node !is VariableDeclarationNode) {
            return StringBuilder()
        }
        var formattedCode = code
        var colonIndex = findCharIndex(code, ':', node.line, node.column)
        //eliminar todos los espacios antes del colon
        while (formattedCode[colonIndex - 1] == ' ') {
            formattedCode = formattedCode.deleteCharAt(colonIndex - 1)
            colonIndex--
            }
        //agregar un espacio antes si spaceBefore es true
        if (spaceBefore) {
            formattedCode = formattedCode.insert(colonIndex, ' ')
            colonIndex++
        }
        // eliminar todos los espacios despues del colon
        while (formattedCode[colonIndex + 1] == ' ') {
            formattedCode = formattedCode.deleteCharAt(colonIndex + 1)
        }
        //agregar un espacio despues si spaceAfter es true
        if (spaceAfter) {
            formattedCode = formattedCode.insert(colonIndex + 1, ' ')
        }

        return formattedCode
    }

    private fun findCharIndex(code: StringBuilder, charToFind: Char, line: Int, column: Int): Int {
        return code.indexOf(charToFind)
    }
}
