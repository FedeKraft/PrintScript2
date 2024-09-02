import command.ParseCommand
import command.VariableDeclarationStatementCommand
import lexer.LexerFactory
import org.example.command.AssignationCommand
import org.example.parser.Parser
import org.junit.jupiter.api.Test
import token.TokenType
import java.io.File

class ParserTest{

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText()
    }
    @Test
    fun testParse(){
        val lexer = LexerFactory().createLexer1_0(readSourceCodeFromFile("testCodeIdentifier.txt"))
        val parser = Parser(lexer, mapOf(
            TokenType.LET to VariableDeclarationStatementCommand(),
            TokenType.PRINT to PrintStatementCommand(),
            TokenType.IDENTIFIER to AssignationCommand()
        ))
        var statement = parser.nextStatement()
        while(true){
            println(statement)
            statement = parser.nextStatement()
        }
    }
    @Test
    fun testParse2(){
        val lexer = LexerFactory().createLexer1_0(readSourceCodeFromFile("testCodeIdentifier.txt"))
        val parser = Parser(lexer, mapOf(
            TokenType.LET to VariableDeclarationStatementCommand(),
            TokenType.PRINT to PrintStatementCommand(),
            TokenType.IDENTIFIER to AssignationCommand()
        ))
        var statement = parser.nextStatement()
        while (statement != null) {
            println(statement)
            statement = parser.nextStatement()

        }

    }

}