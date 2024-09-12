import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.PrintStatementNode
import ast.StringLiteralNode
import ast.VariableDeclarationNode
import factory.LinterFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.ASTProvider
import parser.DummyASTProvider
import rules.CamelCaseIdentifierRule
import rules.CamelORSnakeRules
import rules.PrintSimpleExpressionRule
import rules.SnakeCaseIdentifierRule
import token.TokenType

class LinterTests {
    @Test
    fun testLintNoErrors() {
        val astProvider = object : ASTProvider {
            private val nodes = listOf(
                VariableDeclarationNode(
                    IdentifierNode("validCamelCase", line = 1, column = 1),
                    TokenType.NUMBER_TYPE,
                    NumberLiteralNode(42.0, line = 1, column = 10),
                    line = 1,
                    column = 1,
                ),
            )
            private var index = 0

            override fun hasNextAST() = index < nodes.size

            override fun getNextAST() = nodes[index++]
        }

        val linter = LinterFactory().createLinter1_0(astProvider, "{}".byteInputStream())

        val errors = linter.lint().toList()

        assertTrue(errors.isEmpty(), "No debería haber errores para nodos válidos en camelCase")
    }

    @Test
    fun testLintWithCamelCaseErrors() {
        val astProvider = object : ASTProvider {
            private val nodes = listOf(
                VariableDeclarationNode(
                    IdentifierNode("Invalid_snake_case", line = 1, column = 1),
                    TokenType.NUMBER_TYPE,
                    NumberLiteralNode(42.0, line = 1, column = 10),
                    line = 1,
                    column = 1,
                ),
            )
            private var index = 0

            override fun hasNextAST() = index < nodes.size

            override fun getNextAST() = nodes[index++]
        }

        val linter = LinterFactory().createLinter1_0(
            astProvider,
            """{ "identifier_format": "camel case" }""".byteInputStream(),
        )

        val errors = linter.lint().toList()

        assertEquals(1, errors.size)
        assertEquals("Identifier 'Invalid_snake_case' should be in camelCase", errors[0].message)
    }

    @Test
    fun `test valid camelCase identifier`() {
        val node = VariableDeclarationNode(
            IdentifierNode("validCamelCase", 1, 1),
            TokenType.STRING_TYPE,
            StringLiteralNode("value", 1, 1),
            1,
            1,
        )
        val camelCaseRule = CamelCaseIdentifierRule(isActive = true)
        val errors = camelCaseRule.apply(node)
        assertEquals(0, errors.size)
    }

    @Test
    fun `test invalid camelCase identifier`() {
        val node = VariableDeclarationNode(
            IdentifierNode("Invalid_camelCase", 1, 1),
            TokenType.STRING_TYPE,
            StringLiteralNode("value", 1, 1),
            1,
            1,
        )
        val camelCaseRule = CamelCaseIdentifierRule(isActive = true)
        val errors = camelCaseRule.apply(node)
        assertEquals(1, errors.size)
        assertEquals("Identifier 'Invalid_camelCase' should be in camelCase", errors[0].message)
    }

    @Test
    fun `test valid snake_case identifier`() {
        val node = VariableDeclarationNode(
            IdentifierNode("valid_snake_case", 1, 1),
            TokenType.STRING_TYPE,
            StringLiteralNode("value", 1, 1),
            1,
            1,
        )
        val snakeCaseRule = SnakeCaseIdentifierRule(isActive = true)
        val errors = snakeCaseRule.apply(node)
        assertEquals(0, errors.size)
    }

    @Test
    fun `test invalid snake_case identifier`() {
        val node = VariableDeclarationNode(
            IdentifierNode("InvalidSnakeCase", 1, 1),
            TokenType.STRING_TYPE,
            StringLiteralNode("value", 1, 1),
            1,
            1,
        )
        val snakeCaseRule = SnakeCaseIdentifierRule(isActive = true)
        val errors = snakeCaseRule.apply(node)
        assertEquals(1, errors.size)
        assertEquals("Identifier 'InvalidSnakeCase' should be in snake_case", errors[0].message)
    }

    @Test
    fun `test valid print expression`() {
        val node = PrintStatementNode(StringLiteralNode("Hello, World!", 1, 1), 1, 1)
        val printRule = PrintSimpleExpressionRule(isActive = true)
        val errors = printRule.apply(node)
        assertEquals(0, errors.size)
    }

    @Test
    fun `test complex print expression`() {
        val node = PrintStatementNode(
            StringLiteralNode("This is a very long and complex expression that exceeds the limit", 1, 1),
            1,
            1,
        )
        val printRule = PrintSimpleExpressionRule(isActive = true)
        val errors = printRule.apply(node)
        assertEquals(1, errors.size)
        assertEquals("Print statement too complex", errors[0].message)
    }

    @Test
    fun `test valid camelCase or snake_case identifier`() {
        val nodeCamel = VariableDeclarationNode(
            IdentifierNode("validCamelCase", 1, 1),
            TokenType.STRING_TYPE,
            StringLiteralNode("value", 1, 1),
            1,
            1,
        )

        val nodeSnake = VariableDeclarationNode(
            IdentifierNode("valid_snake_case", 1, 1),
            TokenType.STRING_TYPE,
            StringLiteralNode("value", 1, 1),
            1,
            1,
        )

        val camelOrSnakeRules = CamelORSnakeRules(CamelCaseIdentifierRule(), SnakeCaseIdentifierRule())

        assertEquals(0, camelOrSnakeRules.apply(nodeCamel).size)
        assertEquals(0, camelOrSnakeRules.apply(nodeSnake).size)

        // Llamadas al linter con dummy AST provider
        val linter = LinterFactory().createLinter1_0(DummyASTProvider(), "{}".byteInputStream())
        linter.lint()

        val linter1 = LinterFactory().createLinter1_1(DummyASTProvider())
        linter1.lint()
    }
}
