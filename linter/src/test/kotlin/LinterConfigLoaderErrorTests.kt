import config.LinterConfigLoader
import org.junit.jupiter.api.Test
import parser.ASTProvider
import java.io.ByteArrayInputStream
import java.io.IOException

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
                invalidConfigInputStream, // Use the empty InputStream
                null, // Passing null as the file path since we're using InputStream
            )
            invalidStreamLoader.load() // Attempt to load a configuration from an empty InputStream
        } catch (e: Exception) {
            // Expect some kind of exception due to invalid configuration stream
            println("Caught expected exception: ${e::class.simpleName}")
            assert(e is IOException || e is IllegalArgumentException || e is IllegalStateException)
        }
    }
}
