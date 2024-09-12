package inputProvider
interface InputProvider {
    fun readInput(prompt: Any): Any
}

class ConsoleInputProvider : InputProvider {
    override fun readInput(prompt: Any): Any {
        println(prompt)
        return readlnOrNull() ?: throw IllegalArgumentException("Error al leer la entrada del usuario")
    }
}
class TestInputProvider(private val mockInput: Any) : InputProvider {
    override fun readInput(prompt: Any): Any {
        println("Simulated input for test: $mockInput")
        return mockInput
    }
}
