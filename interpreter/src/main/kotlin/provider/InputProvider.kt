package provider

interface InputProvider {
    fun readInput(prompt: Any): Any
}

class ConsoleInputProvider : InputProvider {
    override fun readInput(prompt: Any): Any {
        println("Prompt: $prompt")
        val input = readln()
        println("Input received: $input")
        return input
    }
}



class TestInputProvider(
    private val mockInput: Any,
) : InputProvider {
    override fun readInput(prompt: Any): Any {
        println("Simulated input for test: $mockInput")
        return mockInput
    }
}
