package provider

interface InputProvider {
    fun readInput(prompt: String): Any
}

class ConsoleInputProvider : InputProvider {
    override fun readInput(prompt: String): Any {
        println("Prompt: $prompt")
        val input = readln()
        println("Input received: $input")
        return input
    }
}

class TestInputProvider(private val simulatedInput: Any) : InputProvider {
    override fun readInput(prompt: String): Any {
        return when (simulatedInput) {
            is String -> simulatedInput
            is Boolean -> simulatedInput.toString()
            is Int -> simulatedInput.toString()
            else -> throw IllegalArgumentException("Error al leer la entrada del usuario")
        }
    }
}
