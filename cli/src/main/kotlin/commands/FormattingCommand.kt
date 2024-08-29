package commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import org.example.Formatter
import org.example.rules.SpaceBeetwenOperatorsRule
import java.io.File

class FormattingCommand : CliktCommand(help = "Format the file") {
    private val file by argument(help = "Source file to format")
    private val configFile by option(help = "Configuration file for formatting")
    override fun run() {
        var code = File(file).readText()
        val formatter : Formatter = Formatter(listOf(SpaceBeetwenOperatorsRule()))
        println("file formatted")
    }
}