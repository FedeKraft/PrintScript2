package org.example.rules

import ASTNode
import VariableDeclarationNode

class SpaceAroundColonRule(
    private val spaceBefore: Boolean,
    private val spaceAfter: Boolean
) : FormatterRule {
    override fun apply(node: ASTNode, code: StringBuilder): StringBuilder {
        if (node is VariableDeclarationNode) {
            // Encontrar la posición del `:`
            val colonIndex = 1
                //code.indexOf(":", node.line, node.column)




            if (spaceBefore && colonIndex > 0 && code[colonIndex - 1] != ' ') {
                code.insert(colonIndex, ' ')
            }


            if (spaceAfter && colonIndex < code.length - 1 && code[colonIndex + 1] != ' ') {
                code.insert(colonIndex + 1, ' ')
            }
        }
        return code
    }
}