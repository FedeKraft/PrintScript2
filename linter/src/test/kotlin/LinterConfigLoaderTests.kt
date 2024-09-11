import ast.IdentifierNode
import ast.NumberLiteralNode
import ast.VariableDeclarationNode
import config.LinterConfigLoader
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.ASTProvider

class LinterConfigLoaderTests {

    @Test
    fun testLoadConfigWithValidPath() {
        // Crear un ASTProvider que devuelva un solo nodo válido
        val astProvider = object : ASTProvider {
            private var hasMore = true // Usar una bandera para simular que solo hay un nodo

            override fun hasNextAST() = hasMore

            override fun getNextAST(): VariableDeclarationNode {
                hasMore = false // Después de devolver el nodo, indica que no hay más nodos
                return VariableDeclarationNode(IdentifierNode("validCamelCase"), NumberLiteralNode(42.0))
            }
        }

        // Cargar el linter con una configuración válida
        val linter = LinterConfigLoader(astProvider).load()

        // Ejecutar el linter
        val errors = linter.lint().toList()

        // Verificar que no haya errores para un identificador válido en camelCase
        assertTrue(errors.isEmpty(), "No debería haber errores para un identificador válido")
    }

    @Test
    fun testLoadConfigWithMissingFields() {
        // Simular un ASTProvider que devuelva un solo nodo
        val astProvider = object : ASTProvider {
            private var hasMore = true // Flag para controlar la entrega de nodos

            override fun hasNextAST() = hasMore

            override fun getNextAST(): VariableDeclarationNode {
                hasMore = false // Después de devolver el nodo, cambiar hasMore a false para detener el ciclo
                return VariableDeclarationNode(IdentifierNode("missingField"), NumberLiteralNode(42.0))
            }
        }

        // Simular la carga de un archivo de configuración con campos faltantes
        val linter = LinterConfigLoader(astProvider).load()

        // Ejecutar el linter
        val errors = linter.lint().toList()

        // Verificar si se manejan correctamente las configuraciones con campos faltantes
        assertTrue(errors.isEmpty(), "Debería manejarse sin errores a pesar de los campos faltantes")
    }
}
