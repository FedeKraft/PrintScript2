package factory

import lexer.Lexer
import reader.Reader
import patterns.TokenPatterns
import token.TokenType

class LexerFactory {

    fun createLexer1_0(reader: Reader): Lexer {
        val patternsMap = mapOf(
            TokenPatterns.SEMICOLON to TokenType.SEMICOLON,
            // Primero palabras clave
            TokenPatterns.LET to TokenType.LET,
            TokenPatterns.PRINT to TokenType.PRINT,
            TokenPatterns.STRING_TYPE to TokenType.STRING_TYPE,
            TokenPatterns.NUMBER_TYPE to TokenType.NUMBER_TYPE,
            // Luego los identificadores y literales
            TokenPatterns.IDENTIFIER to TokenType.IDENTIFIER,
            TokenPatterns.NUMBER to TokenType.NUMBER,
            TokenPatterns.STRING to TokenType.STRING,
            // Operadores y símbolos
            TokenPatterns.ASSIGN to TokenType.ASSIGN,
            TokenPatterns.SUM to TokenType.SUM,
            TokenPatterns.SUBTRACT to TokenType.SUBTRACT,
            TokenPatterns.MULTIPLY to TokenType.MULTIPLY,
            TokenPatterns.DIVIDE to TokenType.DIVIDE,
            TokenPatterns.COLON to TokenType.COLON,
            TokenPatterns.LEFT_PARENTHESIS to TokenType.LEFT_PARENTHESIS,
            TokenPatterns.RIGHT_PARENTHESIS to TokenType.RIGHT_PARENTHESIS,
        )
        return Lexer(reader, patternsMap)
    }

    fun createLexer1_1(reader: Reader): Lexer {
        val patternsMap = mapOf(
            TokenPatterns.NUMBER to TokenType.NUMBER,
            TokenPatterns.STRING to TokenType.STRING,
            TokenPatterns.LET to TokenType.LET,
            TokenPatterns.PRINT to TokenType.PRINT,
            TokenPatterns.STRING_TYPE to TokenType.STRING_TYPE,
            TokenPatterns.NUMBER_TYPE to TokenType.NUMBER_TYPE,
            TokenPatterns.ELSE to TokenType.ELSE,
            TokenPatterns.BOOLEAN to TokenType.BOOLEAN,
            TokenPatterns.IF to TokenType.IF,
            TokenPatterns.CONST to TokenType.CONST,
            TokenPatterns.IDENTIFIER to TokenType.IDENTIFIER,
            TokenPatterns.ASSIGN to TokenType.ASSIGN,
            TokenPatterns.SUM to TokenType.SUM,
            TokenPatterns.SUBTRACT to TokenType.SUBTRACT,
            TokenPatterns.MULTIPLY to TokenType.MULTIPLY,
            TokenPatterns.DIVIDE to TokenType.DIVIDE,
            TokenPatterns.SEMICOLON to TokenType.SEMICOLON,
            TokenPatterns.COLON to TokenType.COLON,
            TokenPatterns.LEFT_PARENTHESIS to TokenType.LEFT_PARENTHESIS,
            TokenPatterns.RIGHT_PARENTHESIS to TokenType.RIGHT_PARENTHESIS,
            TokenPatterns.LEFT_BRACE to TokenType.OPEN_BRACE,
            TokenPatterns.RIGHT_BRACE to TokenType.CLOSE_BRACE,
            TokenPatterns.BOOLEAN_TYPE to TokenType.BOOLEAN_TYPE,
        )
        return Lexer(reader, patternsMap)
    }
}
