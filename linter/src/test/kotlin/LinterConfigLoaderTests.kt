import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.VariableDeclarationNode
import factory.LinterFactory
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.ASTProvider
import token.TokenType

class LinterConfigLoaderTests {

    @Test
    fun testLoadConfigWithMissingFields() {
        // Simulación de un archivo de configuración con campos faltantes
        val missingFieldsConfig = """
            {
                "camelCaseIdentifier": { "enabled": true }
            }
        """.trimIndent().byteInputStream()

        val astProvider = object : ASTProvider {
            private var hasMore = true

            override fun hasNextAST() = hasMore

            override fun getNextAST(): VariableDeclarationNode {
                hasMore = false
                return VariableDeclarationNode(
                    IdentifierNode("missingField", line = 2, column = 3),
                    TokenType.NUMBER_TYPE,
                    NumberLiteralNode(42.0, line = 2, column = 10),
                    line = 2,
                    column = 3,
                )
            }
        }

        // Usamos la LinterFactory para crear el linter con la configuración parcial
        val linter = LinterFactory().createLinter1_0(astProvider, missingFieldsConfig)

        val errors = linter.lint().toList()

        // Verificamos que no haya errores a pesar de los campos faltantes
        assertTrue(errors.isEmpty(), "Debería manejarse sin errores a pesar de los campos faltantes")
    }

    @Test
    fun testLoadConfigFromJsonWithAllRulesDisabled() {
        // Simulación de un archivo de configuración con todas las reglas desactivadas
        val configFile = """
            {
                "printSimpleExpression": { "enabled": false },
                "snakeCaseIdentifier": { "enabled": false },
                "camelCaseIdentifier": { "enabled": false }
            }
        """.trimIndent().byteInputStream()

        val astProvider = object : ASTProvider {
            private var hasMore = true

            override fun hasNextAST() = hasMore

            override fun getNextAST(): VariableDeclarationNode {
                hasMore = false
                return VariableDeclarationNode(
                    IdentifierNode("anythingGoes", line = 1, column = 1),
                    TokenType.NUMBER_TYPE,
                    NumberLiteralNode(42.0, line = 1, column = 5),
                    line = 1,
                    column = 1,
                )
            }
        }

        // Usamos la LinterFactory para crear el linter con todas las reglas desactivadas
        val linter = LinterFactory().createLinter1_0(astProvider, configFile)

        val errors = linter.lint().toList()

        // Verificamos que no haya errores cuando todas las reglas están desactivadas
        assertTrue(errors.isEmpty(), "No debería haber errores cuando todas las reglas están desactivadas")
    }
}
