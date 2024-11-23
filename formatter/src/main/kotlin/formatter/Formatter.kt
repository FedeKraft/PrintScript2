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
                result,
            )
        }
        return result
    }

    fun formatNode(node: Any): String {
        return when (node) {
            is VariableDeclarationNode -> {
                val inferredType = inferType(node.value)
                "let ${formatNode(node.identifier)}:" +
                    "$inferredType = ${formatNode(node.value)};"
            }
            is ConstDeclarationNode -> {
                val inferredType = inferType(node.value)
                "const ${formatNode(node.identifier)}:" +
                    "$inferredType = ${formatNode(node.value)};"
            }
            is AssignationNode -> {
                "${formatNode(node.identifier)} = ${formatNode(node.value)};"
            }
            is PrintStatementNode -> {
                "println(${formatNode(node.expression)});"
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
                "${formatNode(node.left)} $operatorSymbol ${formatNode(node.right)}"
            }
            is IfElseNode -> {
                val ifBlockString = formatNode(node.ifBlock)
                val elseBlockString = node.elseBlock?.let { formatNode(it) } ?: ""
                if (node.elseBlock == null) {
                    "if (${formatNode(node.condition)}) {\n$ifBlockString\n}"
                } else {
                    "if (${formatNode(node.condition)}) " +
                        "{\n$ifBlockString\n}\nelse {\n$elseBlockString\n}"
                }
            }
            is BlockNode -> {
                node.statements.joinToString(separator = "\n") { formatNode(it) }
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

    fun inferType(expression: ExpressionNode): String {
        return when (expression) {
            is StringLiteralNode -> "string"
            is NumberLiteralNode -> "number"
            is BooleanLiteralNode -> "boolean"
            is BinaryExpressionNode -> {
                val leftType = inferType(expression.left)
                val rightType = inferType(expression.right)
                if (leftType == rightType) leftType else "UnknownType"
            }
            else -> "UnknownType"
        }
    }
}
