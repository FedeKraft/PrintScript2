package parser

import ast.BlockNode
import ast.BooleanLiteralNode
import ast.ExpressionNode
import ast.IdentifierNode
import ast.IfElseNode
import ast.StatementNode
import lexer.TokenProvider
import parserTypes.AssignationParser
import parserTypes.ConstDeclarationParser
import parserTypes.Parser
import parserTypes.PrintParser
import parserTypes.VariableDeclarationParser
import token.Token
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
            checkUnknownToken()
            if (isIfOrElseTokenType()) {
                return processBlockNode()
            }
            if (isSemiColonType()) {
                currentToken = tokenProvider.nextToken()
                return processStatement(tokens)
            }

            tokens.add(currentToken)
            currentToken = tokenProvider.nextToken()
        }
        if (tokens.isEmpty()) {
            throw NoSuchElementException("No more tokens available")
        }
        if (!isSemiColonType()) {
            throw RuntimeException("Expected semicolon at line: ${currentToken.line}, column: ${currentToken.column}")
        }
        return processStatement(tokens)
    }

    override fun getNextAST(): StatementNode = nextStatement()

    override fun hasNextAST(): Boolean = tokenProvider.hasNextToken()

    private fun processBlockNode(): StatementNode {
        // guardo la linea y columna del if
        val blockLine = currentToken.line
        val blockColumn = currentToken.column
        // me salteo la palabra reservada if
        currentToken = tokenProvider.nextToken()

        val condition = getIfCondition()
        val blockAst = mutableListOf<StatementNode>()
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
            return IfElseNode(condition, blockStatements, elseBlockNode, blockLine, blockColumn)
        }
        val blockStatements = BlockNode(blockAst, 0, 0)
        return IfElseNode(condition, blockStatements, null, blockLine, blockColumn)
    }

    private fun processStatement(tokens: List<Token>): StatementNode {
        val firstToken = tokens.firstOrNull() ?: throw RuntimeException("Empty token list")
        val parser = getParser(firstToken)
        return parser.parse(tokens)
    }

    private fun getIfCondition(): ExpressionNode {
        currentToken = tokenProvider.nextToken()

        return when (currentToken.type) {
            TokenType.IDENTIFIER -> IdentifierNode(
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

    private fun checkUnknownToken() {
        if (currentToken.type == TokenType.UNKNOWN) {
            throw RuntimeException(
                "unknown token in variable assign at line: ${currentToken.line}, column: ${currentToken.column}",
            )
        }
    }
    private fun isIfOrElseTokenType(): Boolean {
        return currentToken.type == TokenType.IF || currentToken.type == TokenType.ELSE
    }
    private fun isSemiColonType(): Boolean {
        return currentToken.type == TokenType.SEMICOLON
    }

    private fun getParser(firstToken: Token): Parser {
        return when (firstToken.type) {
            TokenType.IDENTIFIER -> AssignationParser()
            TokenType.LET -> VariableDeclarationParser()
            TokenType.PRINT -> PrintParser()
            TokenType.CONST -> ConstDeclarationParser()
            else -> throw RuntimeException("Unsupported token type: ${firstToken.type}")
        }
    }
}