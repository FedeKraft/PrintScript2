import interpreter.Interpreter
import org.example.Parser
import org.junit.jupiter.api.Test
import java.io.File

class InterpreterTests {
    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText()
    }

    @Test
    fun `test interpreter`() {
        val code = readSourceCodeFromFile("testCodeIdentifier.txt")
        val lexer = Lexer(code)
        val tokens = lexer.tokenize()
        val parser = Parser()
        val nodes = parser.parse(tokens)
        val interpreter = Interpreter()
        interpreter.interpret(nodes)
    }
}
