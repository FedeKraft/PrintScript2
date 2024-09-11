import linter.LinterError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LinterErrorTests {

    @Test
    fun testLinterErrorMessage() {
        val error = LinterError("Identifier 'x' should be in camelCase")
        assertEquals("Identifier 'x' should be in camelCase", error.message)
    }

    @Test
    fun testLinterErrorHandling() {
        val errors = listOf(
            LinterError("Error 1"),
            LinterError("Error 2"),
        )

        assertEquals(2, errors.size)
        assertEquals("Error 1", errors[0].message)
        assertEquals("Error 2", errors[1].message)
    }
}
