import ast.IdentifierNode
import ast.ReadInputNode
import ast.VariableDeclarationNode
import factory.LexerFactory
import factory.LinterFactory
import factory.ParserFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import reader.Reader
import rules.ReadInputWithSimpleArgumentRule
import token.TokenType
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths

class TestRule {
    @Test
    fun `should report error for readInput with expression argument`() {
        val node =
            VariableDeclarationNode(
                identifier = IdentifierNode(name = "a", line = 1, column = 1),
                type = TokenType.STRING,
                value =
                ReadInputNode(
                    value = "invalidValue", // El valor aquí debería ser algo que cause un error
                    line = 1,
                    column = 1,
                ),
                line = 1,
                column = 1,
            )

        val rule = ReadInputWithSimpleArgumentRule(isActive = true)
        val errors = rule.apply(node)

        assertEquals(1, errors.size)
        assertEquals("readInput debe tener un argumento simple (identificador o literal)", errors[0].message)
    }

    @Test
    fun testLinterFunctionalTest() {
        val path = Paths.get("src/test/resources/linterConfig.json")
        val inputStream: InputStream = Files.newInputStream(path)
        val lexer = LexerFactory().createLexer1_0(
            Reader(File("src/test/resources/linterFunctionalTest.txt").inputStream()),
        )
        val parser = ParserFactory().createParser1_0(lexer)
        val linter = LinterFactory().createLinter1_0(parser, inputStream)
        val result = linter.lint()
        println(result.toList())
    }

    @Test
    fun testLinterFunctionalTest2() {
        val path = Paths.get("src/test/resources/linterConfig2.json")
        val inputStream: InputStream = Files.newInputStream(path)
        val lexer = LexerFactory().createLexer1_0(
            Reader(File("src/test/resources/linterFunctionalTest2.txt").inputStream()),
        )
        val parser = ParserFactory().createParser1_0(lexer)
        val linter = LinterFactory().createLinter1_0(parser, inputStream)

        linter.lint()
    }

    @Test
    fun testLinterFunctionalTest3() {
        val path = Paths.get("src/test/resources/linterConfig3.json")
        val inputStream: InputStream = Files.newInputStream(path)
        val lexer = LexerFactory().createLexer1_0(
            Reader(File("src/test/resources/linterFunctionalTest3.txt").inputStream()),
        )
        val parser = ParserFactory().createParser1_0(lexer)
        val linter = LinterFactory().createLinter1_0(parser, inputStream)

        val errors = linter.lint().toList()
        assertEquals(1, errors.size) }

    @Test
    fun testLinterFunctionalTest4() {
        val path = Paths.get("src/test/resources/linterConfig4.json")
        val inputStream: InputStream = Files.newInputStream(path)
        val lexer = LexerFactory().createLexer1_0(
            Reader(File("src/test/resources/linterFunctionalTest4.txt").inputStream()),
        )
        val parser = ParserFactory().createParser1_0(lexer)
        val linter = LinterFactory().createLinter1_0(parser, inputStream)

        linter.lint()
    }
}
