import ast.IdentifierNode
import ast.ReadInputNode
import ast.VariableDeclarationNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import rules.ReadInputWithSimpleArgumentRule
import token.TokenType

class TestRule {
    @Test
    fun `should report error for readInput with expression argument`() {
        val node =
            VariableDeclarationNode(
                identifier = IdentifierNode(name = "a", line = 1, column = 1),
                type = TokenType.STRING,
                value =
                ReadInputNode(
                    value = "invalidValue", // El valor aquí debería ser algo que cause un error
                    line = 1,
                    column = 1,
                ),
                line = 1,
                column = 1,
            )

        val rule = ReadInputWithSimpleArgumentRule(isActive = true)
        val errors = rule.apply(node)

        assertEquals(1, errors.size)
        assertEquals("readInput debe tener un argumento simple (identificador o literal)", errors[0].message)
    }
}
