import emitter.PrintEmitter
import errorCollector.ErrorCollector
import interpreter.Interpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parser.DummyASTProvider
import provider.TestInputProvider

class InterpreterTest {
    @Test
    fun test() {
        val dummyParser = DummyASTProvider()
        val interpreter = Interpreter(dummyParser, TestInputProvider("TestInput"), PrintEmitter(), ErrorCollector())
        interpreter.interpret()
    }

    @Test
    fun test2() {
        val testInputProvider = TestInputProvider("TestInput")
        val exception =
            assertThrows<IllegalArgumentException> {
                testInputProvider.readInput("TestName")
            }
        assertEquals(exception.message, "Error al leer la entrada del usuario")
    }
}
