import org.example.Linter
import org.example.rules.CamelCaseIdentifierRule
import org.example.rules.CamelORSnakeRules
import org.example.rules.PrintSimpleExpressionRule
import org.example.rules.SnakeCaseIdentifierRule
import org.example.util.LinterConfigLoader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.TokenType

class LinterTests {

    @Test
    fun `test CamelCaseIdentifierRule`() {
        val rule = CamelCaseIdentifierRule()
        val node = IdentifierNode("snake_case", 1, 1)
        val errors = rule.check(node)
        assertEquals(1, errors.size)
        assertEquals("Identifier snake_case should be in camel case", errors[0].message)
    }

    @Test
    fun `test SnakeCaseIdentifierRule`() {
        val rule = SnakeCaseIdentifierRule()
        val printNode = PrintStatementNode(IdentifierNode("snakeCase", 1, 1), 1, 1)
        val program = ProgramNode(listOf(printNode))
        val linter = Linter(listOf(rule))
        val errors = linter.lint(program)
        assertEquals(1, errors.size)
        assertEquals("Identifier snakeCase should be in snake case", errors[0].message)
    }

    @Test
    fun `test Linter with PrintSimpleExpressionRule enabled`() {
        val config = LinterConfigLoader.loadConfig("src/test/resources/linterConfig.json")
        val rules = listOf(PrintSimpleExpressionRule(config.printSimpleExpression))

        val linter = Linter(rules)

        val printNode = PrintStatementNode(
            BinaryExpressionNode(NumberLiteralNode(1.0, 1, 1), TokenType.SUM, NumberLiteralNode(2.0, 1, 1), 1, 1),
            1,
            1,
        )
        val program = ProgramNode(listOf(printNode))

        val errors = linter.lint(program)

        assertEquals(1, errors.size)
        assertEquals("Binary expression should be simple", errors[0].message)
    }

    @Test
    fun `test Linter with PrintSimpleExpressionRule disabled`() {
        val rules = listOf(PrintSimpleExpressionRule(PrintSimpleExpressionConfig(false)))
        val linter = Linter(rules)

        val printNode = PrintStatementNode(
            BinaryExpressionNode(NumberLiteralNode(1.0, 1, 1), TokenType.SUM, NumberLiteralNode(2.0, 1, 1), 1, 1),
            1,
            1,
        )
        val program = ProgramNode(listOf(printNode))

        val errors = linter.lint(program)

        assertEquals(0, errors.size)
    }

    @Test
    fun `test CamelORSnakeRules`() { // recibe string mal escrito, deberia estar en camel o snake case
        val rule = CamelORSnakeRules()
        val printNode = PrintStatementNode(IdentifierNode("snake_Case", 1, 1), 1, 1)
        val program = ProgramNode(listOf(printNode))
        val linter = Linter(listOf(rule))

        val errors = linter.lint(program)
        assertEquals(2, errors.size)
        val errorMessages = errors.map { it.message }
        assertEquals("Identifier snake_Case should be in camel case", errorMessages[0])
        assertEquals("Identifier snake_Case should be in snake case", errorMessages[1])
    }
}
