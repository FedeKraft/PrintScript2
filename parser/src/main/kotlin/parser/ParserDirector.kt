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

class ParserDirector(
    private val tokenProvider: TokenProvider,
    private val commands: Map<TokenType, Parser>,
) : ASTProvider {
    private var currentToken = tokenProvider.nextToken()

    fun nextStatement(): StatementNode {
        val tokens = mutableListOf<token.Token>()
        while (tokenProvider.hasNextToken()) {
            if (currentToken.type == TokenType.UNKNOWN) {
                throw RuntimeException(
                    "unknown token in variable assign at line: ${currentToken.line}, column: ${currentToken.column}",
                )
            }

            if (currentToken.type == TokenType.IF) {
                return processBlockNode()
            }
            if (currentToken.type == TokenType.ELSE) {
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
        return processStatement(tokens)
    }

    override fun getNextAST(): StatementNode = nextStatement()

    override fun hasNextAST(): Boolean = tokenProvider.hasNextToken()

    private fun processBlockNode(): StatementNode {
        val currentIfLine = currentToken.line
        val currentIfColumn = currentToken.column
        currentToken = tokenProvider.nextToken()
        val condition = getIfCondition()
        val blockAst = mutableListOf<StatementNode>() // armo listita vacia para agregar los statements
        currentToken = tokenProvider.nextToken()
        currentToken = tokenProvider.nextToken()
        currentToken = tokenProvider.nextToken()
        while (currentToken.type != TokenType.CLOSE_BRACE) {
            val blockTokens = mutableListOf<Token>() // lista para los tokens antes de cada parseo de linea
            while (currentToken.type != TokenType.SEMICOLON) { // consigo todos los tokens de la linea
                if (currentToken.type == TokenType.IF) {
                    blockAst.add(processBlockNode())
                }
                blockTokens.add(currentToken)
                currentToken = tokenProvider.nextToken()
            }
            currentToken = tokenProvider.nextToken()
            val currentAst = processStatement(blockTokens) // parseo la linea
            blockAst.add(currentAst) // la agrego a la list de statements y si no se cierra el if se corre la sig linea
        }
        currentToken = tokenProvider.nextToken()
        if (currentToken.type == TokenType.ELSE) {
            val elseBlockNode = processElseBlockNode()
            val blockStatements = BlockNode(blockAst, 0, 0)
            return IfElseNode(condition, blockStatements, elseBlockNode, currentIfLine, currentIfColumn)
        }
        val blockStatements = BlockNode(blockAst, 0, 0)
        return IfElseNode(condition, blockStatements, null, currentIfLine, currentIfColumn)
    }

    private fun processStatement(tokens: List<Token>): StatementNode {
        val firstToken = tokens.firstOrNull() ?: throw RuntimeException("Empty token list")
        val parser =
            when (firstToken.type) {
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
            TokenType.STRING ->
                StringLiteralNode(
                    (currentToken.value as TokenValue.StringValue).value,
                    currentToken.line,
                    currentToken.column,
                )
            TokenType.BOOLEAN ->
                BooleanLiteralNode(
                    (currentToken.value as TokenValue.BooleanValue).value,
                    currentToken.line,
                    currentToken.column,
                )
            else -> throw RuntimeException("Unsupported token type: ${currentToken.type}")
        }
    }

    private fun processElseBlockNode(): BlockNode {
        val blockAst = mutableListOf<StatementNode>() // armo listita vacia para agregar los statements
        currentToken = tokenProvider.nextToken()
        currentToken = tokenProvider.nextToken()

        while (currentToken.type != TokenType.CLOSE_BRACE) {
            val blockTokens = mutableListOf<Token>() // lista para los tokens antes de cada parseo de linea
            while (currentToken.type != TokenType.SEMICOLON) { // consigo todos los tokens de la linea
                if (currentToken.type == TokenType.IF) {
                    blockAst.add(processBlockNode())
                }
                blockTokens.add(currentToken)
                currentToken = tokenProvider.nextToken()
            }
            currentToken = tokenProvider.nextToken()
            val currentAst = processStatement(blockTokens) // parseo la linea
            blockAst.add(currentAst) // la agrego a la list de statements y si no se cierra el if se corre la sig linea
        }
        currentToken = tokenProvider.nextToken()
        return BlockNode(blockAst, 0, 0)
    }
}
