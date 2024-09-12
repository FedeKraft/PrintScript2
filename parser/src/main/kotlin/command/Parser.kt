package command

import ast.StatementNode
import token.Token

interface Parser {
    fun parse(tokens: List<Token>): StatementNode
}
