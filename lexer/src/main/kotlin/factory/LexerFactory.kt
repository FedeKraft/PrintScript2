package factory

import lexer.Lexer
import patterns.TokenPatterns
import reader.Reader
import token.TokenType

class LexerFactory {

    fun createLexer1_0(reader: Reader): Lexer {
        val patternsMap = mapOf(
            TokenPatterns.PRINT to TokenType.PRINT,
            TokenPatterns.LET to TokenType.LET,

            TokenPatterns.ASSIGN to TokenType.ASSIGN,
            TokenPatterns.LEFT_PARENTHESIS to TokenType.LEFT_PARENTHESIS,
            TokenPatterns.RIGHT_PARENTHESIS to TokenType.RIGHT_PARENTHESIS,
            TokenPatterns.SEMICOLON to TokenType.SEMICOLON,
            TokenPatterns.COLON to TokenType.COLON,

            TokenPatterns.STRING_TYPE to TokenType.STRING_TYPE,
            TokenPatterns.NUMBER_TYPE to TokenType.NUMBER_TYPE,

            TokenPatterns.NUMBER to TokenType.NUMBER,
            TokenPatterns.STRING to TokenType.STRING,

            TokenPatterns.SUM to TokenType.SUM,
            TokenPatterns.SUBTRACT to TokenType.SUBTRACT,
            TokenPatterns.MULTIPLY to TokenType.MULTIPLY,
            TokenPatterns.DIVIDE to TokenType.DIVIDE,

            TokenPatterns.IDENTIFIER to TokenType.IDENTIFIER,

            )
        return Lexer(reader, patternsMap)
    }

    fun createLexer1_1(reader: Reader): Lexer {
        val patternsMap = mapOf(
            TokenPatterns.PRINT to TokenType.PRINT,
            TokenPatterns.LET to TokenType.LET,
            TokenPatterns.CONST to TokenType.CONST,
            TokenPatterns.IF to TokenType.IF,
            TokenPatterns.ELSE to TokenType.ELSE,

            TokenPatterns.ASSIGN to TokenType.ASSIGN,
            TokenPatterns.LEFT_PARENTHESIS to TokenType.LEFT_PARENTHESIS,
            TokenPatterns.RIGHT_PARENTHESIS to TokenType.RIGHT_PARENTHESIS,
            TokenPatterns.LEFT_BRACE to TokenType.OPEN_BRACE,
            TokenPatterns.RIGHT_BRACE to TokenType.CLOSE_BRACE,
            TokenPatterns.SEMICOLON to TokenType.SEMICOLON,
            TokenPatterns.COLON to TokenType.COLON,


            TokenPatterns.STRING_TYPE to TokenType.STRING_TYPE,
            TokenPatterns.NUMBER_TYPE to TokenType.NUMBER_TYPE,
            TokenPatterns.BOOLEAN_TYPE to TokenType.BOOLEAN_TYPE,

            TokenPatterns.NUMBER to TokenType.NUMBER,
            TokenPatterns.STRING to TokenType.STRING,
            TokenPatterns.BOOLEAN to TokenType.BOOLEAN,

            TokenPatterns.SUM to TokenType.SUM,
            TokenPatterns.SUBTRACT to TokenType.SUBTRACT,
            TokenPatterns.MULTIPLY to TokenType.MULTIPLY,
            TokenPatterns.DIVIDE to TokenType.DIVIDE,

            TokenPatterns.IDENTIFIER to TokenType.IDENTIFIER,

        )
        return Lexer(reader, patternsMap)
    }
}
