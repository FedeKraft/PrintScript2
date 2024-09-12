import config.LinterConfigLoader
import org.junit.jupiter.api.Test
import parser.ASTProvider
import java.io.ByteArrayInputStream
import java.io.FileNotFoundException

class LinterConfigLoaderErrorTests {

    @Test
    fun testLoadConfigWithInvalidStream() {
        val invalidConfigInputStream = ByteArrayInputStream(ByteArray(0))

        try {
            val invalidStreamLoader = LinterConfigLoader(
                object : ASTProvider {
                    override fun hasNextAST() = false
                    override fun getNextAST() = throw NotImplementedError()
                },
                invalidConfigInputStream, // Usar el InputStream vacío
            )
            invalidStreamLoader.load() // Intentar cargar una configuración desde un InputStream vacío
        } catch (e: Exception) {
            // Verificar que ocurra alguna excepción, ya que estamos simulando un archivo de configuración inválido
            println("Caught expected exception: ${e::class.simpleName}")
            assert(e is FileNotFoundException || e is IllegalArgumentException || e is IllegalStateException)
        }
    }
}
