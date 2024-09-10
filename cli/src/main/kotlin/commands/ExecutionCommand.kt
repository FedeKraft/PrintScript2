package commands

import PrintStatementCommand
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import command.VariableDeclarationStatementCommand
import interpreter.Interpreter
import lexer.Lexer
import org.example.command.AssignationCommand
import org.example.parser.Parser
import token.TokenType
import java.io.File

class ExecutionCommand : CliktCommand(help = "Execute the file") {
    private val file by argument(help = "Source file to execute")
    override fun run() {
        val code = File(file).readText()
        val lexer = Lexer(code, patternsMap)
        val parser = Parser(
            lexer,
            mapOf(
                TokenType.LET to VariableDeclarationStatementCommand(),
                TokenType.PRINT to PrintStatementCommand(),
                TokenType.IDENTIFIER to AssignationCommand(),
            ),
        )
        val interpreter = Interpreter(parser)
        interpreter.interpret()
        println("file executed")
    }
}
