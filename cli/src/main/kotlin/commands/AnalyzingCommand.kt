package commands

import Lexer
import ProgramNode
import Token
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import org.example.Linter
import org.example.Parser
import org.example.rules.SnakeCaseIdentifierRule
import org.example.rules.SpaceBeetwenOperatorsRule
import java.io.File

class AnalyzingCommand : CliktCommand(help = "Analyze the file") {
    private val file by argument(help = "Source file to analyze")
    override fun run() {
        var code = File(file).readText()
        val lexer = Lexer(code)
        val tokens : List<Token> = lexer.tokenize()
        val parser = Parser()
        val programNode : ProgramNode = parser.parse(tokens)
        val linter : Linter = Linter(listOf((SnakeCaseIdentifierRule())))
        println("file analyzed")
    }
}