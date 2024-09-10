package command

import ast.StatementNode
import token.Token

interface Parser {
    fun parse(parser: List<Token>): StatementNode
}
