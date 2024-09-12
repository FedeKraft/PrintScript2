import config.Config
import factory.LinterFactory
import org.junit.jupiter.api.Test
import parser.ASTProvider
import java.io.ByteArrayInputStream
import java.io.InputStream

class LinterConfigLoaderErrorTests {

    @Test
    fun testLoadConfigWithInvalidStream() {
        val invalidConfigInputStream = ByteArrayInputStream(ByteArray(0))

        try {
            // Creamos una instancia del ASTProvider
            val astProvider = object : ASTProvider {
                override fun hasNextAST() = false
                override fun getNextAST() = throw NotImplementedError()
            }

            // Intentamos cargar la configuración con un InputStream inválido (vacío)
            val linter = LinterFactory().createLinter1_0(astProvider, invalidConfigInputStream)

            // Verificamos que el linter cargó con la configuración por defecto
            val config = Config() // Configuración por defecto
            assert(linter.rules.isNotEmpty()) // El linter debería haber cargado las reglas por defecto
            println("Configuración cargada correctamente con valores por defecto.")
        } catch (e: Exception) {
            // Esperamos que no se produzca una excepción aquí porque el sistema debe usar la configuración por defecto
            println("Error no esperado: ${e::class.simpleName}")
            assert(false) // El test falla si se lanza una excepción
        }
    }

    @Test
    fun testLoadConfigWithValidStream() {
        // Creamos un InputStream con una configuración válida
        val validJson = """{
            "identifier_format": "camel case",
            "mandatory-variable-or-literal-in-println": true
        }"""
        val validConfigInputStream: InputStream = ByteArrayInputStream(validJson.toByteArray())

        // Creamos una instancia del ASTProvider
        val astProvider = object : ASTProvider {
            override fun hasNextAST() = false
            override fun getNextAST() = throw NotImplementedError()
        }

        // Intentamos cargar la configuración con un InputStream válido
        val linter = LinterFactory().createLinter1_0(astProvider, validConfigInputStream)

        // Verificamos que el linter cargó la configuración correctamente
        assert(linter.rules.isNotEmpty()) // El linter debería haber cargado reglas
        println("Configuración cargada correctamente con valores del archivo.")
    }
}
