import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) =
    Cli()
        .subcommands(
            // ExecutionCommand(),
            // AnalyzingCommand(),
            // FormattingCommand(),
            // ValidationCommand(),
        )
        .main(args)
