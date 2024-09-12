import emitter.PrintEmitter
import errorCollector.ErrorCollector
import factory.LexerFactory
import interpreter.Interpreter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.ParserFactory
import provider.TestInputProvider
import reader.Reader
import java.io.File

class InterpreterTestWithParser {
    @Test
    fun test1() {
        val lexer = LexerFactory().createLexer1_1(Reader(File("src/test/resources/test1.txt").inputStream()))
        val parser = ParserFactory().createParser1_1(lexer)
        val interpreter = Interpreter(parser, TestInputProvider("TestName"), PrintEmitter(), ErrorCollector())
        interpreter.interpret()
    }

    @Test
    fun test2() {
        val lexer = LexerFactory().createLexer1_1(Reader(File("src/test/resources/test2.txt").inputStream()))
        val parser = ParserFactory().createParser1_1(lexer)
        val inputProvider = TestInputProvider("TestName") // Simulamos el input
        val interpreter = Interpreter(parser, inputProvider, PrintEmitter(), ErrorCollector())
        interpreter.interpret()
    }

    @Test
    fun test3() {
        val lexer = LexerFactory().createLexer1_1(Reader(File("src/test/resources/test3.txt").inputStream()))
        val parser = ParserFactory().createParser1_1(lexer)
        val interpreter = Interpreter(parser, TestInputProvider("TestName"), PrintEmitter(), ErrorCollector())
        interpreter.interpret()
    }

    @Test
    fun test4() {
        val lexer = LexerFactory().createLexer1_1(Reader(File("src/test/resources/test4.txt").inputStream()))
        val parser = ParserFactory().createParser1_1(lexer)
        val interpreter = Interpreter(parser, TestInputProvider("TestName"), PrintEmitter(), ErrorCollector())
        interpreter.interpret()
    }

    @Test
    fun test5() {
        val lexer = LexerFactory().createLexer1_1(Reader(File("src/test/resources/test5.txt").inputStream()))
        val parser = ParserFactory().createParser1_1(lexer)
        val interpreter = Interpreter(parser, TestInputProvider(6), PrintEmitter(), ErrorCollector())
        interpreter.interpret()
    }

    @Test
    fun test6() {
        val lexer = LexerFactory().createLexer1_1(Reader(File("src/test/resources/test6.txt").inputStream()))
        val parser = ParserFactory().createParser1_1(lexer)
        val interpreter = Interpreter(parser, TestInputProvider(true), PrintEmitter(), ErrorCollector())
        interpreter.interpret()
    }

    @Test
    fun test7() {
        val lexer = LexerFactory().createLexer1_1(Reader(File("src/test/resources/test7.txt").inputStream()))
        val parser = ParserFactory().createParser1_1(lexer)
        val errorCollector = ErrorCollector()
        val interpreter = Interpreter(parser, TestInputProvider(true), PrintEmitter(), errorCollector)

        interpreter.interpret()

        // Verificamos que el error esperado se haya registrado en el ErrorCollector
        val errors = errorCollector.getErrors()
        assertEquals(1, errors.size)
        assertEquals(
            "Error de tipo en declaración de variable: Se esperaba STRING_TYPE pero se encontró BOOLEAN_TYPE",
            errors[0],
        )
    }

    @Test
    fun test8() {
        val lexer = LexerFactory().createLexer1_1(Reader(File("src/test/resources/test8.txt").inputStream()))
        val parser = ParserFactory().createParser1_1(lexer)
        val errorCollector = ErrorCollector()
        val interpreter = Interpreter(parser, TestInputProvider(true), PrintEmitter(), errorCollector)

        interpreter.interpret()

        // Verificamos que el error esperado se haya registrado en el ErrorCollector
        val errors = errorCollector.getErrors()
        assertEquals(1, errors.size)
        assertEquals("No se puede reasignar una constante: name", errors[0])
    }

    @Test
    fun test9() {
        val lexer = LexerFactory().createLexer1_1(Reader(File("src/test/resources/test9.txt").inputStream()))
        val parser = ParserFactory().createParser1_1(lexer)
        val errorCollector = ErrorCollector()
        val interpreter = Interpreter(parser, TestInputProvider(true), PrintEmitter(), errorCollector)

        interpreter.interpret()

        // Verificamos que los errores esperados se hayan registrado en el ErrorCollector
        val errors = errorCollector.getErrors()
        assertEquals(2, errors.size) // Ahora esperamos 2 errores en lugar de 1
        assertEquals("La variable de entorno '/src' no esta definida", errors[0])

        // Verificamos que el PrintEmitter funcione correctamente
        assertEquals(0, interpreter.getPrintEmitter().getCount())
    }

    @Test
    fun test10() {
        val lexer = LexerFactory().createLexer1_1(Reader(File("src/test/resources/test10.txt").inputStream()))
        val parser = ParserFactory().createParser1_1(lexer)
        val errorCollector = ErrorCollector()
        val interpreter = Interpreter(parser, TestInputProvider(true), PrintEmitter(), errorCollector)

        interpreter.interpret()
        println(errorCollector.getErrors())
        assertEquals(0, errorCollector.getErrors().size)
    }
    @Test
    fun test11() {
        val lexer = LexerFactory().createLexer1_1(Reader(File("src/test/resources/test11.txt").inputStream()))
        val parser = ParserFactory().createParser1_1(lexer)
        val errorCollector = ErrorCollector()
        val interpreter = Interpreter(parser, TestInputProvider("world"), PrintEmitter(), errorCollector)

        interpreter.interpret()
        println(errorCollector.getErrors())
        assertEquals(0, errorCollector.getErrors().size)
    }
}
