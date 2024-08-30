package lexer

import handlers.ArithmeticOperatorHandler
import handlers.AssignationHandler
import handlers.ColonAndTypeHandler
import handlers.IdentifierOrKeywordHandler
import handlers.NumberHandler
import handlers.ParenthesisHandler
import handlers.SemicolonHandler
import handlers.StringLiteralHandler
import handlers.WhitespaceHandler

class LexerFactory {

    fun createLexer1_0(code: String): Lexer {
        val handlers = listOf(
            WhitespaceHandler(),
            IdentifierOrKeywordHandler(),
            StringLiteralHandler(),
            AssignationHandler(),
            SemicolonHandler(),
            NumberHandler(),
            ParenthesisHandler(),
            ColonAndTypeHandler(),
            ArithmeticOperatorHandler(),
        )
        return Lexer(code, handlers)
    }

    fun createLexer1_1(code: String): Lexer {
        val handlers = listOf(
            WhitespaceHandler(),
            IdentifierOrKeywordHandler(),
            StringLiteralHandler(),
            AssignationHandler(),
            SemicolonHandler(),
            NumberHandler(),
            ParenthesisHandler(),
            ColonAndTypeHandler(),
            ArithmeticOperatorHandler(), // agregar nuevos handlers cuando hagamos version 1.1
        )
        return Lexer(code, handlers)
    }
}
