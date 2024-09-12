// ParserTest.kt
package parser

import ast.AssignationNode
import ast.BinaryExpressionNode
import ast.BlockNode
import ast.BooleanLiteralNode
import ast.ConstDeclarationNode
import ast.IdentifierNode
import ast.IfElseNode
import ast.NumberLiteralNode
import ast.PrintStatementNode
import ast.StatementNode
import ast.StringLiteralNode
import ast.VariableDeclarationNode
import factory.LexerFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import reader.Reader
import token.TokenType
import java.io.File

class ParserTest {
    @Test
    fun `test parser`() {
        val reader = Reader(File("src/test/resources/testCodeIdentifier.txt").inputStream())
        val lexer = LexerFactory().createLexer1_1(reader)
        val parser = ParserFactory().createParser1_0(lexer)
        val actualAst = mutableListOf<StatementNode>()
        while (parser.hasNextAST()) {
            actualAst.add(parser.getNextAST())
        }
        println(actualAst)
    }

    @Test
    fun `test print statements parsing`() {
        val reader = Reader(File("src/test/resources/PrintAST/PrintStatements.txt").inputStream())
        val lexer = LexerFactory().createLexer1_1(reader)
        val parser = ParserFactory().createParser1_0(lexer)
        val actualAst = mutableListOf<StatementNode>()
        while (parser.hasNextAST()) {
            actualAst.add(parser.getNextAST())
        }
        val expectedAst = listOf(
            PrintStatementNode(IdentifierNode("a", 1, 9), 1, 1),
            PrintStatementNode(NumberLiteralNode(5.0, 2, 9), 2, 1),
            PrintStatementNode(StringLiteralNode("Hello", 3, 9), 3, 1),
            PrintStatementNode(BooleanLiteralNode(true, 4, 9), 4, 1),
        )
        // Assert that the actual AST matches the expected AST
        assertEquals(expectedAst, actualAst, "The AST generated does not match the expected structure")
    }

    @Test
    fun `test variable declarations parsing`() {
        val reader = Reader(File("src/test/resources/VariablesAST/VariablesStatements.txt").inputStream())
        val lexer = LexerFactory().createLexer1_1(reader)
        val parser = ParserFactory().createParser1_0(lexer)
        val actualAst = mutableListOf<StatementNode>()
        while (parser.hasNextAST()) {
            actualAst.add(parser.getNextAST())
        }
        val expectedAst = listOf(
            VariableDeclarationNode(
                IdentifierNode("name", 1, 5),
                TokenType.STRING_TYPE,
                StringLiteralNode("Olive", 1, 20),
                1,
                1, // Adjusted column
            ),
            VariableDeclarationNode(
                IdentifierNode("age", 2, 5),
                TokenType.NUMBER_TYPE,
                BinaryExpressionNode(
                    NumberLiteralNode(10.0, 2, 19),
                    TokenType.SUM,
                    NumberLiteralNode(5.0, 2, 24),
                    2,
                    22,
                ),
                2,
                1, // Adjusted column
            ),
            VariableDeclarationNode(
                IdentifierNode("age2", 3, 5),
                TokenType.NUMBER_TYPE,
                BinaryExpressionNode(
                    BinaryExpressionNode(
                        IdentifierNode("age", 3, 20),
                        TokenType.SUM,
                        NumberLiteralNode(5.0, 3, 26),
                        3,
                        24,
                    ),
                    TokenType.SUBTRACT,
                    BinaryExpressionNode(
                        BinaryExpressionNode(
                            NumberLiteralNode(1.0, 3, 30),
                            TokenType.MULTIPLY,
                            NumberLiteralNode(2.0, 3, 34),
                            3,
                            32,
                        ),
                        TokenType.DIVIDE,
                        NumberLiteralNode(3.0, 3, 38),
                        3,
                        36,
                    ),
                    3,
                    28,
                ),
                3,
                1, // Adjusted column
            ),
            VariableDeclarationNode(
                IdentifierNode("isAlive", 4, 5),
                TokenType.BOOLEAN_TYPE,
                BooleanLiteralNode(true, 4, 24),
                4,
                1, // Adjusted column
            ),
        )
        assertEquals(expectedAst, actualAst, "The AST generated does not match the expected structure")
    }

    @Test
    fun `test IfElseNode one IF`() {
        val reader = Reader(File("src/test/resources/If&IfElseAST/OneIfNode.txt").inputStream())
        val lexer = LexerFactory().createLexer1_1(reader)
        val parser = ParserFactory().createParser1_1(lexer)
        val actualAst = mutableListOf<StatementNode>()
        while (parser.hasNextAST()) {
            actualAst.add(parser.getNextAST())
        }
        val expectedAst = listOf(
            IfElseNode(
                BooleanLiteralNode(true, 1, 4),
                BlockNode(
                    listOf(
                        VariableDeclarationNode(
                            IdentifierNode("a2", 2, 9),
                            TokenType.STRING_TYPE,
                            StringLiteralNode("Bielsa", 2, 22),
                            2,
                            5, // Adjusted column
                        ),
                    ),
                    0,
                    0,
                ),
                null,
                1,
                1,
            ),
        )
        assertEquals(expectedAst, actualAst, "The AST generated does not match the expected structure")
    }

    @Test
    fun `test IfElseNode TWO IFS`() {
        val reader = Reader(File("src/test/resources/If&IfElseAST/TwoIfs.txt").inputStream())
        val lexer = LexerFactory().createLexer1_1(reader)
        val parser = ParserFactory().createParser1_1(lexer)
        val actualAst = mutableListOf<StatementNode>()
        while (parser.hasNextAST()) {
            actualAst.add(parser.getNextAST())
        }
        val expectedAst = listOf(
            IfElseNode(
                BooleanLiteralNode(true, 1, 4),
                BlockNode(
                    listOf(
                        VariableDeclarationNode(
                            IdentifierNode("a2", 2, 9),
                            TokenType.STRING_TYPE,
                            StringLiteralNode("Gordito", 2, 22),
                            2,
                            5, // Adjusted column
                        ),
                        IfElseNode(
                            BooleanLiteralNode(true, 3, 8),
                            BlockNode(
                                listOf(
                                    VariableDeclarationNode(
                                        IdentifierNode("a3", 4, 13),
                                        TokenType.STRING_TYPE,
                                        StringLiteralNode("hdp", 4, 27),
                                        4,
                                        9, // Adjusted column
                                    ),
                                    PrintStatementNode(
                                        IdentifierNode("a4", 5, 17),
                                        5,
                                        9, // Adjusted column
                                    ),
                                ),
                                0,
                                0,
                            ),
                            null,
                            3,
                            5,
                        ),
                        PrintStatementNode(
                            IdentifierNode("a7", 7, 13),
                            7,
                            5, // Adjusted column
                        ),
                    ),
                    0,
                    0,
                ),
                null,
                1,
                1,
            ),
        )
        assertEquals(expectedAst, actualAst, "The AST generated does not match the expected structure")
    }

    @Test
    fun `test IfElseNode with variable declarations in both if and else blocks`() {
        val reader = Reader(File("src/test/resources/If&IfElseAST/IfElseNode.txt").inputStream())
        val lexer = LexerFactory().createLexer1_1(reader)
        val parser = ParserFactory().createParser1_1(lexer)
        val actualAst = mutableListOf<StatementNode>()
        while (parser.hasNextAST()) {
            actualAst.add(parser.getNextAST())
        }
        val expectedAst = listOf(
            IfElseNode(
                BooleanLiteralNode(true, 1, 4),
                BlockNode(
                    listOf(
                        VariableDeclarationNode(
                            IdentifierNode("a2", 2, 9),
                            TokenType.STRING_TYPE,
                            StringLiteralNode("Bielsa", 2, 22),
                            2,
                            5, // Adjusted column
                        ),
                    ),
                    0,
                    0,
                ),
                BlockNode(
                    listOf(
                        VariableDeclarationNode(
                            IdentifierNode("a3", 5, 13),
                            TokenType.STRING_TYPE,
                            StringLiteralNode("Bielseada", 5, 26),
                            5,
                            9, // Adjusted column
                        ),
                    ),
                    0,
                    0,
                ),
                1,
                1,
            ),
        )
        assertEquals(expectedAst, actualAst, "The AST generated does not match the expected structure")
    }

    @Test
    fun `test assignation parsing`() {
        val reader = Reader(File("src/test/resources/AssignationAST/AssignationStatements.txt").inputStream())
        val lexer = LexerFactory().createLexer1_1(reader)
        val parser = ParserFactory().createParser1_0(lexer)
        val actualAst = mutableListOf<StatementNode>()
        while (parser.hasNextAST()) {
            actualAst.add(parser.getNextAST())
        }
        val expectedAst = listOf(
            AssignationNode(
                IdentifierNode("name", 1, 1),
                StringLiteralNode("UruguayoNoTeConfiesFaltaUnaBielseada", 1, 8),
                1,
                1,
            ),
            AssignationNode(
                IdentifierNode("a1", 2, 1),
                NumberLiteralNode(10.0, 2, 6),
                2,
                1,
            ),
            AssignationNode(
                IdentifierNode("a2", 3, 1),
                IdentifierNode("a1", 3, 6),
                3,
                1,
            ),
            AssignationNode(
                IdentifierNode("a1", 4, 1),
                BooleanLiteralNode(true, 4, 6),
                4,
                1,
            ),
        )
        assertEquals(expectedAst, actualAst, "The AST generated does not match the expected structure")
    }

    @Test
    fun `test Const parsing`() {
        val reader = Reader(File("src/test/resources/ConstAST/ConstStatements.txt").inputStream())
        val lexer = LexerFactory().createLexer1_1(reader)
        val parser = ParserFactory().createParser1_1(lexer)
        val actualAst = mutableListOf<StatementNode>()
        while (parser.hasNextAST()) {
            actualAst.add(parser.getNextAST())
        }
        val expectedAst = listOf(
            ConstDeclarationNode(
                IdentifierNode("name", 1, 7),
                StringLiteralNode("Olive", 1, 22),
                1,
                7,
            ),
            ConstDeclarationNode(
                IdentifierNode("age", 2, 7),
                NumberLiteralNode(10.0, 2, 21),
                2,
                7,
            ),
            ConstDeclarationNode(
                IdentifierNode("isAlive", 3, 7),
                BooleanLiteralNode(false, 3, 26),
                3,
                7,
            ),
        )
        assertEquals(expectedAst, actualAst, "The AST generated does not match the expected structure")
    }

    @Test
    fun `test readInput in parsers`() {
        val reader = Reader(File("src/test/resources/ReadEnvAndReadInput.txt").inputStream())
        val lexer = LexerFactory().createLexer1_1(reader)
        val parser = ParserFactory().createParser1_0(lexer)
        val actualAst = mutableListOf<StatementNode>()
        while (parser.hasNextAST()) {
            val ast = parser.getNextAST()
            println(ast)
            actualAst.add(ast)
        }
    }
}
