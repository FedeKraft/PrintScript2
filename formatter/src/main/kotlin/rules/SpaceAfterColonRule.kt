package rules

import StatementNode
import VariableDeclarationNode

class SpaceAfterColonRule(private val enabled: Boolean) : FormatterRule {
    override fun applyRule(node: StatementNode,variableTypes: Map<String, Any>): String {
        if (node is VariableDeclarationNode) {
            val identifierPart = node.identifier.name
            val colonPart = if (enabled) ": " else ":"
            val valuePart = node.value.toString()
            return "$identifierPart$colonPart$valuePart;"
        }
        return node.toString()  // Devuelve el nodo original si no aplica
    }
}
