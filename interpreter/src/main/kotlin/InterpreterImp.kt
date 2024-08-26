class InterpreterImp {

    private var context = ExecutionContext()
    private val semanticChecker = SemanticCheckerVisitor(context)
    private val interpreterVisitor = InterpreterVisitor(context)

    fun interpret(programNode: ProgramNode) {
        programNode.accept(semanticChecker)
        programNode.accept(interpreterVisitor)
    }
    fun getContext(): ExecutionContext {
        return context
    }
}
