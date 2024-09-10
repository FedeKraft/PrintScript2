import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.PrintStatementNode
import ast.StringLiteralNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import rules.CamelCaseIdentifierRule
import rules.PrintSimpleExpressionRule
import rules.SnakeCaseIdentifierRule
import java.nio.file.Paths
import ast.VariableDeclarationNode as VariableDeclarationNode1

// Cargar la configuración desde el archivo JSON
object LinterConfigLoader {
    private const val CONFIG_PATH = "src/test/resources/linterConfig.json"

    fun loadConfig(): LinterConfig {
        val mapper = jacksonObjectMapper()
        val jsonPath = Paths.get(CONFIG_PATH).toFile()
        return mapper.readValue(jsonPath)
    }
}

// Data class para la configuración del linter
data class LinterConfig(
    val printSimpleExpression: RuleConfig,
    val snakeCaseIdentifier: RuleConfig,
    val camelCaseIdentifier: RuleConfig,
)

data class RuleConfig(
    val enabled: Boolean,
)

class LinterTests {

    private val config = LinterConfigLoader.loadConfig()

    @Test
    fun testPrintSimpleExpressionRule() {
        if (config.printSimpleExpression.enabled) {
            val rule = PrintSimpleExpressionRule()

            // Crea un StatementNode (en este caso, un PrintStatementNode)
            val node = PrintStatementNode(
                expression = StringLiteralNode("This is a simple print statement", 1, 1),
                line = 1,
                column = 1,
            )

            // Aplica la regla sobre el nodo creado
            val errors = rule.apply(node)

            // Verifica que no haya errores
            assertTrue(errors.isEmpty())
        } else {
            println("PrintSimpleExpressionRule está deshabilitado.")
        }
    }

    @Test
    fun testSnakeCaseIdentifierRule() = if (config.snakeCaseIdentifier.enabled) {
        val rule = SnakeCaseIdentifierRule()

        // Crea un StatementNode con un identificador en snake_case
        val node =
            VariableDeclarationNode1(
                identifier = IdentifierNode("snake_case_identifier", 1, 1),
                value = NumberLiteralNode(42.0, 1, 1),
                line = 1,
                column = 1,
            )

        // Aplica la regla sobre el nodo creado
        val errors = rule.apply(node)

        // Verifica que no haya errores (o ajusta si debe haber)
        assertTrue(errors.isEmpty())
    } else {
        println("SnakeCaseIdentifierRule está deshabilitado.")
    }

    @Test
    fun testCamelCaseIdentifierRule() {
        if (config.camelCaseIdentifier.enabled) {
            val rule = CamelCaseIdentifierRule()

            // Crea un StatementNode con un identificador en camelCase
            val node = VariableDeclarationNode1(
                identifier = IdentifierNode("camelCaseIdentifier", 1, 1),
                value = NumberLiteralNode(42.0, 1, 1),
                line = 1,
                column = 1,
            )

            // Aplica la regla sobre el nodo creado
            val errors = rule.apply(node)

            // Verifica que no haya errores (o ajusta si debe haber)
            assertTrue(errors.isEmpty())
        } else {
            println("CamelCaseIdentifierRule está deshabilitado.")
        }
    }
}
