
import org.example.rules.CamelCaseIdentifierRule
import org.example.rules.CamelORSnakeRules
import org.example.rules.PrintSimpleExpressionRule
import org.example.rules.SnakeCaseIdentifierRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class LinterTests {
    @Test
    fun `test CamelCaseIdentifierRule`() { // recibe string en snake case pero deberia estar en camel case
        val rule = CamelCaseIdentifierRule()
        val node = IdentifierNode("snake_case", 1, 1)
        val errors = rule.check(node)
        assertEquals(1, errors.size)
        assertEquals("Identifier snake_case should be in camel case", errors[0].message)
    }

    @Test
    fun `test SnakeCaseIdentifierRule`() { // recibe string en camel case pero deberia estar en snake case
        val rule = SnakeCaseIdentifierRule()
        val node = IdentifierNode("snakeCase", 1, 1)
        val errors = rule.check(node)
        assertEquals(1, errors.size)
        assertEquals("Identifier snakeCase should be in snake case", errors[0].message)
    }

    @Test
    fun `test CamelORSnakeRules`() { // recibe string mal escrito, deberia estar en camel o snake case
        val rule = CamelORSnakeRules()
        val node = IdentifierNode("snake_Case", 1, 1)
        val errors = rule.check(node)
        assertEquals(2, errors.size)
        val errorMessages = errors.map { it.message }
        assertTrue(errorMessages.contains("Identifier snake_Case should be in camel case"))
        assertTrue(errorMessages.contains("Identifier snake_Case should be in snake case"))
    }

    @Test
    fun `test print with simple expression (identifier)`() {
        val rule = PrintSimpleExpressionRule()
        val printNode = PrintStatementNode(IdentifierNode("myVariable", 1, 1), 1, 1)
        val errors = rule.check(printNode)
        assertEquals(0, errors.size) // No errors expected for simple expressions
    }

    @Test
    fun `test print with simple expression (NumberLiteral)`() {
        val rule = PrintSimpleExpressionRule()
        val printNode = PrintStatementNode(NumberLiteralNode(42.0, 1, 1), 1, 1)
        val errors = rule.check(printNode)
        assertEquals(0, errors.size) // No errors expected for simple expressions
    }

    @Test
    fun `test print with simple expression (StringLiteral)`() {
        val rule = PrintSimpleExpressionRule()
        val printNode = PrintStatementNode(StringLiteralNode("test", 1, 1), 1, 1)
        val errors = rule.check(printNode)
        assertEquals(0, errors.size) // No errors expected for simple expressions
        println(printNode.expression)
    }

    @Test
    fun `test print with Binary expression`() {
        val rule = PrintSimpleExpressionRule()
        val printNode =
            PrintStatementNode(
                BinaryExpressionNode(NumberLiteralNode(1.0, 1, 1), TokenType.SUM, NumberLiteralNode(2.0, 1, 1), 1, 1),
                1,
                1,
            )
        val errors = rule.check(printNode)
        assertEquals(1, errors.size) // Error expected for binary expressions
    }
}
