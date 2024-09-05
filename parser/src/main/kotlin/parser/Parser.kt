package parser

import StatementNode
import command.ParseCommand
import org.example.parser.ASTProvider
import token.TokenProvider
import token.TokenType

class Parser(private val tokenProvider: TokenProvider, private val commands: Map<TokenType, ParseCommand>) :
    ASTProvider {

    fun nextStatement(): StatementNode {
        val tokens = mutableListOf<token.Token>()
        while (tokenProvider.hasNextToken()) {
            val token = tokenProvider.getNextToken()
            if (token.type == TokenType.SEMICOLON) {
                break
            }
            tokens.add(token)
        }
        if (tokens.isEmpty()) {
            throw NoSuchElementException("No more tokens available")
        }
        val command = commands[tokens[0].type] ?: throw IllegalArgumentException("Unknown command")
        return command.execute(tokens)
    }

    override fun getNextAST(): StatementNode {
        return nextStatement()
    }

    override fun hasNextAST(): Boolean {
        return tokenProvider.hasNextToken()
    }
}
