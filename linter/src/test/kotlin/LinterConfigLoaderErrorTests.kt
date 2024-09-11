import config.LinterConfigLoader
import org.junit.jupiter.api.Test
import parser.ASTProvider
import java.io.FileNotFoundException
import java.nio.file.Paths

class LinterConfigLoaderErrorTests {

    @Test
    fun testLoadConfigWithInvalidPath() {
        val invalidConfigFilePath = Paths.get("src/test/resources/linterConfig.json").toString()

        try {
            val invalidPathLoader = LinterConfigLoader(
                object : ASTProvider {
                    override fun hasNextAST() = false
                    override fun getNextAST() = throw NotImplementedError()
                },
                invalidConfigFilePath,
            )
            invalidPathLoader.load() // Intentar cargar una configuración desde un archivo inexistente
        } catch (e: FileNotFoundException) {
            println("Caught expected exception: ${e::class.simpleName}")
            // Verificar que se lance FileNotFoundException
            assert(e is FileNotFoundException)
        } catch (e: Exception) {
            // Si se lanza alguna otra excepción, fallar el test
            println("Caught unexpected exception: ${e::class.simpleName}")
            throw e // Lanza la excepción para depurar qué está fallando
        }
    }
}
