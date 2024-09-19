package cli

import com.github.ajalt.clikt.core.CliktCommand

class Cli : CliktCommand(name = "CLi", help = "CLI for custom language operations") {

    override fun run() = Unit
}
