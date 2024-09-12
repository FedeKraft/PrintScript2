package inputProvider
interface InputProvider {
    fun readInput(prompt: String): String
}

class ConsoleInputProvider : InputProvider {
    override fun readInput(prompt: String): String {
        println(prompt)
        return readlnOrNull() ?: throw IllegalArgumentException("Error al leer la entrada del usuario")
    }
}
class TestInputProvider(private val mockInput: String) : InputProvider {
    override fun readInput(prompt: String): String {
        println("Simulated input for test: $mockInput")
        return mockInput
    }
}

