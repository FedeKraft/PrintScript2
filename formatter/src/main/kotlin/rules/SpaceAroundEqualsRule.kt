package rules

import StatementNode
import AssignationNode

class SpaceAroundEqualsRule(private val enabled: Boolean) : FormatterRule {
    override fun applyRule(node: StatementNode, variableTypes: Map<String, Any>): String {
        if (node is AssignationNode) {
            val identifierPart = node.identifier.toFormattedString(variableTypes)
            val equalsPart = if (enabled) " = " else "="
            val valuePart = node.value.toFormattedString(variableTypes)
            return "$identifierPart$equalsPart$valuePart;"
        }
        return node.toFormattedString(variableTypes as MutableMap)  // Usar toFormattedString con variableTypes
    }
}
