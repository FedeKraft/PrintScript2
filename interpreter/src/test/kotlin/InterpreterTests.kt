import interpreter.Interpreter
import factory.LexerFactory
import org.junit.jupiter.api.Test
import parser.ParserFactory
import java.io.File

class InterpreterTests {

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText()
    }

    private fun createInterpreter(sourceCode: String): Interpreter {
        val lexer = LexerFactory().createLexer1_0(sourceCode)
        val parser = ParserFactory().createParser1_0(lexer)
        return Interpreter(parser)
    }

    @Test
    fun testPrintSubtraction() {
        val interpreter = createInterpreter("print(2-3);")
        println(interpreter.interpret()) // Esto debería imprimir -1
    }

    @Test
    fun testPrintAddition() {
        val interpreter = createInterpreter("print(2+3);")
        println(interpreter.interpret()) // Esto debería imprimir 5
    }

    @Test
    fun testPrintMultiplicationAndAddition() {
        val interpreter = createInterpreter("print(2*3 + 4);")
        println(interpreter.interpret()) // Esto debería imprimir 10
    }

    @Test
    fun testPrintDivision() {
        val interpreter = createInterpreter("print(10/5);")
        println(interpreter.interpret()) // Esto debería imprimir 2
    }

    @Test
    fun testVariableDeclarationAndPrint() {
        val interpreter = createInterpreter("let age : Number = 10; print(age);")
        println(interpreter.interpret()) // Esto debería imprimir 10
    }

    @Test
    fun testStringConcatenationAndPrint() {
        val interpreter = createInterpreter("let name : String = \"Hola\" + \"Mundo\"; print(name);")
        println(interpreter.interpret()) // Esto debería imprimir "HolaMundo"
    }

    @Test
    fun testVariableAssignmentAndPrint() {
        val interpreter = createInterpreter("let name : String = \"Hola\"; name = \"Mundo\"; print(name);")
        println(interpreter.interpret()) // Esto debería imprimir "Mundo"
    }

    @Test
    fun testComplexExpression() {
        val interpreter = createInterpreter("let result : Number = 2 + 3 * 4; print(result);")
        println(interpreter.interpret()) // Esto debería imprimir 14
    }

    @Test
    fun testInterpretFullFile() {
        val interpreter = createInterpreter(readSourceCodeFromFile("testCodeIdentifier.txt"))
        println(interpreter.interpret())
    }
}
