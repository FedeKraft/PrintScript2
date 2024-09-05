package rules

import PrintStatementNode
import StatementNode
import formatter.NewlineBeforePrintlnConfig

class NewlineBeforePrintlnRule(private val config: NewlineBeforePrintlnConfig) : FormatterRule {

    override fun applyRule(node: StatementNode, variableTypes: Map<String, Any>, result: String): String {
        if (node is PrintStatementNode) {
            val formattedStatement = StringBuilder()
            if (config.enabled) {
                repeat(config.newlineCount) {
                    formattedStatement.append("\n")
                }
            }
            formattedStatement.append(result)
            return formattedStatement.toString()
        }
        return result // Devuelve el nodo original si no es un PrintStatementNode
    }
}
