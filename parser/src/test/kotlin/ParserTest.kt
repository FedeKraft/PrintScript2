// ParserTest.kt
package parser

import command.AssignationParser
import command.ConstDeclarationParser
import command.PrintParser
import command.VariableDeclarationParser
import factory.LexerFactory
import org.junit.jupiter.api.Test
import reader.Reader
import token.TokenType

class ParserTest {

    @Test
    fun testParser() {
        // Initialize tokens
        val reader = Reader("src/test/resources/testCodeIdentifier.txt")
        val lexer = LexerFactory().createLexer1_1(reader)
        // Initialize parsers
        val commands = mapOf(
            TokenType.LET to VariableDeclarationParser(),
            TokenType.ASSIGN to AssignationParser(),
            TokenType.PRINT to PrintParser(),
            TokenType.CONST to ConstDeclarationParser(),
        )

        // Create ParserDirector
        val parserDirector = ParserDirector(lexer, commands)

        var currentAst = parserDirector.getNextAST()
        // Parse the tokens
        while (parserDirector.hasNextAST()) {
            println(currentAst)
            currentAst = parserDirector.getNextAST()
        }
        println(currentAst)
    }
}
