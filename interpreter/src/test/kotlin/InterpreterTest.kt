import factory.LexerFactory
import interpreter.Interpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.ASTProvider
import parser.ParserFactory
import reader.Reader
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class InterpreterTest {

    @Test
    fun `test interpreter with print statement`() {
        // Leer el archivo de prueba

        // Crear un ASTProvider (deberás tener una implementación para esto)
        val astProvider = createASTProviderFromCode("src/test/resources/testCodeIdentifier.txt")

        // Redirigir la salida estándar para capturar el output del println
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        // Crear el intérprete y ejecutar el código
        val interpreter = Interpreter(astProvider)
        interpreter.interpret()

        // Normalizar saltos de línea para evitar problemas de plataforma
        val actualOutput = outputStream.toString().replace("\r\n", "\n").trim()

        // Definir el output esperado basado en las operaciones del archivo testCodeIdentifier.txt
        val expectedOutput = """
            -1.0
            5.0
            10.0
            2.0
            HolaMundo
            10.0
            Hola
            20.0
            14.0
            1.0
            15.0
            7.0
        """.trimIndent()

        // Verificar que el output sea el esperado
        assertEquals(expectedOutput, actualOutput)
    }

    // Método hipotético para crear el ASTProvider desde el código
    private fun createASTProviderFromCode(path: String): ASTProvider {
        val lexer = LexerFactory().createLexer1_1(Reader(path))

        val parser = ParserFactory().createParser1_1(lexer)
        while (lexer.hasNextToken()) {
            print(lexer.nextToken())
        }
        return parser
    }

    @Test
    fun `test interpreter with print statement2`() {
        val astProvider = createASTProviderFromCode("src/test/resources/test2.txt")
        // val outputStream = ByteArrayOutputStream()
        // System.setOut(PrintStream(outputStream))
        // val interpreter = Interpreter(astProvider)
        // interpreter.interpret()
        // val actualOutput = outputStream.toString().replace("\r\n", "\n").trim()
        // val expectedOutput = "\"Result: \" + 3"
        // assertEquals(expectedOutput, actualOutput)
    }
}
