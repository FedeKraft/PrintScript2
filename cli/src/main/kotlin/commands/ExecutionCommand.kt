package commands

/*
class ExecutionCommand : CliktCommand(help = "Execute the file") {
    private val file by argument(help = "Source file to execute")
    override fun run() {
        var code = File(file).readText()
        val lexer = Lexer(code)
        val tokens: List<Token> = lexer.tokenize()
        val parser = Parser()
        val programNode: ProgramNode = parser.parse(tokens)
        val interpreter: InterpreterImp = InterpreterImp()
        interpreter.interpret(programNode)
        println("file executed")
    }
}
*/
