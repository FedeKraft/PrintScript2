package rules

import ast.PrintStatementNode
import ast.StatementNode
import config.NewlineBeforePrintlnConfig

class NewlineBeforePrintlnRule(
    private val config: NewlineBeforePrintlnConfig,
) : FormatterRule {
    override fun applyRule(
        node: StatementNode,
        variableTypes: Map<String, Any>,
        result: String,
    ): String {
        if (config.newlineCount > 2) {
            throw IllegalArgumentException("Newline count must be less than 2")
        }
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
        return result
    }
}
