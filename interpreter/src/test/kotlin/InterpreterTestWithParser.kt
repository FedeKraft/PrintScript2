import factory.LexerFactory
import interpreter.Interpreter
import org.junit.jupiter.api.Test
import parser.ParserFactory
import reader.Reader

class InterpreterTestWithParser {
    @Test
    fun test1() {
        val lexer = LexerFactory().createLexer1_1(Reader("src/test/resources/test1.txt"))
        val parser = ParserFactory().createParser1_1(lexer)
        val interpreter = Interpreter(parser)
        interpreter.interpret()
    }

    @Test
    fun test2() {
        val lexer = LexerFactory().createLexer1_1(Reader("src/test/resources/test3.txt"))
        val parser = ParserFactory().createParser1_1(lexer)
        val interpreter = Interpreter(parser)
        interpreter.interpret()
    }
}
