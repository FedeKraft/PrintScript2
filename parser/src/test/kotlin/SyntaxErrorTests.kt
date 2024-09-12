package parser

import factory.LexerFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import reader.Reader
import java.lang.RuntimeException

class SyntaxErrorTests {

    @Test
    fun `test variable declaration errors`() {
        val reader = Reader("src/test/resources/VariableDeclarationErrors/VariableDeclarationErrors.txt")
        val lexer = LexerFactory().createLexer1_1(reader)
        val parser = ParserFactory().createParser1_1(lexer)
        val actualErrors = mutableListOf<String>()
        while (parser.hasNextAST()) {
            try {
                parser.getNextAST() // This will raise an error for incorrect lines
            } catch (e: RuntimeException) {
                actualErrors.add(e.message!!) // Capture the error message
            }
        }
        val expectedErrors = listOf(
            "Expected 'IDENTIFIER', found COLON line: 1, column: 5",
            "Expected ':', found STRING_TYPE line: 2, column: 7",
            "Expected type 'STRING_TYPE' or 'NUMBER_TYPE' or 'BOOLEAN_TYPE', found IDENTIFIER line: 3, column: 9",
            "Expected '=', found STRING line: 4, column: 17",
        )
        assertEquals(expectedErrors, actualErrors, "The error messages do not match the expected ones")
    }

    @Test
    fun `test assignation errors`() {
        val reader = Reader("src/test/resources/AssignationErrors/AssignationErrors.txt")
        val lexer = LexerFactory().createLexer1_1(reader)
        val parser = ParserFactory().createParser1_1(lexer)
        val actualErrors = mutableListOf<String>()
        while (parser.hasNextAST()) {
            try {
                parser.getNextAST() // This will raise an error for incorrect lines
            } catch (e: RuntimeException) {
                actualErrors.add(e.message!!) // Capture the error message
            }
        }
        val expectedErrors = listOf(
            "Missing value token after = in line: 1, column: 3",
            "Missing ASSIGN token line: 2, column: 3",
            "Invalid value token in variable assignment line: 3, column: 5",
        )
        assertEquals(expectedErrors, actualErrors, "The error messages do not match the expected ones")
    }

    @Test
    fun `test print statement errors`() {
        val reader = Reader("src/test/resources/PrintErrors/PrintErrors.txt")
        val lexer = LexerFactory().createLexer1_1(reader)
        val parser = ParserFactory().createParser1_1(lexer)
        val actualErrors = mutableListOf<String>()
        while (parser.hasNextAST()) {
            try {
                parser.getNextAST() // This will raise an error for incorrect lines
            } catch (e: RuntimeException) {
                actualErrors.add(e.message!!) // Capture the error message
            }
        }
        val expectedErrors = listOf(
            "Print statement is missing a RIGHT_PARENTHESIS token line: 1, column: 1",
            "Missing args in print statement line: 2, column: 8",
            "Print statement is missing a LEFT_PARENTHESIS token line: 3, column: 1",
            "Unexpected token order in print statement line: 4, column: 8",
            "Invalid number of arguments in print statement line: 5, column: 14",
            "Invalid argument in print statement line: 6, column: 9",
        )
        assertEquals(expectedErrors, actualErrors, "The error messages do not match the expected ones")
    }
}
