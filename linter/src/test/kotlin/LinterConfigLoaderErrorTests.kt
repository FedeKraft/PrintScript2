import config.LinterConfigLoader
import org.junit.jupiter.api.Test
import parser.ASTProvider

class LinterConfigLoaderErrorTests {

    @Test
    fun testLoadConfigWithInvalidPath() {
        try {
            val invalidPathLoader = LinterConfigLoader(object : ASTProvider {
                override fun hasNextAST() = false
                override fun getNextAST() = throw NotImplementedError()
            })
            invalidPathLoader.load() // Usar una ruta inválida en el cargador de configuración
        } catch (e: Exception) {
            println("Caught exception: ${e::class.simpleName}")
            throw e // Lanza la excepción para depurar qué está fallando
        }
    }
}
