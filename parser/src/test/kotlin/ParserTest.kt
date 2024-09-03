import lexer.LexerFactory
import org.junit.jupiter.api.Test
import parser.ParserFactory
import java.io.File

class ParserTest {

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText()
    }

    @Test
    fun testParse() {
        val lexer = LexerFactory().createLexer1_0(readSourceCodeFromFile("testCodeIdentifier.txt"))
        val parser = ParserFactory().createParser1_0(lexer)
        var statement: StatementNode
        while (parser.hasNextAST()) {
            statement = parser.nextStatement()
            println(statement)
        }
    }

    @Test
    fun testPrintParsing() {
        val lexer = LexerFactory().createLexer1_0(readSourceCodeFromFile("testCodeIdentifier.txt"))
        val parser = ParserFactory().createParser1_0(lexer)
        val statements = mutableListOf<StatementNode>()
        var statement: StatementNode
        while (parser.hasNextAST()) {
            statement = parser.nextStatement()
            statements.add(statement)
        }
        for (s in statements) {
            if (s is PrintStatementNode) {
                println(s)
            }
        }
    }

    @Test
    fun testVariableDeclarationParsing() {
        val lexer = LexerFactory().createLexer1_0(readSourceCodeFromFile("testCodeIdentifier.txt"))
        val parser = ParserFactory().createParser1_0(lexer)
        val statements = mutableListOf<StatementNode>()
        var statement: StatementNode
        while (parser.hasNextAST()) {
            statement = parser.nextStatement()
            statements.add(statement)
        }
        for (s in statements) {
            if (s is VariableDeclarationNode) {
                println(s)
            }
        }
    }

    @Test
    fun testAssignationParsing() {
        val lexer = LexerFactory().createLexer1_0(readSourceCodeFromFile("testCodeIdentifier.txt"))
        val parser = ParserFactory().createParser1_0(lexer)
        val statements = mutableListOf<StatementNode>()
        var statement: StatementNode
        while (parser.hasNextAST()) {
            statement = parser.nextStatement()
            statements.add(statement)
        }
        for (s in statements) {
            if (s is AssignationNode) {
                println(s)
            }
        }
    }
}
