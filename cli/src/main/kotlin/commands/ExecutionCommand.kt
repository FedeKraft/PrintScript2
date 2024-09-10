package commands

import PrintStatementCommand
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import command.VariableDeclarationParser
import interpreter.Interpreter
import lexer.Lexer
import command.AssignationParser
import parser.ParserDirector
import token.TokenType
import java.io.File

class ExecutionCommand : CliktCommand(help = "Execute the file") {
    private val file by argument(help = "Source file to execute")
    override fun run() {
        val code = File(file).readText()
        val lexer = Lexer(code, patternsMap)
        val parserDirector = ParserDirector(
            lexer,
            mapOf(
                TokenType.LET to VariableDeclarationParser(),
                TokenType.PRINT to PrintStatementCommand(),
                TokenType.IDENTIFIER to AssignationParser(),
            ),
        )
        val interpreter = Interpreter(parserDirector)
        interpreter.interpret()
        println("file executed")
    }
}
