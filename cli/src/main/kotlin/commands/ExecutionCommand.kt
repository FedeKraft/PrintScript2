package commands

import Lexer
import ProgramNode
import Token
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import org.example.Parser
import java.io.File

class ExecutionCommand : CliktCommand(help = "Execute the file") {
    private val file by argument(help = "Source file to execute")
    override fun run() {
        var code = File(file).readText()
        val lexer = Lexer(code)
        val tokens : List<Token> = lexer.tokenize()
        val parser = Parser()
        val programNode : ProgramNode = parser.parse(tokens)
        val interpreter : Interpreter = Interpreter()
        interpreter.interpret(programNode)
        println("file executed")
    }
}