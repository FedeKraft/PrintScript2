package org.example

import ASTNode
import PrintStatementNode
import ProgramNode
import VariableDeclarationNode
import org.example.rules.*

class Linter(private val rules: List<LinterRule>) {
    fun lint(program: ProgramNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()
        for (statement in program.statements) {
            errors.addAll(checkNode(statement))
        }
        return errors
    }

    private fun checkNode(node: ASTNode): List<LinterError> {
        val errors = mutableListOf<LinterError>()
        for (rule in rules) {
            if (node is PrintStatementNode){
                errors.addAll(rule.check(node.expression))
            }
            if( node is VariableDeclarationNode){
                errors.addAll(rule.check(node.identifier))
            }
        }
        return errors
    }
}
