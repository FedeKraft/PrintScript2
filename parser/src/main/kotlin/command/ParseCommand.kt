package command

import ASTNode
import token.Token

interface ParseCommand {
    fun execute(tokens: List<Token>): ASTNode
}
