package rules

import PrintStatementNode
import StatementNode
import formatter.NewlineBeforePrintlnConfig

class NewlineBeforePrintlnRule(private val config: NewlineBeforePrintlnConfig) : FormatterRule {

    override fun applyRule(node: StatementNode,variableTypes: Map<String, Any>): String {
        if (node is PrintStatementNode) {
            val formattedStatement = StringBuilder()
            if (config.enabled) {
                repeat(config.newlineCount) {
                    formattedStatement.append("\n")
                }
            }
            formattedStatement.append("println(${node.expression})")
            return formattedStatement.toString()
        }
        return node.toString()  // Devuelve el nodo original si no es un PrintStatementNode
    }
}
