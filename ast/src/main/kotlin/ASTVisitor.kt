package org.example.visitor

import AssignationNode
import BinaryExpressionNode
import IdentifierNode
import NumberLiteralNode
import PrintStatementNode
import ProgramNode
import StringLiteralNode
import VariableDeclarationNode

interface ASTVisitor {
    fun visit(programNode: ProgramNode)
    fun visit(variableDeclarationNode: VariableDeclarationNode)
    fun visit(printStatementNode: PrintStatementNode)
    fun visit(identifierNode: IdentifierNode)
    fun visit(stringLiteralNode: StringLiteralNode)
    fun visit(assignationNode: AssignationNode)
    fun visit(numberLiteralNode: NumberLiteralNode)
    fun visit(binaryExpressionNode: BinaryExpressionNode)
}
