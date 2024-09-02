import command.VariableDeclarationStatementCommand
import interpreter.Interpreter
import lexer.Lexer
import org.example.command.AssignationCommand
import org.example.parser.Parser
import org.junit.jupiter.api.Test
import token.TokenType
import java.io.File

class InterpreterTests {
    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText()
    }

    @Test
    fun `test interpreter`() {
        val code = readSourceCodeFromFile("testCodeIdentifier.txt")
        val lexer = Lexer(code)
        val parser = Parser(lexer, mapOf(
            TokenType.LET to VariableDeclarationStatementCommand(),
            TokenType.PRINT to PrintStatementCommand(),
            TokenType.IDENTIFIER to AssignationCommand()
        ))
        val interpreter = Interpreter(parser)
        interpreter.interpret()

    }
}
