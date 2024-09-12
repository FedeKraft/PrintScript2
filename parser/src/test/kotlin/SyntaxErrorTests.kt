package parser

import ast.StatementNode
import command.AssignationParser
import factory.LexerFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import reader.Reader
import token.TokenType
import java.lang.RuntimeException

class SyntaxErrorTests {

    @Test
    fun `test variable declaration errors`() {
        // Initialize reader with the file that contains erroneous declarations
        val reader = Reader("src/test/resources/VariableDeclarationErrors.txt")
        val lexer = LexerFactory().createLexer1_1(reader)

        // Define the command map, using only necessary parsers
        val commands = mapOf(
            TokenType.LET to AssignationParser()
        )
        // Create the ParserDirector
        val parserDirector = ParserDirector(lexer, commands)
        // List to capture actual error messages
        val actualErrors = mutableListOf<String>()
        // Try to parse the AST and catch errors
        while (parserDirector.hasNextAST()) {
            try {
                parserDirector.getNextAST() // This will raise an error for incorrect lines
            } catch (e: RuntimeException) {
                actualErrors.add(e.message!!) // Capture the error message
            }
        }
        // Expected error messages
        val expectedErrors = listOf(
            "Expected 'IDENTIFIER', found COLON line: 1, column: 5",
            "Expected ':', found STRING_TYPE line: 2, column: 7",
            "Expected type 'STRING_TYPE' or 'NUMBER_TYPE' or 'BOOLEAN_TYPE', found IDENTIFIER line: 3, column: 9",
            "Expected '=', found STRING line: 4, column: 17"
        )
        // Assert that the actual error messages match the expected ones
        assertEquals(expectedErrors, actualErrors, "The error messages do not match the expected ones")
    }
}
