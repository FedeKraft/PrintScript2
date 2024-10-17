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
        val tokens = mutableListOf<Token>()
        while (tokenProvider.hasNextToken()) {
            checkUnknownToken()
            if (isIfTokenType() || isElseTokenType()) {
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
        val blockLine = currentToken.line
        val blockColumn = currentToken.column
        currentToken = tokenProvider.nextToken() // salteo el token de if
        val condition = getIfCondition()
        advanceToStartOfBlock() // Me posiciono directamente dentro del bloque
        val ifBlock = parseBlockStatements()
        // Check for 'else' block
        val elseBlock = if (isElseTokenType()) {
            currentToken = tokenProvider.nextToken() // Skip 'else'
            advanceToStartOfBlock() // Position at the start of the else block
            BlockNode(parseBlockStatements(),0, 0)
        } else {
            null
        }
        return IfElseNode(condition, BlockNode(ifBlock, blockLine, blockColumn), elseBlock, blockLine, blockColumn)
    }

    private fun getIfCondition(): ExpressionNode {
        currentToken = tokenProvider.nextToken() // Me muevo uno mas para estar en la condicion
        return parseExpression()
    }

    private fun advanceToStartOfBlock() {
        while (!isOpenBraceType() && tokenProvider.hasNextToken()) {
            currentToken = tokenProvider.nextToken()
        }
        currentToken = tokenProvider.nextToken() // me muevo hasta el token despues de la llave
    }

    private fun parseBlockStatements(): List<StatementNode> {
        val blockAst = mutableListOf<StatementNode>()
        // parseo los statments hasta encontrar una llave de cierre
        while (!isCloseBraceType() && tokenProvider.hasNextToken()) {
            val blockTokens = mutableListOf<Token>()
            // armo cada statement con los tokens
            while (!isSemiColonType() && !isCloseBraceType()) {
                if (isIfTokenType()) {
                    blockAst.add(processBlockNode()) // en caso de doble if
                } else {
                    blockTokens.add(currentToken)
                    currentToken = tokenProvider.nextToken()
                }
            }
            // avanzo un token despues del semicolon
            if (isSemiColonType()) {
                currentToken = tokenProvider.nextToken()
            }
            // parseo el statement que tengo hasta ahora y lo agrego al conjunto de statements en el bloque
            if (blockTokens.isNotEmpty()) {
                blockAst.add(processStatement(blockTokens))
            }
        }
        currentToken = tokenProvider.nextToken() // cuando encuentro la llave, avanzo uno y devuelvo el bloque
        return blockAst
    }

    private fun parseExpression(): ExpressionNode {
        return when (currentToken.type) {
            TokenType.IDENTIFIER ->
                IdentifierNode(
                    currentToken.value.toString(),
                    currentToken.line,
                    currentToken.column
                )
            TokenType.BOOLEAN ->
                BooleanLiteralNode(
                    (currentToken.value as TokenValue.BooleanValue).value,
                    currentToken.line,
                    currentToken.column
                )
            else -> throw RuntimeException("Unsupported token type: ${currentToken.type}")
        }
    }

    private fun processStatement(tokens: List<Token>): StatementNode {
        val firstToken = tokens.firstOrNull() ?: throw RuntimeException("Empty token list")
        val parser = getParser(firstToken)
        return parser.parse(tokens)
    }

    private fun checkUnknownToken() {
        if (currentToken.type == TokenType.UNKNOWN) {
            throw RuntimeException(
                "Unknown token at line: ${currentToken.line}, column: ${currentToken.column}"
            )
        }
    }

    private fun isElseTokenType(): Boolean {
        return currentToken.type == TokenType.ELSE
    }

    private fun isIfTokenType(): Boolean {
        return currentToken.type == TokenType.IF
    }

    private fun isSemiColonType(): Boolean {
        return currentToken.type == TokenType.SEMICOLON
    }

    private fun isCloseBraceType(): Boolean{
        return currentToken.type == TokenType.CLOSE_BRACE
    }

    private fun isOpenBraceType(): Boolean{
        return currentToken.type == TokenType.OPEN_BRACE
    }

    private fun getParser(firstToken: Token): Parser {
        return commands[firstToken.type] ?: when (firstToken.type) {
            TokenType.IDENTIFIER -> AssignationParser()
            TokenType.LET -> VariableDeclarationParser()
            TokenType.PRINT -> PrintParser()
            TokenType.CONST -> ConstDeclarationParser()
            else -> throw RuntimeException("Unsupported token type: ${firstToken.type}")
        }
    }
}