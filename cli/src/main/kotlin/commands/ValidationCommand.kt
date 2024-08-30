package commands

/*
class ValidationCommand : CliktCommand(help = "Validate the syntax and semantics of the file") {
    private val file by argument(help = "Source file to validate")
    override fun run() {
        var code = File(file).readText()
        val lexer = Lexer(code)
        val tokens: List<Token> = lexer.tokenize()
        val parser = Parser()
        val programNode: ProgramNode = parser.parse(tokens)
        println("file validated")
    }
}
*/
