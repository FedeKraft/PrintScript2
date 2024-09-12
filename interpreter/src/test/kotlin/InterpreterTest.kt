import inputProvider.ConsoleInputProvider
import interpreter.Interpreter
import org.junit.jupiter.api.Test
import parser.DummyASTProvider

class InterpreterTest {
    @Test
    fun test() {
        val dummyParser = DummyASTProvider()
        val interpreter = Interpreter(dummyParser,ConsoleInputProvider())
        interpreter.interpret()
    }
}
