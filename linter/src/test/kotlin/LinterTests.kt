import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.PrintStatementNode
import ast.StringLiteralNode
import ast.VariableDeclarationNode
import linter.Linter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.ASTProvider
import rules.CamelCaseIdentifierRule
import rules.CamelORSnakeRules
import rules.PrintSimpleExpressionRule
import rules.SnakeCaseIdentifierRule

class LinterTests {

    @Test
    fun testLintNoErrors() {
        val astProvider = object : ASTProvider {
            private val nodes = listOf(
                VariableDeclarationNode(
                    IdentifierNode("validCamelCase", line = 1, column = 1),
                    NumberLiteralNode(42.0, line = 1, column = 10),
                    line = 1,
                    column = 1,
                ),
            )
            private var index = 0

            override fun hasNextAST() = index < nodes.size
            override fun getNextAST() = nodes[index++]
        }

        val camelCaseRule = CamelCaseIdentifierRule(isActive = true)
        val snakeCaseRule = SnakeCaseIdentifierRule(isActive = false)

        val linter = Linter(listOf(camelCaseRule, snakeCaseRule), astProvider)

        val errors = linter.lint().toList()

        assertTrue(errors.isEmpty(), "No debería haber errores para nodos válidos en camelCase")
    }

    @Test
    fun testLintWithCamelCaseErrors() {
        val astProvider = object : ASTProvider {
            private val nodes = listOf(
                VariableDeclarationNode(
                    IdentifierNode("Invalid_snake_case", line = 1, column = 1),
                    NumberLiteralNode(42.0, line = 1, column = 10),
                    line = 1,
                    column = 1,
                ),
            )
            private var index = 0

            override fun hasNextAST() = index < nodes.size
            override fun getNextAST() = nodes[index++]
        }

        val camelCaseRule = CamelCaseIdentifierRule(isActive = true)

        val linter = Linter(listOf(camelCaseRule), astProvider)

        val errors = linter.lint().toList()

        assertEquals(1, errors.size)
        assertEquals("Identifier 'Invalid_snake_case' should be in camelCase", errors[0].message)
    }
    private val camelCaseRule = CamelCaseIdentifierRule()

    @Test
    fun `test valid camelCase identifier`() {
        val node =
            VariableDeclarationNode(IdentifierNode("validCamelCase", 1, 1), StringLiteralNode("value", 1, 1), 1, 1)
        val errors = camelCaseRule.apply(node)
        assertEquals(0, errors.size)
    }

    @Test
    fun `test invalid camelCase identifier`() {
        val node =
            VariableDeclarationNode(IdentifierNode("Invalid_camelCase", 1, 1), StringLiteralNode("value", 1, 1), 1, 1)
        val errors = camelCaseRule.apply(node)
        assertEquals(1, errors.size)
        assertEquals("Identifier 'Invalid_camelCase' should be in camelCase", errors[0].message)
    }
    private val snakeCaseRule = SnakeCaseIdentifierRule()

    @Test
    fun `test valid snake_case identifier`() {
        val node =
            VariableDeclarationNode(IdentifierNode("valid_snake_case", 1, 1), StringLiteralNode("value", 1, 1), 1, 1)
        val errors = snakeCaseRule.apply(node)
        assertEquals(0, errors.size)
    }

    @Test
    fun `test invalid snake_case identifier`() {
        val node =
            VariableDeclarationNode(IdentifierNode("InvalidSnakeCase", 1, 1), StringLiteralNode("value", 1, 1), 1, 1)
        val errors = snakeCaseRule.apply(node)
        assertEquals(1, errors.size)
        assertEquals("Identifier 'InvalidSnakeCase' should be in camelCase", errors[0].message)
    }
    private val printRule = PrintSimpleExpressionRule()

    @Test
    fun `test valid print expression`() {
        val node = PrintStatementNode(StringLiteralNode("Hello, World!", 1, 1), 1, 1)
        val errors = printRule.apply(node)
        assertEquals(0, errors.size)
    }

    @Test
    fun `test complex print expression`() {
        val node =
            PrintStatementNode(
                StringLiteralNode("This is a very long and complex expression that exceeds the limit", 1, 1),
                1,
                1,
            )
        val errors = printRule.apply(node)
        assertEquals(1, errors.size)
        assertEquals("Print statement too complex", errors[0].message)
    }

    private val camelOrSnakeRules = CamelORSnakeRules(CamelCaseIdentifierRule(), SnakeCaseIdentifierRule())

    @Test
    fun `test valid camelCase identifier1`() {
        val node =
            VariableDeclarationNode(IdentifierNode("validCamelCase", 1, 1), StringLiteralNode("value", 1, 1), 1, 1)
        val errors = camelOrSnakeRules.apply(node)
        assertEquals(0, errors.size)
    }

    @Test
    fun `test valid snake_case identifier1`() {
        val node =
            VariableDeclarationNode(IdentifierNode("valid_snake_case", 1, 1), StringLiteralNode("value", 1, 1), 1, 1)
        val errors = camelOrSnakeRules.apply(node)
        assertEquals(0, errors.size)
    }
}
