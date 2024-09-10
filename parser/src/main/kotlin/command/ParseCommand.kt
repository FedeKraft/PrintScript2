package command

import ast.StatementNode
import token.Token

interface ParseCommand {
    fun execute(tokens: List<Token>): StatementNode
}
