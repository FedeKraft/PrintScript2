package rules

import LinterError
import StatementNode

interface LinterRule {
    var isActive: Boolean
    fun apply(node: StatementNode): List<LinterError>
}
