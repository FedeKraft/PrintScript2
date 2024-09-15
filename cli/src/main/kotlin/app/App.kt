package app

import cli.Cli
import com.github.ajalt.clikt.core.subcommands
import commands.ExecutionCommand
import commands.FormattingCommand
import commands.ValidationCommand

fun main(args: Array<String>) =
    Cli()
        .subcommands(
            ExecutionCommand(),
            // AnalyzingCommand(),
            FormattingCommand(),
            ValidationCommand(),
        ).main(args)
