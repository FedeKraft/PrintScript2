package parser

import ast.BlockNode
import ast.BooleanLiteralNode
import ast.ExpressionNode
import ast.IfElseNode
import ast.StatementNode
import ast.StringLiteralNode
import command.AssignationParser
import command.ConstDeclarationParser
import command.Parser
import command.PrintParser
import command.VariableDeclarationParser
import token.Token
import token.TokenProvider
import token.TokenType
import token.TokenValue

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
            if(currentToken.type == TokenType.ELSE){
                return processBlockNode()
            }
            if (currentToken.type == TokenType.SEMICOLON) {
                currentToken = tokenProvider.nextToken()
                return processStatement(tokens)
            }
            tokens.add(currentToken)
            currentToken = tokenProvider.nextToken()
        }
        if (tokens.isEmpty()) {
            throw NoSuchElementException("No more tokens available")
        }
        val command = commands[tokens[0].type]
        if (command != null) {
            return command.parse(tokens)
        }
        throw RuntimeException(
            "Syntax error, cannot initialize a statement with token: " +
                    "${tokens[0].value}, line: ${tokens[0].line}, column: ${tokens[0].column}",
        )
    }

    override fun getNextAST(): StatementNode {
        return nextStatement()
    }

    override fun hasNextAST(): Boolean {
        return tokenProvider.hasNextToken()
    }

    private fun processBlockNode():StatementNode{
        currentToken = tokenProvider.nextToken()
        var condition = getIfCondition()
        var blockAst = mutableListOf<StatementNode>() //armo listita vacia para agregar los statements
        currentToken = tokenProvider.nextToken()
        currentToken = tokenProvider.nextToken()
        currentToken = tokenProvider.nextToken()

        while (currentToken.type != TokenType.CLOSE_BRACE){
            var blockTokens = mutableListOf<Token>()  //lista para los tokens antes de cada parseo de linea
            while (currentToken.type != TokenType.SEMICOLON){ // consigo todos los tokens de la linea
                if(currentToken.type == TokenType.IF){
                    blockAst.add( processBlockNode())

                }
                blockTokens.add(currentToken)
                currentToken = tokenProvider.nextToken()
            }
            currentToken = tokenProvider.nextToken()
            var currentAst = processStatement(blockTokens) // parseo la linea
            blockAst.add(currentAst)// la agrego a la lista de statements y si no se cierra el if se corre la siguiente linea

        }
        currentToken = tokenProvider.nextToken()
        var blockStatements = BlockNode(blockAst)
        return IfElseNode(condition, blockStatements)
    }
    private fun processStatement(tokens: List<Token>): StatementNode {
        val firstToken = tokens.firstOrNull() ?: throw RuntimeException("Empty token list")
        val parser = when (firstToken.type) {
            TokenType.IDENTIFIER -> AssignationParser()
            TokenType.LET -> VariableDeclarationParser()
            TokenType.PRINT -> PrintParser()
            TokenType.CONST -> ConstDeclarationParser()

            else -> throw RuntimeException("Unsupported token type: ${firstToken.type}")
        }
        return parser.parse(tokens)
    }

    private fun getIfCondition(): ExpressionNode {
        currentToken = tokenProvider.nextToken()

        return when (currentToken.type) {
            TokenType.STRING -> StringLiteralNode((currentToken.value as TokenValue.StringValue).value)
            TokenType.BOOLEAN -> BooleanLiteralNode((currentToken.value as TokenValue.BooleanValue).value)
            else -> throw RuntimeException("Unsupported token type: ${currentToken.type}")
        }
    }
}