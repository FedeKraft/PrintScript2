package org.example.rules

import ASTNode
import org.example.LinterError

interface LinterRule {
    fun check(node: ASTNode): List<LinterError>
}
