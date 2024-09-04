package rules

import StatementNode
import ExpressionNode
import BinaryExpressionNode
import AssignationNode
import VariableDeclarationNode
import PrintStatementNode

class SpaceAroundOperatorsRule : FormatterRule {
    override fun applyRule(node: StatementNode, variableTypes: Map<String, Any>, result: String): String {
        val operators = setOf('+', '-', '*', '/')
        val modifiedResult = StringBuilder()
        var i = 0

        while (i < result.length) {
            val char = result[i]
            if (char in operators) {
                if (i > 0 && result[i - 1] != ' ') {
                    modifiedResult.append(' ')
                }
                modifiedResult.append(char)
                if (i < result.length - 1 && result[i + 1] != ' ') {
                    modifiedResult.append(' ')
                }
            } else {
                modifiedResult.append(char)
            }
            i++
        }
        return modifiedResult.toString()
    }
}

