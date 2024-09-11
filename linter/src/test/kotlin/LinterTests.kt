import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.PrintStatementNode
import ast.StringLiteralNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import linter.Linter
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.ASTProvider
import rules.CamelCaseIdentifierRule
import rules.CamelORSnakeRules
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
                expression = StringLiteralNode("This is a simple print statement"),
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
                identifier = IdentifierNode("snake_case_identifier"),
                value = NumberLiteralNode(42.0),
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
                identifier = IdentifierNode("camelCaseIdentifier"),
                value = NumberLiteralNode(42.0),
            )

            // Aplica la regla sobre el nodo creado
            val errors = rule.apply(node)

            // Verifica que no haya errores (o ajusta si debe haber)
            assertTrue(errors.isEmpty())
        } else {
            println("CamelCaseIdentifierRule está deshabilitado.")
        }
    }

    @Test
    fun testCamelCaseRuleWithInvalidIdentifier() {
        if (config.camelCaseIdentifier.enabled) {
            val rule = CamelCaseIdentifierRule()

            // Crea un StatementNode con un identificador inválido
            val node = VariableDeclarationNode1(
                identifier = IdentifierNode("Invalid_identifier"), // no cumple con camelCase
                value = NumberLiteralNode(42.0),
            )

            // Aplica la regla sobre el nodo creado
            val errors = rule.apply(node)

            // Verifica que haya errores
            assertTrue(errors.isNotEmpty(), "Debería fallar para identificadores que no cumplen camelCase")
        }
    }

    @Test
    fun testCamelORSnakeRulesWithMixedIdentifiers() {
        val astProvider = object : ASTProvider {
            private val nodes = listOf(
                VariableDeclarationNode1(IdentifierNode("camelCaseIdentifier"), NumberLiteralNode(42.0)),
                VariableDeclarationNode1(IdentifierNode("snake_case_identifier"), NumberLiteralNode(42.0)),
            )
            private var index = 0

            override fun hasNextAST() = index < nodes.size
            override fun getNextAST() = nodes[index++]
        }

        val camelCaseRule = CamelCaseIdentifierRule(isActive = true)
        val snakeCaseRule = SnakeCaseIdentifierRule(isActive = true)

        val combinedRule = CamelORSnakeRules(camelCaseRule, snakeCaseRule)

        val linter = Linter(listOf(combinedRule), astProvider)

        // Ejecutar el linter
        val errors = linter.lint().toList()

        // Verificar los errores correctos para cada identificador
        assertTrue(errors.isEmpty(), "Ambos identificadores deben cumplir con sus respectivas reglas")
    }
}
