package parser

import org.junit.jupiter.api.Test

class DummyASTProviderTest {
    @Test
    fun `test DummyASTProvider printing AST nodes`() {
        // Inicializar el DummyASTProvider
        val provider = DummyASTProvider()

        // Mientras haya ASTs para procesar
        while (provider.hasNextAST()) {
            val ast = provider.getNextAST()
            println(ast) // Imprimir el nodo AST
        }
    }
}
