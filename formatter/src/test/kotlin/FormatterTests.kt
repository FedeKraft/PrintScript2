import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import config.FormatterConfig
import factory.FormatterFactory
import factory.LexerFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import parser.ParserDirector
import parserTypes.AssignationParser
import parserTypes.PrintParser
import parserTypes.VariableDeclarationParser
import reader.Reader
import token.TokenType
import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths

class FormatterTests {

    private fun readSourceCodeFromFile(filename: String): String {
        return File("src/test/resources/$filename").readText().replace("\r\n", "\n")
    }

    @ParameterizedTest
    @CsvSource(
        "formatterTest2.txt, formatterTest2Expected.txt",
    )
    fun `test formatting with multiple cases`(inputFile: String, expectedFile: String) {
        val expected = readSourceCodeFromFile(expectedFile)
        val unformattedCode = readSourceCodeFromFile(inputFile).toByteArray()
        val inputStream = ByteArrayInputStream(unformattedCode)

        val lexer = LexerFactory().createLexer1_1(Reader(inputStream))
        val parserDirector = ParserDirector(
            lexer,
            mapOf(
                TokenType.PRINT to PrintParser(),
                TokenType.LET to VariableDeclarationParser(),
                TokenType.IDENTIFIER to AssignationParser(),
            ),
        )

        val path = Paths.get("src/test/resources/formatter-config2.json")
        val configFromJSON: InputStream = Files.newInputStream(path)

        val formatter = FormatterFactory().createFormatter1_1(parserDirector, configFromJSON)
        val result = formatter.format().joinToString("\n") { formattedString ->
            formattedString
        }

        println("Generated output:\n$result")
        println("Expected output:\n$expected")
        assertEquals(expected, result)
    }

    @Test
    fun `test formatter config deserialization`() {
        val json = """
        {
          "space-before-colon": true,
          "space-after-colon": true,
          "space-around-equals": true,
          "newline-before-println": 2,
          "indentation": 4
        }
        """
        val mapper = jacksonObjectMapper()
        val config = mapper.readValue<FormatterConfig>(json)
        assertEquals(true, config.spaceBeforeColon)
        assertEquals(true, config.spaceAfterColon)
        assertEquals(4, config.indentation)
    }

    @Test
    fun `validate formatter-config json file`() {
        val path = Paths.get("src/test/resources/formatter-config2.json")
        val configFromJSON: InputStream = Files.newInputStream(path)
        val mapper = jacksonObjectMapper()
        val config = mapper.readValue<FormatterConfig>(configFromJSON)

        // Validating configuration properties
        assertEquals(true, config.spaceBeforeColon)
        assertEquals(4, config.indentation)
        println("Formatter config file validated successfully!")
    }
}
