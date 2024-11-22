package formatter

import ast.AssignationNode
import ast.BinaryExpressionNode
import ast.BlockNode
import ast.BooleanLiteralNode
import ast.ConstDeclarationNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.IfElseNode
import ast.NullValueNode
import ast.NumberLiteralNode
import ast.PrintStatementNode
import ast.ReadEnvNode
import ast.ReadInputNode
import ast.StatementNode
import ast.StringLiteralNode
import ast.VariableDeclarationNode
import parser.ASTProvider
import rules.FormatterRule
import token.TokenType

class Formatter(
    private val rules: List<FormatterRule>,
    private val astProvider: ASTProvider,
) {
    private val variableTypes = mutableMapOf<String, String>()

    fun format(): Sequence<String> =
        sequence {
            while (astProvider.hasNextAST()) {
                val node = astProvider.getNextAST()
                val formattedString = applyRules(node)
                if (formattedString.isNotEmpty()) {
                    yield(formattedString)
                }
            }
        }

    private fun applyRules(node: StatementNode): String {
        var result = formatNode(node)
        for (
        rule in

        rules
        ) {
            result = rule.applyRule(

                node,
                variableTypes,
                result,
            )
        }
        return result
    }


    fun formatNode(node: Any, variableTypes: MutableMap<String, String> = mutableMapOf()): String {
        return when (node) {
            is VariableDeclarationNode -> {
                val inferredType = inferType(node.value, variableTypes)
                variableTypes[node.identifier.name] = inferredType
                "let ${formatNode(node.identifier, variableTypes)}:" +
                        "$inferredType = ${formatNode(node.value, variableTypes)};"
            }
            is ConstDeclarationNode -> {
                val inferredType = inferType(node.value, variableTypes)
                "const ${formatNode(node.identifier, variableTypes)}:" +
                        "$inferredType = ${formatNode(node.value, variableTypes)};"
            }
            is AssignationNode -> {
                "${formatNode(node.identifier, variableTypes)} = ${formatNode(node.value, variableTypes)};"
            }
            is PrintStatementNode -> {
                "println(${formatNode(node.expression, variableTypes)});"
            }
            is IdentifierNode -> {
                node.name
            }
            is NumberLiteralNode -> {
                node.value.toString()
            }
            is StringLiteralNode -> {
                "\"${node.value}\""
            }
            is BooleanLiteralNode -> {
                node.value.toString()
            }
            is BinaryExpressionNode -> {
                val operatorSymbol = when (node.operator) {
                    TokenType.SUM -> "+"
                    TokenType.SUBTRACT -> "-"
                    TokenType.MULTIPLY -> "*"
                    TokenType.DIVIDE -> "/"
                    else -> node.operator.toString()
                }
                "${formatNode(node.left, variableTypes)} $operatorSymbol ${formatNode(node.right, variableTypes)}"
            }
            is IfElseNode -> {
                val ifBlockString = formatNode(node.ifBlock, variableTypes)
                val elseBlockString = node.elseBlock?.let { formatNode(it, variableTypes) } ?: ""
                if (node.elseBlock == null) {
                    "if (${formatNode(node.condition, variableTypes)}) {\n$ifBlockString\n}"
                } else {
                    "if (${formatNode(node.condition, variableTypes)}) {\n$ifBlockString\n}\nelse {\n$elseBlockString\n}"
                }
            }
            is BlockNode -> {
                node.statements.joinToString(separator = "\n") { formatNode(it, variableTypes) }
            }
            is ReadInputNode -> {
                "readInput(${node.value})"
            }
            is ReadEnvNode -> {
                "readEnv(${node.value})"
            }
            is NullValueNode -> {
                ""
            }
            else -> throw IllegalArgumentException("Unsupported node type: ${node::class.simpleName}")
        }
    }

    fun inferType(expression: ExpressionNode, variableTypes: Map<String, String>): String {
        return when (expression) {
            is StringLiteralNode -> "string"
            is NumberLiteralNode -> "number"
            is BooleanLiteralNode -> "boolean"
            is IdentifierNode -> variableTypes[expression.name] ?: "UnknownType"
            is BinaryExpressionNode -> {
                val leftType = inferType(expression.left, variableTypes)
                val rightType = inferType(expression.right, variableTypes)
                if (leftType == rightType) leftType else "UnknownType"
            }
            else -> "UnknownType"
        }
    }
}