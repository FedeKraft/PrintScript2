package commands

/*
class AnalyzingCommand : CliktCommand(help = "Analyze the file") {
    private val file by argument(help = "Source file to analyze")
    override fun run() {
        var code = File(file).readText()
        val lexer = Lexer(code)
        val tokens: List<Token> = lexer.tokenize()
        val parser = Parser()
        val programNode: ProgramNode = parser.parse(tokens)
        val linter: Linter = Linter(listOf((SnakeCaseIdentifierRule())))
        println("file analyzed")
    }
}
*/
