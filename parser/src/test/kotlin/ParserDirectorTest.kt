import ast.AssignationNode
import ast.PrintStatementNode
import ast.StatementNode
import ast.VariableDeclarationNode
import factory.LexerFactory
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import parser.ParserFactory
import reader.Reader
import java.io.File

class ParserDirectorTest {


    @Disabled
    @Test
    fun testParse() {
        val lexer = LexerFactory().createLexer1_0(Reader("src/test/resources/testCodeIdentifier.txt"))
        val parser = ParserFactory().createParser1_0(lexer)
        var statement: StatementNode
        while (parser.hasNextAST()) {
            statement = parser.nextStatement()
            println(statement)
        }
    }

    @Disabled
    @Test
    fun testPrintParsing() {
        val lexer = LexerFactory().createLexer1_0(Reader("src/test/resources/testCodeIdentifier.txt"))
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

    @Disabled
    @Test
    fun testVariableDeclarationParsing() {
        val lexer = LexerFactory().createLexer1_0(Reader("src/test/resources/testCodeIdentifier.txt"))
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

    @Disabled
    @Test
    fun testAssignationParsing() {
        val lexer = LexerFactory().createLexer1_0(Reader("src/test/resources/testCodeIdentifier.txt"))
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