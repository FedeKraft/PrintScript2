package parser

import ast.StatementNode
import command.Parser
import org.example.parser.ASTProvider
import token.Token
import token.TokenProvider
import token.TokenType

class ParserDirector(private val tokenProvider: TokenProvider, private val commands: Map<TokenType, Parser>) :
    ASTProvider {
        private var currentToken = tokenProvider.nextToken()
    fun nextStatement(): StatementNode {
        val tokens = mutableListOf<token.Token>()
        while (tokenProvider.hasNextToken()) {
            if (currentToken.type == TokenType.UNKNOWN) {
                throw RuntimeException(
                    "Unknown token in variable assignment at line: " +
                            "${currentToken.line}, column: ${currentToken.column}")
            }
            if(currentToken.type == TokenType.IF){
                return processBlockNode()
            }
            if (currentToken.type == TokenType.SEMICOLON) {
                return processStatement(tokens)
            }
            tokens.add(currentToken)
        }
        if (tokens.isEmpty()) {
            throw NoSuchElementException("No more tokens available")
        }
        val command = commands[tokens[0].type]
        if (command != null) {
            return command.parse(tokens)
        }
        throw RuntimeException(
            "Syntax error, cannot initialize a statement with token: ${tokens[0].value}, line: ${tokens[0].line}, column: ${tokens[0].column}",
        )
    }

    override fun getNextAST(): StatementNode {
        return nextStatement()
    }

    override fun hasNextAST(): Boolean {
        return tokenProvider.hasNextToken()
    }

    private fun processBlockNode():StatementNode{
        val blockTokens = mutableListOf<Token>()
        blockTokens.add(currentToken)
        while (currentToken.type != TokenType.CLOSE_BRACE){
            currentToken = tokenProvider.nextToken()
            blockTokens.add(currentToken)
        }
        return BlockParser().parse(blockTokens)
    }
    private fun processStatement(tokens: List<Token>):StatementNode{

    }
}