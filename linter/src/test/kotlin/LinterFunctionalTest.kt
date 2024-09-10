import command.VariableDeclarationStatementCommand
import config.LinterConfigLoader
import lexer.Lexer
import linter.Linter
import linter.LinterError
import command.AssignationCommand
import org.example.parser.Parser
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import rules.CamelCaseIdentifierRule
import rules.PrintSimpleExpressionRule
import rules.SnakeCaseIdentifierRule
import token.TokenType
import java.io.File

class LinterFunctionalTest {

    // Cargar la configuración del linter
    private val config = LinterConfigLoader.loadConfig()

    // Aquí puedes definir las reglas, aunque el objetivo es solo comprobar que el linter funciona sin reglas específicas.
    private val rules = listOf(
        CamelCaseIdentifierRule(config.camelCaseIdentifier.enabled),
        SnakeCaseIdentifierRule(config.snakeCaseIdentifier.enabled),
        PrintSimpleExpressionRule(config.printSimpleExpression.enabled),
    )

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText().replace("\r\n", "\n")
    }

    @Test
    fun `test Linter functional execution`() {
        // Leer código de ejemplo
        val sourceCode = readSourceCodeFromFile("linterFunctionalTest.txt")

        // Inicializar lexer y parser
        val lexer = Lexer(sourceCode, patternsMap)
        val parser = Parser(
            lexer,
            mapOf(
                TokenType.PRINT to PrintStatementCommand(),
                TokenType.LET to VariableDeclarationStatementCommand(),
                TokenType.IDENTIFIER to AssignationCommand(),
            ),
        )

        // Crear instancia del linter
        val linter = Linter(rules, parser)

        // Ejecutar el linter y recoger los errores
        var errors = emptyList<LinterError>()
        while (true) {
            val error = linter.lint().firstOrNull() ?: break
            errors += error
        }

        // Verificar que el proceso de linteo se ejecuta sin fallos (puedes ajustar según lo esperado)
        assertTrue(errors.isEmpty(), "El proceso de linteo generó errores inesperados.")

        // Imprimir cualquier error para depuración
        if (errors.isNotEmpty()) {
            errors.forEach { error ->
                println("linter.Linter error: ${error.message} en línea ${error.line}, columna ${error.column}")
            }
        }
    }
}
