import emitter.PrintEmitter
import errorCollector.ErrorCollector
import interpreter.Interpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parser.DummyASTProvider
import provider.ConsoleInputProvider

class InterpreterTest {
    @Test
    fun test() {
        val dummyParser = DummyASTProvider()
        val interpreter = Interpreter(dummyParser, ConsoleInputProvider(), PrintEmitter(), ErrorCollector())
        interpreter.interpret()
    }

    @Test
    fun test2() {
        val consoleInputProvider = ConsoleInputProvider()
        val exception = assertThrows<IllegalArgumentException> {
            consoleInputProvider.readInput("TestName")
        }
        assertEquals(exception.message, "Error al leer la entrada del usuario")
    }
}
