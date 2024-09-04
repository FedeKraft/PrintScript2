import rules.CamelCaseIdentifierRule
import rules.CamelORSnakeRules
import rules.PrintSimpleExpressionRule
import rules.SnakeCaseIdentifierRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.example.parser.ASTProvider
import token.*

class LinterTests {

    @Test
    fun `test CamelCaseIdentifierRule`() {
        val rule = CamelCaseIdentifierRule()
        val node = VariableDeclarationNode(
            identifier = IdentifierNode("snake_case", 1, 1),
            value = StringLiteralNode("value", 1, 1),
            line = 1,
            column = 1
        )
        val errors = rule.apply(node)
        assertEquals(1, errors.size)
        assertEquals("Identifier 'snake_case' should be in camelCase", errors[0].message)
    }

    @Test
    fun `test SnakeCaseIdentifierRule`() {
        val rule = SnakeCaseIdentifierRule()
        val node = VariableDeclarationNode(
            identifier = IdentifierNode("snakeCase", 1, 1),
            value = StringLiteralNode("value", 1, 1),
            line = 1,
            column = 1
        )
        val errors = rule.apply(node)
        assertEquals(1, errors.size)
        assertEquals("Identifier 'snakeCase' should be in snake_case", errors[0].message)
    }

    @Test
    fun `test Linter with PrintSimpleExpressionRule enabled`() {
        val rule = PrintSimpleExpressionRule(isActive = true)
        val node = PrintStatementNode(
            expression = BinaryExpressionNode(
                left = StringLiteralNode("ThisIsAVeryLongStringLiteralThatExceedsFortyCharacters", 1, 1),
                operator = TokenType.SUM,
                right = StringLiteralNode("AndAnotherLongStringLiteralThatAlsoExceedsFortyCharacters", 1, 1),
                line = 1,
                column = 1
            ),
            line = 1,
            column = 1
        )

        val linter = Linter(listOf(rule), object : ASTProvider {
            private var hasNext = true
            override fun getNextAST(): StatementNode {
                hasNext = false
                return node
            }
            override fun hasNextAST(): Boolean = hasNext
        })

        val errors = linter.lint().toList()

        assertEquals(1, errors.size)
        assertEquals("Print statement too complex", errors[0].message)
    }


    @Test
    fun `test Linter with PrintSimpleExpressionRule disabled`() {
        val rule = PrintSimpleExpressionRule(isActive = false)
        val linter = Linter(listOf(rule), TestASTProvider())

        val errors = linter.lint().toList()

        assertEquals(0, errors.size)
    }

    @Test
    fun `test CamelORSnakeRules`() {
        val camelCaseRule = CamelCaseIdentifierRule(isActive = true)
        val snakeCaseRule = SnakeCaseIdentifierRule(isActive = true)
        val rule = CamelORSnakeRules(camelCaseRule, snakeCaseRule)

        val linter = Linter(listOf(rule), TestASTProvider(identifier = "snake_Case"))

        val errors = linter.lint().toList()
        assertEquals(2, errors.size)
        val errorMessages = errors.map { it.message }
        assertEquals("Identifier 'snake_Case' should be in camelCase", errorMessages[0])
        assertEquals("Identifier 'snake_Case' should be in snake_case", errorMessages[1])
    }

    // ASTProvider mock for testing
    class TestASTProvider(private val identifier: String = "complexExpression") : ASTProvider {
        private var hasNext = true

        override fun getNextAST(): StatementNode {
            hasNext = false
            return when (identifier) {
                "complexExpression" -> PrintStatementNode(
                    BinaryExpressionNode(NumberLiteralNode(1.0, 1, 1), TokenType.SUM, NumberLiteralNode(2.0, 1, 1), 1, 1),
                    1, 1
                )
                else -> VariableDeclarationNode(
                    identifier = IdentifierNode(identifier, 1, 1),
                    value = StringLiteralNode("value", 1, 1),
                    line = 1,
                    column = 1
                )
            }
        }

        override fun hasNextAST(): Boolean {
            return hasNext
        }
    }
}
