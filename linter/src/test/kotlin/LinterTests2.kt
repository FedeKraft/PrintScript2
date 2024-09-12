import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.VariableDeclarationNode
import linter.Linter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.ASTProvider
import rules.CamelCaseIdentifierRule
import rules.SnakeCaseIdentifierRule
import token.TokenType

class LinterTests2 {

    @Test
    fun testLintNoErrors() {
        // Crear un ASTProvider que devuelva un nodo válido
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

        // Desactivar la regla de snake_case, ya que estamos probando camelCase
        val camelCaseRule = CamelCaseIdentifierRule(isActive = true)
        val snakeCaseRule = SnakeCaseIdentifierRule(isActive = false)

        // Crear el linter con las reglas
        val linter = Linter(listOf(camelCaseRule, snakeCaseRule), astProvider)

        // Ejecutar el linter
        val errors = linter.lint().toList()

        // Verificar que no haya errores
        assertTrue(errors.isEmpty(), "No debería haber errores para nodos válidos en camelCase")
    }

    @Test
    fun testLintWithErrors() {
        // Crear un ASTProvider que devuelva un nodo inválido
        val astProvider = object : ASTProvider {
            private val nodes = listOf(
                VariableDeclarationNode(
                    IdentifierNode("Invalid_snake_case", line = 2, column = 3),
                    TokenType.NUMBER_TYPE,
                    NumberLiteralNode(42.0, line = 2, column = 10),
                    line = 2,
                    column = 3,
                ),
            )
            private var index = 0

            override fun hasNextAST() = index < nodes.size
            override fun getNextAST() = nodes[index++]
        }

        // Activar solo la regla camelCase, ya que queremos verificar que falle con este identificador
        val camelCaseRule = CamelCaseIdentifierRule(isActive = true)
        val snakeCaseRule = SnakeCaseIdentifierRule(isActive = false)

        // Crear el linter con las reglas
        val linter = Linter(listOf(camelCaseRule, snakeCaseRule), astProvider)

        // Ejecutar el linter
        val errors = linter.lint().toList()

        // Verificar que solo haya un error
        assertEquals(1, errors.size)
        assertEquals("Identifier 'Invalid_snake_case' should be in camelCase", errors[0].message)
        assertEquals(2, errors[0].line)
        assertEquals(3, errors[0].column)
    }

    @Test
    fun testCamelCaseRuleDisabled() {
        val astProvider = object : ASTProvider {
            private var hasMore = true // Usar una bandera para controlar la entrega de nodos

            override fun hasNextAST() = hasMore

            override fun getNextAST(): VariableDeclarationNode {
                hasMore = false // Solo devolver un nodo, luego detener el ciclo
                return VariableDeclarationNode(
                    IdentifierNode("invalid_case", line = 3, column = 4),
                    TokenType.NUMBER_TYPE,
                    NumberLiteralNode(42.0, line = 3, column = 12),
                    line = 3,
                    column = 4,
                )
            }
        }

        // Desactivar la regla de camelCase y activar la regla de snake_case
        val camelCaseRule = CamelCaseIdentifierRule(isActive = false) // Regla desactivada
        val snakeCaseRule = SnakeCaseIdentifierRule(isActive = true) // Regla activa

        // Crear el linter con ambas reglas
        val linter = Linter(listOf(camelCaseRule, snakeCaseRule), astProvider)

        // Ejecutar el linter
        val errors = linter.lint().toList()

        // Verificar que no haya errores porque la regla de camelCase está desactivada
        assertTrue(errors.isEmpty(), "No debería haber errores cuando la regla camelCase está desactivada")
    }
}
