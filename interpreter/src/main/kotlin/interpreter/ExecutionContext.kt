package interpreter

class ExecutionContext {
    private val stack = mutableListOf<MutableMap<String, Any?>>()

    init {
        // Iniciar con un contexto global
        stack.add(mutableMapOf())
    }

    // Añadir una nueva variable en el contexto actual
    fun addVariable(name: String, value: Any?) {
        stack.last()[name] = value
    }

    // Obtener una variable del contexto más cercano
    fun getVariable(name: String): Any? {
        for (scope in stack.asReversed()) {
            if (scope.containsKey(name)) {
                return scope[name]
            }
        }
        return null
    }

    // Entrar a un nuevo contexto (cuando entras a un bloque)
    fun enterBlock() {
        stack.add(mutableMapOf())
    }

    // Salir del contexto actual (cuando sales de un bloque)
    fun exitBlock() {
        if (stack.size > 1) {
            stack.removeAt(stack.size - 1)
        }
    }
    override fun toString(): String {
        return stack.toString()
    }
}
