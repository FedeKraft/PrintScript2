import linter.LinterError
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LinterErrorTests {

    @Test
    fun testLinterErrorMessage() {
        val error = LinterError("Identifier 'x' should be in camelCase", line = 1, column = 1)
        assertEquals("Identifier 'x' should be in camelCase", error.message)
        assertEquals(1, error.line)
        assertEquals(1, error.column)
    }

    @Test
    fun testLinterErrorWithLineAndColumn() {
        val error = LinterError("Identifier 'x' should be in camelCase", line = 3, column = 5)
        assertEquals("Identifier 'x' should be in camelCase", error.message)
        assertEquals(3, error.line)
        assertEquals(5, error.column)
    }

    @Test
    fun testMultipleLinterErrors() {
        val errors = listOf(
            LinterError("Error 1", line = 1, column = 1),
            LinterError("Error 2", line = 2, column = 2)
        )

        assertEquals(2, errors.size)
        assertEquals("Error 1", errors[0].message)
        assertEquals(1, errors[0].line)
        assertEquals(1, errors[0].column)

        assertEquals("Error 2", errors[1].message)
        assertEquals(2, errors[1].line)
        assertEquals(2, errors[1].column)
    }
}
