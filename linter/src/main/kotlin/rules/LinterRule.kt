package rules

import ast.StatementNode
import linter.LinterError

interface LinterRule {
    var isActive: Boolean
    fun apply(node: StatementNode): List<LinterError>
}
